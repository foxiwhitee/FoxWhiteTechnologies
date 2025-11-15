package foxiwhitee.FoxWhiteTechnologies.recipes;

import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipeRuneAltar;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipeRuneAltar implements IBotanyManaRecipe {
    private final ItemStack output;
    private final List<Object> inputs;
    private final int mana;

    public CustomRecipeRuneAltar(ItemStack output, int mana, Object... inputs) {
        this.output = output;
        this.mana = mana;
        List<Object> inputsToSet = new ArrayList();

        for(Object obj : inputs) {
            if (!(obj instanceof String) && !(obj instanceof ItemStack) && !(obj instanceof StackOreDict)) {
                throw new IllegalArgumentException("Invalid input");
            }

            inputsToSet.add(obj);
        }

        this.inputs = inputsToSet;
    }

    public CustomRecipeRuneAltar(RecipeRuneAltar recipe) {
        this(recipe.getOutput(), recipe.getManaUsage(), recipe.getInputs().toArray());
    }

    public boolean upgradedMatches(IInventory inv, boolean stackOreStack) {
        List<Object> inputsMissing = new ArrayList<>(this.getInputs());

        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) {
                break;
            }

            int stackIndex = -1;
            int oredictIndex = -1;

            for(int j = 0; j < inputsMissing.size(); ++j) {
                Object input = inputsMissing.get(j);
                if (input instanceof String str) {
                    List<ItemStack> validStacks = OreDictionary.getOres(str);
                    boolean found = false;

                    for(ItemStack ostack : validStacks) {
                        ItemStack cstack = ostack.copy();
                        if (cstack.getItemDamage() == 32767) {
                            cstack.setItemDamage(stack.getItemDamage());
                        }

                        if (stack.isItemEqual(cstack)) {
                            oredictIndex = j;
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        break;
                    }
                } else if (input instanceof ItemStack st && this.simpleAreStacksEqual(st, stack)) {
                    stackIndex = j;
                    break;
                } else if (input instanceof StackOreDict ore && ore.check(stack, stackOreStack)) {
                    stackIndex = j;
                    break;
                }
            }

            if (stackIndex != -1) {
                inputsMissing.remove(stackIndex);
            } else {
                if (oredictIndex == -1) {
                    return false;
                }

                inputsMissing.remove(oredictIndex);
            }
        }

        return inputsMissing.isEmpty();
    }

    boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2) {
        return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
    }

    @Override
    public boolean matches(IInventory inv) {
        return upgradedMatches(inv, false);
    }

    public List<Object> getInputs() {
        return new ArrayList(this.inputs);
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public int getManaUsage() {
        return this.mana;
    }
}
