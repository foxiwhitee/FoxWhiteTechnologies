package foxiwhitee.FoxWhiteTechnologies.recipes.util;

import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipeManaInfusion;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipeManaInfusion extends RecipeManaInfusion implements IBotanyManaRecipe {
    public CustomRecipeManaInfusion(ItemStack output, Object input, int mana, boolean isAlchemy, boolean isConjuration) {
        super(output, input, mana);
        setAlchemy(isAlchemy);
        setConjuration(isConjuration);
    }

    public CustomRecipeManaInfusion(RecipeManaInfusion recipe) {
        super(recipe.getOutput(), recipe.getInput(), recipe.getManaToConsume());
        setAlchemy(recipe.isAlchemy());
        setConjuration(recipe.isConjuration());
    }

    @Override
    public List<Object> getInputs() {
        List<Object> inputs = new ArrayList<>();
        inputs.add(getInput());
        return inputs;
    }

    @Override
    public int getManaUsage() {
        return getManaToConsume();
    }

    @Override
    public boolean upgradedMatches(IInventory inv, boolean b) {
        return matches(inv.getStackInSlot(0));
    }

    public boolean matches(ItemStack stack) {
        if (this.getInput() instanceof ItemStack) {
            ItemStack inputCopy = ((ItemStack)this.getInput()).copy();
            if (inputCopy.getItemDamage() == 32767) {
                inputCopy.setItemDamage(stack.getItemDamage());
            }

            return stack.isItemEqual(inputCopy);
        } else {
            if (this.getInput() instanceof String) {
                for(ItemStack ostack : OreDictionary.getOres((String)this.getInput())) {
                    ItemStack cstack = ostack.copy();
                    if (cstack.getItemDamage() == 32767) {
                        cstack.setItemDamage(stack.getItemDamage());
                    }

                    if (stack.isItemEqual(cstack)) {
                        return true;
                    }
                }
            } else if (this.getInput() instanceof StackOreDict) {
                for(ItemStack ostack : OreDictionary.getOres((String)((StackOreDict) this.getInput()).getOre())) {
                    ItemStack cstack = ostack.copy();
                    if (cstack.getItemDamage() == 32767) {
                        cstack.setItemDamage(stack.getItemDamage());
                    }

                    if (stack.isItemEqual(cstack)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
