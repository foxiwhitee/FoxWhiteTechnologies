package foxiwhitee.FoxWhiteTechnologies.recipes.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IBotanyRecipe {
    List<Object> getInputs();
    ItemStack getOutput();

    boolean upgradedMatches(IInventory inv, boolean b);

    default boolean matches(IInventory inv) {
        return upgradedMatches(inv, false);
    }
}
