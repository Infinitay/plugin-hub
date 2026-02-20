package tictac7x.charges.items.shields;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class S_Chronicle extends ChargedItem {
    public S_Chronicle(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.chronicle, ItemId.CHRONICLE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CHRONICLE),
        };

        this.triggers.addAll(List.of(
            // Check plural.
            new OnChatMessage("Your book has (?<charges>.+) charges? left.").setDynamicallyCharges().onItemClick(),

            // Check single.
            new OnChatMessage("You have one charge left in your book.").setFixedCharges(1).onItemClick()
        ));
    }
}
