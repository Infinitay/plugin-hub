package tictac7x.charges.items.weapons;

import java.awt.Color;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_VenatorBow extends ChargedItemWithStorage {
    public W_VenatorBow(final Provider provider) {
        this(TicTac7xChargesImprovedConfig.venator_bow, ItemId.VENATOR_BOW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.VENATOR_BOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.VENATOR_BOW)
        };
    }

    public W_VenatorBow(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);

        this.storage.storableItems(
            new StorableItem(ItemId.ANCIENT_ESSENCE)
        );

        this.triggers.addAll(List.of(
            // Charge it with ancient essence.
            new OnWidgetLoaded(InterfaceID.OBJECTBOX, 0).onItemClick().consumer(() -> {
                Widget objectboxTextWidget = provider.client.getWidget(InterfaceID.Objectbox.TEXT);
                if (objectboxTextWidget != null && objectboxTextWidget.getText() != null && !objectboxTextWidget.getText().isEmpty()) {
                    String cleanedText = TicTac7xChargesImprovedPlugin.getCleanText(objectboxTextWidget.getText());

                    // Adding charges
                    // First test to see if the bow is already at max capacity
                    if (cleanedText.endsWith("venator bow is already fully charged.")) {
                        final StorageItem ancientEssence = new StorageItem(ItemId.ANCIENT_ESSENCE, 50000);
                        storage.clearAndPut(ancientEssence);
                        return;
                    }

                    // For charging your echo venator bow, as of March 2026, the game doesn't explicitly say "echo venator bow", but I'll include it just in case
                    Pattern chargePattern = Pattern.compile("You use (?<addedCharges>.+) ancient essence to charge your (echo )?venator bow. It now has (?<charges>.+) charges.");
                    Matcher chargeMatcher = chargePattern.matcher(cleanedText);
                    if (chargeMatcher.find()) {
                        int charges = TicTac7xChargesImprovedPlugin.getNumberFromCommaString(chargeMatcher.group("charges"));
                        final StorageItem ancientEssence = new StorageItem(ItemId.ANCIENT_ESSENCE, charges);
                        storage.clearAndPut(ancientEssence);
                    }
                }
            }),

            // Uncharge (you can only uncharge ALL charges at once)
            new OnWidgetLoaded(InterfaceID.OBJECTBOX, 0).consumer(() -> {
                Widget objectboxTextWidget = provider.client.getWidget(InterfaceID.Objectbox.TEXT);
                if (objectboxTextWidget != null && objectboxTextWidget.getText() != null && !objectboxTextWidget.getText().isEmpty()) {
                    String cleanedText = TicTac7xChargesImprovedPlugin.getCleanText(objectboxTextWidget.getText());
                    Pattern unchargePattern = Pattern.compile("You fully uncharge your (echo )?venator bow, regaining (?<charges>.+) ancient essence in the process.");
                    Matcher unchargeMatcher = unchargePattern.matcher(cleanedText);
                    Widget objectboxItemWidget = provider.client.getWidget(InterfaceID.Objectbox.ITEM);
                    // Check against the uncharged item since the item displayed on the widget will be the uncharged version
                    if (unchargeMatcher.find() && objectboxItemWidget != null && objectboxItemWidget.getItemId() == this.itemId) {
                        storage.clear();
                    }
                }
            }),

            // Check.
            new OnChatMessage("Your (echo )?venator bow has (?<charges>.+) charges? remaining.").onItemClick().matcherConsumer(m -> {
                final StorageItem ancientEssence = new StorageItem(ItemId.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(ancientEssence);
            }).setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2289).isEquipped().consumer(() -> {
                final Optional<StorageItem> ancientEssence = this.storage.getStorage().getItem(ItemId.ANCIENT_ESSENCE);
                if (ancientEssence.isPresent() && ancientEssence.get().getQuantity() > 0) {
                    ancientEssence.get().decreaseQuantity(1);
                }
            }).decreaseCharges(1)
        ));
    }

    @Override
    public Color getTextColor(final int itemId) {
        return this.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (this.storage.getStorage().count(ItemId.ANCIENT_ESSENCE) == 0) {
            return provider.config.getColorEmpty();
        }

        return super.getTotalTextColor();
    }
}
