package foxiwhitee.FoxWhiteTechnologies.tile.mechanic;

import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.items.*;
import foxiwhitee.FoxWhiteTechnologies.recipes.util.IBotanyManaRecipe;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;

import java.util.*;

public abstract class TileMechanicManaBlock<T extends IBotanyManaRecipe> extends TileMechanicBlock<T> implements ISparkAttachable {
    private int mana, maxMana = 1000000;
    private boolean infinityMana = false;

    public TileMechanicManaBlock() {

    }

    @Override
    protected boolean additionallyConditionForCrafting() {
        return currentRecipe != null && (mana >= currentRecipe.getManaUsage() || infinityMana);
    }

    @Override
    protected void afterCrafting() {
        super.afterCrafting();
        int minusMana = infinityMana ? 0 : currentRecipe.getManaUsage();
        mana -= minusMana;
    }

    @Override
    protected void inventoryCheck(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        super.inventoryCheck(iInventory, i, invOperation, itemStack, itemStack1);
        if (iInventory == getUpgradesInventory()) {
            this.maxMana = 1000000;
            this.infinityMana = false;
            for (int j = 0; j < getUpgradesInventory().getSizeInventory(); j++) {
                ItemStack stack = getUpgradesInventory().getStackInSlot(j);
                if (stack != null && stack.getItem() instanceof ItemStorageUpgrade) {
                    int temp = switch (stack.getItemDamage()) {
                        case 0 -> WTConfig.manaMidgardPool;
                        case 1 -> WTConfig.manaValhallaPool;
                        case 2 -> WTConfig.manaHelheimPool;
                        case 3 -> WTConfig.manaAsgardPool;
                        default -> 0;
                    };
                    this.maxMana = (int)Math.min((long)Integer.MAX_VALUE, (long)this.maxMana + (long)temp);
                }
                if (stack != null && stack.getItem() instanceof ItemInfinityManaUpgrade) {
                    this.infinityMana = true;
                }
            }
            this.mana = Math.min(this.mana, this.maxMana);
            if (mana < 0) {
                mana = 0;
            }
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
        mana = data.getInteger("mana");
        maxMana = data.getInteger("maxMana");
        infinityMana = data.getBoolean("infinityMana");
        markForUpdate();
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
        data.setInteger("mana", mana);
        data.setInteger("maxMana", maxMana);
        data.setBoolean("infinityMana", infinityMana);
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(mana);
        data.writeInt(maxMana);
        data.writeBoolean(infinityMana);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        int oldMana = mana, oldMaxMana = maxMana;
        boolean oldInfMana = infinityMana, superRead = super.readFromStream(data);
        mana = data.readInt();
        maxMana = data.readInt();
        infinityMana = data.readBoolean();
        return oldMana != mana || oldMaxMana != maxMana || oldInfMana != infinityMana || superRead;
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
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox((double) this.xCoord, (double) (this.yCoord + 1), (double) this.zCoord, (double) (this.xCoord + 1), (double) (this.yCoord + 2), (double) (this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity) sparks.get(0);
            return (ISparkEntity) e;
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

}
