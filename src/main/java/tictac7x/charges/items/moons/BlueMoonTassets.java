package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class BlueMoonTassets extends _MoonItem {
    public BlueMoonTassets(
        final Provider provider
    ) {
        super("Blue moon tassets", ItemId.BLUE_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLUE_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemId.BLUE_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemId.BLUE_MOON_TASSETS_BROKEN).fixedCharges(0),
        };
    }
}