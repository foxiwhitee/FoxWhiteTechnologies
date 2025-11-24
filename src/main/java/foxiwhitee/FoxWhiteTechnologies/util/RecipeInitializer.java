package foxiwhitee.FoxWhiteTechnologies.util;

import foxiwhitee.FoxWhiteTechnologies.recipes.util.*;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecipeInitializer {
    public static void initRecipesMechanicRuneAltar(List<CustomRecipeRuneAltar> recipes) {
        for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes) {
            recipes.add(new CustomRecipeRuneAltar(r.getOutput(), r.getManaUsage(), createIngredients(r.getInputs()).toArray()));
        }
    }

    public static void initRecipesMechanicPetals(List<CustomRecipePetals> recipes) {
        for (RecipePetals r : BotaniaAPI.petalRecipes) {
            recipes.add(new CustomRecipePetals(r.getOutput(), createIngredients(r.getInputs()).toArray()));
        }
    }

    public static void initRecipesMechanicElvenTrade(List<CustomRecipeElvenTrade> recipes) {
        for (RecipeElvenTrade r : BotaniaAPI.elvenTradeRecipes) {
            recipes.add(new CustomRecipeElvenTrade(r.getOutput(), createIngredients(r.getInputs()).toArray()));
        }
    }

    public static void initRecipesMechanicManaPool(List<CustomRecipeManaInfusion> recipes) {
        for (RecipeManaInfusion r : BotaniaAPI.manaInfusionRecipes) {
            recipes.add(new CustomRecipeManaInfusion(r.getOutput(), createIngredients(Collections.singletonList(r.getInput())).get(0), r.getManaToConsume(), r.isAlchemy(), r.isConjuration()));
        }
    }

    public static void initRecipesMechanicPureDaisy(List<CustomRecipePureDaisy> recipes) {
        for (RecipePureDaisy r : BotaniaAPI.pureDaisyRecipes) {
            recipes.add(new CustomRecipePureDaisy(r));
        }
    }

    private static List<Object> createIngredients(List<Object> objects) {
        List<Object> ingredients = new ArrayList<>();

        for (Object ingr : objects) {
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
        return ingredients;
    }
}
