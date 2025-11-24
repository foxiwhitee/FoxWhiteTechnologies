package foxiwhitee.FoxWhiteTechnologies.container;

import cpw.mods.fml.common.FMLCommonHandler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.container.slots.SlotPlayerHotBar;
import foxiwhitee.FoxLib.container.slots.SlotPlayerInv;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class ContainerMechanicRuneAltar extends ContainerMechanicBlock {

    public ContainerMechanicRuneAltar(EntityPlayer ip, TileMechanicRuneAltar myTile) {
        super(ip, myTile, "noLivingRock", "mechanicManaBlockUpgrade", 1);

        addSlotToContainer(new SlotFiltered("livingRock", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));
    }
}
