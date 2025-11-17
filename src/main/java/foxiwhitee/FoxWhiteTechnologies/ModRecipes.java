package foxiwhitee.FoxWhiteTechnologies;

import foxiwhitee.FoxLib.recipes.RecipesLocation;
import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRecipes {

    @RecipesLocation(modId = "foxwhitetechnologies")
    public static final String[] recipes = {"recipes"};

    public static final List<RecipeMalachitePlate> recipesMalachitePlate = new ArrayList<>();

    public static void registerRecipes() {
    }
}
