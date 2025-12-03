package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeManaInfusion;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.CustomRecipeRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.util.RecipeInitializer;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import vazkii.botania.common.block.mana.BlockAlchemyCatalyst;
import vazkii.botania.common.block.mana.BlockConjurationCatalyst;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class TileMechanicManaPool extends TileMechanicManaBlock<CustomRecipeManaInfusion> {
    private static final List<CustomRecipeManaInfusion> recipes = new ArrayList<>();
    private boolean alchemy, conjuration;

    public TileMechanicManaPool() {
        if (recipes.isEmpty()) {
            RecipeInitializer.initRecipesMechanicManaPool(recipes);
        }
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        super.onChangeInventory(iInventory, i, invOperation, itemStack, itemStack1);
        if (iInventory == getInternalInventory() && i == 0) {
            alchemy = false;
            conjuration = false;
            if (itemStack1 != null) {
                if (Block.getBlockFromItem(itemStack1.getItem()) instanceof BlockConjurationCatalyst) {
                    conjuration = true;
                } else if (Block.getBlockFromItem(itemStack1.getItem()) instanceof BlockAlchemyCatalyst) {
                    alchemy = true;
                }
            }
        }
        markForUpdate();
    }

    protected void updateRecipeIfNeeded() {
        usedSlots.clear();

        List<InvEntry> inputs = new ArrayList<>();

        for (int i = 1; i < getInvSize(); i++) {
            ItemStack stack = getInternalInventory().getStackInSlot(i);
            if (stack != null) {
                inputs.add(new InvEntry(i, stack));
            }
        }

        currentRecipe = getRecipe(inputs);
    }

    protected CustomRecipeManaInfusion getRecipe(List<InvEntry> entries) {
        usedSlots.clear();

        for (CustomRecipeManaInfusion r : getRecipes()) {
            List<Integer> matchedSlots = new ArrayList<>();
            if (r.getOutput().getItem() == ModItems.manaResource && r.getOutput().getItemDamage() == 0) {
                int oo = 0;
            }
            List<ItemStack> matchedStacks = matchesStacks(new ArrayList<>(r.getInputs()), matchedSlots, entries);

            if (matchedStacks != null) {
                InventoryBasic inv = new InventoryBasic("null", false, matchedStacks.size());
                for (int i = 0; i < matchedStacks.size(); i++)
                    inv.setInventorySlotContents(i, matchedStacks.get(i));

                if (r.upgradedMatches(inv, true) && r.isAlchemy() == alchemy && r.isConjuration() == conjuration) {

                    usedSlots.addAll(matchedSlots);
                    return r;
                }
            }
        }

        return null;
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        alchemy = data.getBoolean("alchemy");
        conjuration = data.getBoolean("conjuration");
        markForUpdate();
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        data.setBoolean("alchemy", alchemy);
        data.setBoolean("conjuration", conjuration);
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        boolean oldAl = alchemy;
        boolean oldConj = conjuration;
        alchemy = data.readBoolean();
        conjuration = data.readBoolean();
        return old || oldAl != alchemy || oldConj != conjuration;
    }

    @Override
    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeBoolean(alchemy);
        data.writeBoolean(conjuration);
    }

    @Override
    protected List<CustomRecipeManaInfusion> getRecipes() {
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
        return 20 * WTConfig.speedManaPool;
    }

    @Override
    protected int getMaxSpeedBonus() {
        return WTConfig.manaPoolMaxSpeedBonus;
    }

    @Override
    protected boolean hasProductivity() {
        return WTConfig.manaPoolHasProductivity;
    }
}
