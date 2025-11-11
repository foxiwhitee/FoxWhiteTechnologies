package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.tile.FoxBaseInvTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxWhiteTechnologies.recipes.IBotanyManaRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.client.core.handler.HUDHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TileMechanicManaBlock<T extends IBotanyManaRecipe> extends FoxBaseInvTile implements ISparkAttachable {
    private int mana, maxMana, progress;
    private final FoxInternalInventory inv = new FoxInternalInventory(this, getInvSize());
    private final FoxInternalInventory output = new FoxInternalInventory(this, getInvOutSize());

    public TileMechanicManaBlock() {

    }

    private T currentRecipe = null;

    private final List<Integer> usedSlots = new ArrayList<>();

    @TileEvent(TileEventType.TICK)
    protected void tick() {
        if (worldObj.isRemote) {
            return;
        }
        updateRecipeIfNeeded();

        if (currentRecipe != null && mana >= currentRecipe.getManaUsage()) {
            progress += 1;

            if (progress >= getSpeed()) {
                craftRecipe();
                mana -= currentRecipe.getManaUsage();
                progress = 0;
            }

        } else {
            progress = 0;
        }
    }

    protected void craftRecipe() {
        if (currentRecipe == null)
            return;

        for (int slot : usedSlots) {
            if (inv.getStackInSlot(slot) != null) {
                if (!inv.getStackInSlot(slot).getItem().getUnlocalizedName().contains("rune"))
                    inv.getStackInSlot(slot).stackSize--;
                if (inv.getStackInSlot(slot).stackSize <= 0)
                    inv.setInventorySlotContents(slot, null);
            }
        }

        if (inv.getStackInSlot(0) != null) {
            inv.getStackInSlot(0).stackSize--;
            if (inv.getStackInSlot(0).stackSize <= 0)
                inv.setInventorySlotContents(0, null);
        }

        ItemStack out = currentRecipe.getOutput().copy();
        insertOutput(out);

        for (int slot : usedSlots) {
            ItemStack stack = inv.getStackInSlot(slot);
            if (stack != null && stack.getItem().getUnlocalizedName().contains("rune")) {
                insertOutput(stack.copy());
            }
        }


        currentRecipe = null;
    }

    private void insertOutput(ItemStack stack) {
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

        if (getInternalInventory().getStackInSlot(0) == null) {
            currentRecipe = null;
            return;
        }

        List<ItemStack> inputs = new ArrayList<>();

        for (int i = 0; i < getInvSize(); i++) {
            if (getInternalInventory().getStackInSlot(i) != null) {
                inputs.add(getInternalInventory().getStackInSlot(i));
                usedSlots.add(i);
            }
        }

        currentRecipe = getRecipe(inputs);
    }

    protected abstract T getRecipe(List<ItemStack> stacks);

    protected T getRecipe(List<ItemStack> stacks, Object... params) {
        return getRecipe(stacks);
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        data.setInteger("mana", mana);
        data.setInteger("maxMana", maxMana);
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        mana = data.getInteger("mana");
        maxMana = data.getInteger("maxMana");
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    private void writeToStream(ByteBuf data) {
        data.writeInt(mana);
        data.writeInt(maxMana);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    private void readFromStream(ByteBuf data) {
        mana = data.readInt();
        maxMana = data.readInt();
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inv;
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
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

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
        return Math.max(0, 100000000 - this.getCurrentMana());
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

    public void renderHUD(Minecraft mc, ScaledResolution res) {
        ItemStack pool = new ItemStack(this.getBlockType());
        String name = StatCollector.translateToLocal(pool.getUnlocalizedName());
        int color = 4474111;
        HUDHandler.drawSimpleManaHUD(color, this.mana, maxMana, name, res);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        mc.renderEngine.bindTexture(HUDHandler.manaBar);
    }

    protected abstract int getInvSize();
    protected abstract int getInvOutSize();

    protected abstract int getSpeed();
}
