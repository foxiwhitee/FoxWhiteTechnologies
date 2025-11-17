package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.util.RecipeInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TileMechanicRuneAltar extends TileMechanicManaBlock<CustomRecipeRuneAltar> {
    private static List<ItemStack> runes;
    private static final List<CustomRecipeRuneAltar> recipes = new ArrayList<>();

    public TileMechanicRuneAltar() {
        if (runes == null || runes.isEmpty()) {
            initRunes();
        }
        if (recipes.isEmpty()) {
            RecipeInitializer.initRecipesMechanicRuneAltar(recipes);
        }
    }

    private static void initRunes() {
        runes = new ArrayList<>();
        for (int i = 0; i < WTConfig.runes.length; i++) {
            String r = WTConfig.runes[i];
            String[] split = r.split(":");
            if (split.length == 2) {
                Item item = (Item) Item.itemRegistry.getObject(r);
                runes.add(new ItemStack(item));
            } else if (split.length == 3) {
                Item item = (Item) Item.itemRegistry.getObject(split[0] + ":" + split[1]);
                runes.add(new ItemStack(item, 1, Integer.parseInt(split[2])));
            }
        }
    }

    protected void craftRecipe() {
        if (currentRecipe == null)
            return;

        for (int slot : usedSlots) {
            if (getInternalInventory().getStackInSlot(slot) != null) {
                ItemStack stack = getInternalInventory().getStackInSlot(slot);
                boolean b = false;
                for (ItemStack itemStack : runes) {
                    if (itemStack.getItem() == stack.getItem() && itemStack.getItemDamage() == stack.getItemDamage()) {
                        b = true;
                        break;
                    }
                }
                if (!b)
                    consumeItem(stack);
                if (stack.stackSize <= 0)
                    getInternalInventory().setInventorySlotContents(slot, null);
            }
        }
        updateCountInStacks();

        if (getInternalInventory().getStackInSlot(0) != null) {
            getInternalInventory().getStackInSlot(0).stackSize--;
            if (getInternalInventory().getStackInSlot(0).stackSize <= 0)
                getInternalInventory().setInventorySlotContents(0, null);
        }

        ItemStack out = currentRecipe.getOutput().copy();
        insertOutput(out);
    }

    protected void updateRecipeIfNeeded() {
        usedSlots.clear();

        if (getInternalInventory().getStackInSlot(0) == null) {
            currentRecipe = null;
            return;
        }

        List<InvEntry> inputs = new ArrayList<>();

        for (int i = 1; i < getInvSize(); i++) {
            ItemStack stack = getInternalInventory().getStackInSlot(i);
            if (stack != null) {
                inputs.add(new InvEntry(i, stack));
            }
        }

        currentRecipe = getRecipe(inputs);
    }

    @Override
    protected List<CustomRecipeRuneAltar> getRecipes() {
        return recipes;
    }


    @Override
    protected int getInvSize() {
        return 25;
    }

    @Override
    protected int getInvOutSize() {
        return 24;
    }

    @Override
    protected int getSpeed() {
        return 20 * WTConfig.speedRuneAltar;
    }
}
