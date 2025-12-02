package foxiwhitee.FoxWhiteTechnologies.tile;

import foxiwhitee.FoxLib.tile.FoxBaseInvTile;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileGreenhouse extends FoxBaseInvTile {
    private static ItemStack endoflame;
    private static ItemStack entropinnyum;
    private static ItemStack gourmaryllis;
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 9);
    private final FoxInternalInventory flowers = new FoxInternalInventory(this, 27);

    public TileGreenhouse() {
        if (endoflame == null || entropinnyum == null || gourmaryllis == null) {
            initFlowers();
        }
    }

    private static void initFlowers() {
        endoflame = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "endoflame");
        endoflame.setTagCompound(tag);
        entropinnyum = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        tag = new NBTTagCompound();
        tag.setString("type", "entropinnyum");
        entropinnyum.setTagCompound(tag);
        gourmaryllis = new ItemStack(vazkii.botania.common.block.ModBlocks.specialFlower);
        tag = new NBTTagCompound();
        tag.setString("type", "gourmaryllis");
        gourmaryllis.setTagCompound(tag);
    }

    @TileEvent(TileEventType.TICK)
    public void tick() {

    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {

    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        super.readFromNBT_(data);
    }

    @Override
    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        super.writeToNBT_(data);
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    public boolean readFromStream(ByteBuf data) {
        return true;
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    public void writeToStream(ByteBuf data) {

    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    }
}
