package tictac7x.charges.item.listeners;

import net.runelite.api.events.VarbitChanged;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.OnVarbitsMapChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnVarbitsMapChanged extends ListenerBase {
    public ListenerOnVarbitsMapChanged(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final VarbitChanged event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;
            final OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;
            final ChargedItemWithStorage chargedItem = (ChargedItemWithStorage) this.chargedItem;
            boolean triggerUsed = false;

            chargedItem.storage.put(trigger.varbitsMap.get(event.getVarbitId()), event.getValue());
            triggerUsed = true;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final VarbitChanged event) {
        if (!(triggerBase instanceof OnVarbitsMapChanged)) return false;
        if (!(chargedItem instanceof ChargedItemWithStorage)) return false;

        final OnVarbitsMapChanged trigger = (OnVarbitsMapChanged) triggerBase;

        // Valid varbit id check.
        if (!trigger.varbitsMap.containsKey(event.getVarbitId())) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
