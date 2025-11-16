package foxiwhitee.FoxWhiteTechnologies;

import foxiwhitee.FoxWhiteTechnologies.recipes.RecipeMalachitePlate;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRecipes {
    public static final List<RecipeMalachitePlate> recipesMalachitePlate = new ArrayList<>();

    public static void registerRecipes() {
        recipesMalachitePlate.add(new RecipeMalachitePlate(new ItemStack(ModItems.manaResource, 1, 4), 0, 500000, Arrays.asList(
            new ItemStack(Blocks.stone, 2), new ItemStack(ModItems.manaResource, 1)
        )));
    }
}
