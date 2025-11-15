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

public class ContainerMechanicRuneAltar extends FoxBaseContainer {

    public ContainerMechanicRuneAltar(EntityPlayer ip, TileMechanicRuneAltar myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 45, 214);

        addSlotToContainer(new SlotFiltered("livingRock", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 4; ++j) {
                addSlotToContainer(new SlotFiltered("noLivingRock", myTile.getInternalInventory(), j + i * 4 + 1, 35 + j * 18, 59 + i * 18, ip.inventory));
            }
        }

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 4; ++j) {
                addSlotToContainer(new Slot(myTile.getOutputInventory(), j + i * 4, 161 + j * 18, 59 + i * 18) {
                    @Override
                    public boolean isItemValid(ItemStack stack) {
                        return false;
                    }
                });
            }
        }

    }

}
