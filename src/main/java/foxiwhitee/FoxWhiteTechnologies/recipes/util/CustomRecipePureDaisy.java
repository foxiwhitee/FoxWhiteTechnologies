package foxiwhitee.FoxWhiteTechnologies.recipes.util;

import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.recipe.RecipePureDaisy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomRecipePureDaisy implements IBotanyRecipe {
    private final Object input;
    private final ItemStack ouput;

    public CustomRecipePureDaisy(Object input, ItemStack stack) {
        this.input = input;
        this.ouput = stack;
    }

    public CustomRecipePureDaisy(RecipePureDaisy recipe) {
        this.input = recipe.getInput();
        this.ouput = new ItemStack(recipe.getOutput(), 1, recipe.getOutputMeta());
    }

    @Override
    public List<Object> getInputs() {
        return Collections.singletonList(input);
    }

    @Override
    public boolean upgradedMatches(IInventory inv, boolean b) {
        ItemStack stack = inv.getStackInSlot(0);
        if (input instanceof String str) {
            List<ItemStack> validStacks = OreDictionary.getOres(str);

            for(ItemStack ostack : validStacks) {
                ItemStack cstack = ostack.copy();
                if (cstack.getItemDamage() == 32767) {
                    cstack.setItemDamage(stack.getItemDamage());
                }

                if (stack.isItemEqual(cstack)) {
                    return true;
                }
            }

        } else if (input instanceof ItemStack st && this.simpleAreStacksEqual(st, stack)) {
            return true;
        } else if (input instanceof StackOreDict ore && ore.check(stack, b)) {
            return true;
        }
        return false;
    }

    boolean simpleAreStacksEqual(ItemStack stack, ItemStack stack2) {
        return stack.getItem() == stack2.getItem() && stack.getItemDamage() == stack2.getItemDamage();
    }

    @Override
    public ItemStack getOutput() {
        return ouput;
    }
}
