package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class BlueMoonHelm extends _MoonItem {
    public BlueMoonHelm(
        final Provider provider
    ) {
        super("Blue moon helm", ItemId.BLUE_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLUE_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemId.BLUE_MOON_HELM_DEGRADED),
            new TriggerItem(ItemId.BLUE_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}