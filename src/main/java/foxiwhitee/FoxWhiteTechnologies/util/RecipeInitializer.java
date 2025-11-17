package foxiwhitee.FoxWhiteTechnologies.util;

import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeRuneAltar;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;

import java.util.ArrayList;
import java.util.List;

public class RecipeInitializer {
    public static void initRecipesMechanicRuneAltar(List<CustomRecipeRuneAltar> recipes) {
        for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes) {
            List<Object> ingredients = new ArrayList<>();

            for (Object ingr : r.getInputs()) {
                if (ingr instanceof ItemStack stack) {
                    boolean found = false;
                    for (Object o : ingredients) {
                        if (o instanceof ItemStack temp) {
                            if (ItemStack.areItemStacksEqual(temp, stack)) {
                                temp.stackSize += stack.stackSize;
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        ingredients.add(stack.copy());
                    }
                } else if (ingr instanceof String str) {
                    boolean found = false;
                    for (Object o : ingredients) {
                        if (o instanceof StackOreDict temp) {
                            if (temp.getOre().equals(str)) {
                                temp.setCount(temp.getCount() + 1);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        ingredients.add(new StackOreDict(str, 1));
                    }
                }
            }

            recipes.add(new CustomRecipeRuneAltar(r.getOutput(), r.getManaUsage(), ingredients.toArray()));
        }
    }
}
