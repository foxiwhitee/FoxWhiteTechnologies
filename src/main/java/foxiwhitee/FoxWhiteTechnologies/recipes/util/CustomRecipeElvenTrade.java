package foxiwhitee.FoxWhiteTechnologies.recipes.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipeElvenTrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomRecipeElvenTrade implements IBotanyManaRecipe{
    private final ItemStack output;
    private final List<Object> inputs;

    public CustomRecipeElvenTrade(ItemStack output, Object... inputs) {
        this.output = output;
        this.inputs = Arrays.asList(inputs);
    }

    public CustomRecipeElvenTrade(RecipeElvenTrade recipe) {
        this(recipe.getOutput(), recipe.getInputs());
    }

    @Override
    public int getManaUsage() {
        return 10000;
    }

    @Override
    public List<Object> getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public boolean upgradedMatches(IInventory inv, boolean b) {
        List<ItemStack> stack = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            stack.add(inv.getStackInSlot(i));
        }
        return matches(stack, false);
    }

    public boolean matches(List<ItemStack> stacks, boolean remove) {
        List<Object> inputsMissing = new ArrayList(this.inputs);
        List<ItemStack> stacksToRemove = new ArrayList();

        for(ItemStack stack : stacks) {
            if (stack != null) {
                if (inputsMissing.isEmpty()) {
                    break;
                }

                int stackIndex = -1;
                int oredictIndex = -1;

                for(int j = 0; j < inputsMissing.size(); ++j) {
                    Object input = inputsMissing.get(j);
                    if (input instanceof String) {
                        List<ItemStack> validStacks = OreDictionary.getOres((String)input);
                        boolean found = false;

                        for(ItemStack ostack : validStacks) {
                            if (OreDictionary.itemMatches(ostack, stack, false)) {
                                if (!stacksToRemove.contains(stack)) {
                                    stacksToRemove.add(stack);
                                }

                                oredictIndex = j;
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            break;
                        }
                    } else if (input instanceof ItemStack && this.simpleAreStacksEqual((ItemStack)input, stack)) {
                        if (!stacksToRemove.contains(stack)) {
                            stacksToRemove.add(stack);
                        }

                        stackIndex = j;
                        break;
                    }
                }

                if (stackIndex != -1) {
                    inputsMissing.remove(stackIndex);
                } else if (oredictIndex != -1) {
                    inputsMissing.remove(oredictIndex);
                }
            }
        }

        if (remove) {
            for(ItemStack r : stacksToRemove) {
                stacks.remove(r);
            }
        }

        return inputsMissing.isEmpty();
    }

    boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2) {
        return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
    }
}
