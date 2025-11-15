package foxiwhitee.FoxWhiteTechnologies.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IBotanyManaRecipe {
    List<Object> getInputs();
    ItemStack getOutput();
    int getManaUsage();

    boolean upgradedMatches(IInventory inv, boolean b);

    default boolean matches(IInventory inv) {
        return upgradedMatches(inv, false);
    }
}
