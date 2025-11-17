package foxiwhitee.FoxWhiteTechnologies.recipes;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.IBotanyManaRecipe;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class RecipeMalachitePlate implements IFoxRecipe, IBotanyManaRecipe {
    private final ItemStack output;
    private final List<Object> inputs;
    private final int tier, manaCost;

    public RecipeMalachitePlate(ItemStack output, int tier, int manaCost, List<Object> inputs) {
        this.output = output;
        this.inputs = inputs;
        this.tier = tier;
        this.manaCost = manaCost;
    }

    public RecipeMalachitePlate(ItemStack output, List<Object> inputs, int manaCost) {
        this(output, 0, manaCost, inputs);
    }

    @Override
    public ItemStack getOut() {
        return output;
    }

    @Override
    public List<Object> getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getOutput() {
        return getOut();
    }

    @Override
    public int getManaUsage() {
        return getManaCost();
    }

    @Override
    public boolean upgradedMatches(IInventory inv, boolean b) {
        List<ItemStack> stack = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            stack.add(inv.getStackInSlot(i));
        }
        return matches(stack);
    }

    @Override
    public boolean matches(List<ItemStack> list) {
        List<Object> inputsMissing = new ArrayList<>(this.getInputs());

        for(ItemStack stack : list) {
            if (stack == null) {
                break;
            }

            int stackIndex = -1;

            for(int j = 0; j < inputsMissing.size(); ++j) {
                Object input = inputsMissing.get(j);
                if (input instanceof ItemStack st && IFoxRecipe.simpleAreStacksEqual(st, stack) && stack.stackSize >= st.stackSize) {
                    stackIndex = j;
                    break;
                } else if (input instanceof StackOreDict ore && ore.check(stack, true)) {
                    stackIndex = j;
                    break;
                }
            }

            if (stackIndex != -1) {
                inputsMissing.remove(stackIndex);
            } else {
                return false;
            }
        }

        return inputsMissing.isEmpty();
    }

    public int getTier() {
        return tier;
    }

    public int getManaCost() {
        return manaCost;
    }
}
