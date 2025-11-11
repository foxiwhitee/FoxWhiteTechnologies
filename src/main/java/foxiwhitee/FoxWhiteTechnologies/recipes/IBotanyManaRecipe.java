package foxiwhitee.FoxWhiteTechnologies.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IBotanyManaRecipe {
    boolean matches(IInventory inv);
    List<Object> getInputs();
    ItemStack getOutput();
    int getManaUsage();
}
