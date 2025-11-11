package foxiwhitee.FoxWhiteTechnologies.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeElvenTrade;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipeElvenTrade extends RecipeElvenTrade implements IBotanyManaRecipe{
    public CustomRecipeElvenTrade(ItemStack output, Object... inputs) {
        super(output, inputs);
    }

    public CustomRecipeElvenTrade(RecipeElvenTrade recipe) {
        super(recipe.getOutput(), recipe.getInputs());
    }

    @Override
    public boolean matches(IInventory inv) {
        List<ItemStack> stack = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            stack.add(inv.getStackInSlot(i));
        }
        return matches(stack, false);
    }

    @Override
    public int getManaUsage() {
        return 10000;
    }
}
