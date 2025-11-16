package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.tile.FoxBaseInvTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import foxiwhitee.FoxWhiteTechnologies.recipes.CustomRecipeRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.recipes.IBotanyManaRecipe;
import foxiwhitee.FoxWhiteTechnologies.util.StackOreDict;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.client.core.handler.HUDHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class TileMechanicManaBlock<T extends IBotanyManaRecipe> extends FoxBaseInvTile implements ISparkAttachable {
    private int mana, maxMana = 1000000, progress;
    private final FoxInternalInventory inv = new FoxInternalInventory(this, getInvSize());
    private final FoxInternalInventory output = new FoxInternalInventory(this, getInvOutSize());

    public TileMechanicManaBlock() {

    }

    protected T currentRecipe = null;

    protected final List<Integer> usedSlots = new ArrayList<>();

    @TileEvent(TileEventType.TICK)
    public void tick() {
        updateRecipeIfNeeded();

        if (currentRecipe != null && mana >= currentRecipe.getManaUsage()) {
            progress += 1;

            if (progress >= getSpeed()) {
                int minusMana = currentRecipe.getManaUsage();
                if (!worldObj.isRemote) {
                    craftRecipe();
                }
                currentRecipe = null;
                mana -= minusMana;
                progress = 0;
            }

        } else {
            progress = 0;
        }
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        markForUpdate();
    }

    protected void craftRecipe() {
        if (currentRecipe == null)
            return;

        for (int slot : usedSlots) {
            if (inv.getStackInSlot(slot) != null) {
                ItemStack stack = inv.getStackInSlot(slot);

                if (stack.stackSize <= 0)
                    inv.setInventorySlotContents(slot, null);
            }
        }

        ItemStack out = currentRecipe.getOutput().copy();
        insertOutput(out);

        for (int slot : usedSlots) {
            ItemStack stack = inv.getStackInSlot(slot);
            consumeItem(stack);
            if (stack != null) {
                insertOutput(stack.copy());
            }
        }
        updateCountInStacks();
    }

    protected void updateCountInStacks() {
        List<InvEntry> entries = new LinkedList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                for (InvEntry entry : entries) {
                    if (entry.stackEquals(stack) && !entry.stack.hasTagCompound() && entry.stack.stackSize < 64) {

                        int need = 64 - entry.stack.stackSize;
                        entry.stack.stackSize += Math.min(need, stack.stackSize);
                        stack.stackSize -= Math.min(need, stack.stackSize);
                    }
                }
                if (stack.stackSize <= 0) {
                    inv.setInventorySlotContents(i, null);
                } else {
                    entries.add(new InvEntry(i, stack));
                }
            }
        }
    }

    protected void consumeItem(ItemStack stack) {
        for (Object o : currentRecipe.getInputs()) {
            if (o instanceof ItemStack temp) {
                if (temp.getItem() == stack.getItem() && temp.getItemDamage() == stack.getItemDamage()) {
                    stack.stackSize -= temp.stackSize;
                    break;
                }
            } else if (o instanceof String) {
                if (OreDictUtil.areStacksEqual(o, stack)) {
                    stack.stackSize--;
                }
            } else if (o instanceof StackOreDict temp) {
                if (OreDictUtil.areStacksEqual(temp.getOre(), stack)) {
                    stack.stackSize -= temp.getCount();
                }
            }
        }
    }

    protected void insertOutput(ItemStack stack) {
        for (int i = 0; i < getInvOutSize(); i++) {
            if (output.getStackInSlot(i) == null) {
                output.setInventorySlotContents(i, stack);
                return;
            }
            if (output.getStackInSlot(i).isItemEqual(stack) &&
                output.getStackInSlot(i).stackSize + stack.stackSize <= output.getStackInSlot(i).getMaxStackSize()) {
                output.getStackInSlot(i).stackSize += stack.stackSize;
                return;
            }
        }
    }

    protected void updateRecipeIfNeeded() {
        usedSlots.clear();

        List<InvEntry> inputs = new ArrayList<>();

        for (int i = 0; i < getInvSize(); i++) {
            ItemStack stack = getInternalInventory().getStackInSlot(i);
            if (stack != null) {
                inputs.add(new InvEntry(i, stack));
            }
        }

        currentRecipe = getRecipe(inputs);
    }

    protected T getRecipe(List<InvEntry> entries) {

        usedSlots.clear();

        for (T r : getRecipes()) {
            List<Integer> matchedSlots = new ArrayList<>();
            List<ItemStack> matchedStacks = matchesStacks(new ArrayList<>(r.getInputs()), matchedSlots, entries);

            if (matchedStacks != null) {
                InventoryBasic inv = new InventoryBasic("null", false, matchedStacks.size());
                for (int i = 0; i < matchedStacks.size(); i++)
                    inv.setInventorySlotContents(i, matchedStacks.get(i));

                if (r.upgradedMatches(inv, true)) {

                    usedSlots.addAll(matchedSlots);
                    return r;
                }
            }
        }

        return null;
    }

    protected abstract List<T> getRecipes();

    protected T getRecipe(List<InvEntry> entries, Object... params) {
        return getRecipe(entries);
    }

    protected List<ItemStack> matchesStacks(List<Object> required, List<Integer> matchedSlots, List<InvEntry> entries) {
        List<ItemStack> matchedStacks = new ArrayList<>();

        List<InvEntry> available = new ArrayList<>(entries);

        matchLoop:
        for (Object req : required) {
            for (int i = 0; i < available.size(); i++) {

                InvEntry e = available.get(i);
                if (e == null) continue;

                if (matchesIngredient(req, e.stack)) {
                    matchedStacks.add(e.stack);
                    matchedSlots.add(e.slot);
                    available.set(i, null);
                    continue matchLoop;
                }
            }
            matchedStacks = null;
            matchedSlots = null;
            break;
        }
        return matchedStacks;
    }

    protected boolean matchesIngredient(Object req, ItemStack stack) {
        if (req instanceof ItemStack st) {
            return OreDictionary.itemMatches(st, stack, false);
        }
        if (req instanceof String ore) {
            int id = OreDictionary.getOreID(ore);
            for (ItemStack s : OreDictionary.getOres(id)) {
                if (OreDictionary.itemMatches(s, stack, false))
                    return true;
            }
        }
        if (req instanceof List<?> list) {
            for (Object o : list) {
                if (matchesIngredient(o, stack)) return true;
            }
        }
        if (req instanceof StackOreDict ore) {
            return ore.check(stack, false);
        }
        return false;
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        output.readFromNBT(data, "output");
        mana = data.getInteger("mana");
        maxMana = data.getInteger("maxMana");
        progress = data.getInteger("progress");
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        output.writeToNBT(data, "output");
        data.setInteger("mana", mana);
        data.setInteger("maxMana", maxMana);
        data.setInteger("progress", progress);
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        data.writeInt(mana);
        data.writeInt(maxMana);
        data.writeInt(progress);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        int oldMana = mana, oldMaxMana = maxMana, oldProgress = progress;
        mana = data.readInt();
        maxMana = data.readInt();
        progress = data.readInt();
        return oldMana != mana && oldMaxMana != maxMana && oldProgress != progress;
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inv;
    }

    public FoxInternalInventory getOutputInventory() {
        return output;
    }

    @Override
    public int getSizeInventory() {
        return inv.getSizeInventory() + output.getSizeInventory();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot < inv.getSizeInventory();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot >= inv.getSizeInventory();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < inv.getSizeInventory()) {
            return inv.decrStackSize(slot, amount);
        }
        return output.decrStackSize(slot - inv.getSizeInventory(), amount);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < inv.getSizeInventory()) {
            inv.setInventorySlotContents(slot, stack);
        } else {
            output.setInventorySlotContents(slot - inv.getSizeInventory(), stack);
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < inv.getSizeInventory())
            return inv.getStackInSlot(slot);

        return output.getStackInSlot(slot - inv.getSizeInventory());
    }


    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        int[] sides = new int[getSizeInventory()];
        for (int i = 0; i < getSizeInventory(); i++) {
            sides[i] = i;
        }
        return sides;
    }

    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        return Math.max(0, maxMana - this.getCurrentMana());
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 1), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity)sparks.get(0);
            return (ISparkEntity)e;
        } else {
            return null;
        }
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public boolean isFull() {
        return this.mana >= this.maxMana;
    }

    @Override
    public void recieveMana(int i) {
        if (this.mana < this.maxMana) {
            this.mana += i;
            if (this.mana > this.maxMana) {
                this.mana = this.maxMana;
            }
        }
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return this.mana < this.maxMana;
    }

    @Override
    public int getCurrentMana() {
        return this.mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void renderHUD(Minecraft mc, ScaledResolution res) {
        ItemStack pool = new ItemStack(this.getBlockType());
        String name = StatCollector.translateToLocal(pool.getUnlocalizedName());
        int color = 4474111;
        HUDHandler.drawSimpleManaHUD(color, this.mana, maxMana, name, res);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        mc.renderEngine.bindTexture(HUDHandler.manaBar);
    }

    public int getProgress() {
        return progress;
    }

    protected abstract int getInvSize();
    protected abstract int getInvOutSize();
    protected abstract int getSpeed();

    protected static class InvEntry {
        public final int slot;
        public final ItemStack stack;

        public InvEntry(int slot, ItemStack stack) {
            this.slot = slot;
            this.stack = stack;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof InvEntry entry) {
                return slot == entry.slot && stack.getItem() == entry.stack.getItem() && stack.getItemDamage() == entry.stack.getItemDamage();
            }
            return false;
        }

        public boolean stackEquals(ItemStack stack) {
            return this.stack.getItem() == stack.getItem() && this.stack.getItemDamage() == stack.getItemDamage();
        }
    }
}
