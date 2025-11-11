package foxiwhitee.FoxWhiteTechnologies.tile;

import foxiwhitee.FoxLib.tile.FoxBaseTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaPool;

public class TileManaCharger extends FoxBaseTile {
    public ItemStack stack;

    public static int TRANSFER = 10000000;

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound compound) {
        if (this.stack != null) {
            NBTTagCompound tag = new NBTTagCompound();
            this.stack.writeToNBT(tag);
            compound.setTag("stack", (NBTBase)tag);
        }
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound compound) {
        this.stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("stack"));
    }

    @TileEvent(TileEventType.TICK)
    public void tick() {
        if (!this.worldObj.isRemote && this.stack != null) {
            TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
            if (tileEntity instanceof IManaPool pool) {
                IManaItem manaItem = (IManaItem)this.stack.getItem();
                if (pool.isOutputtingPower()) {
                    if (manaItem.canReceiveManaFromPool(this.stack, tileEntity) && manaItem.getMana(this.stack) != manaItem.getMaxMana(this.stack) && pool.getCurrentMana() > 0) {
                        int mana = Math.min(manaItem.getMaxMana(this.stack) - manaItem.getMana(this.stack), TRANSFER);
                        mana = Math.min(pool.getCurrentMana(), mana);
                        pool.recieveMana(-mana);
                        manaItem.addMana(this.stack, mana);
                    }
                } else if (manaItem.canExportManaToPool(this.stack, tileEntity)) {
                    int currentManaInStack = manaItem.getMana(this.stack);
                    if (!pool.isFull() && currentManaInStack > 0) {
                        int mana = Math.min(currentManaInStack, TRANSFER);
                        pool.recieveMana(mana);
                        manaItem.addMana(this.stack, -mana);
                    }
                }
                sync();
            }
        }
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.func_148857_g());
    }

    public void sync() {
        markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
}
