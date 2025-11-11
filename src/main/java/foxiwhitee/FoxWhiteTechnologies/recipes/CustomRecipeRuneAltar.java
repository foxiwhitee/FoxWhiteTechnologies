package foxiwhitee.FoxWhiteTechnologies.recipes;

import net.minecraft.item.ItemStack;
import vazkii.botania.api.recipe.RecipeRuneAltar;

public class CustomRecipeRuneAltar extends RecipeRuneAltar implements IBotanyManaRecipe {
    public CustomRecipeRuneAltar(ItemStack output, int mana, Object... inputs) {
        super(output, mana, inputs);
    }

    public CustomRecipeRuneAltar(RecipeRuneAltar recipe) {
        super(recipe.getOutput(), recipe.getManaUsage(), recipe.getInputs());
    }
}
