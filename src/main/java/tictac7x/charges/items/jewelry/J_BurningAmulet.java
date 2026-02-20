package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_BurningAmulet extends ChargedItem {
    public J_BurningAmulet(
        final Provider provider
    ) {
        super(TicTac7xChargesImprovedConfig.burning_amulet, ItemId.BURNING_AMULET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BURNING_AMULET_1).fixedCharges(1),
            new TriggerItem(ItemId.BURNING_AMULET_2).fixedCharges(2),
            new TriggerItem(ItemId.BURNING_AMULET_3).fixedCharges(3),
            new TriggerItem(ItemId.BURNING_AMULET_4).fixedCharges(4),
            new TriggerItem(ItemId.BURNING_AMULET_5).fixedCharges(5),
        };

        this.triggers.addAll(List.of(
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}