package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicBlock;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerMechanicBlock extends FoxBaseContainer {

    public ContainerMechanicBlock(EntityPlayer ip, TileMechanicBlock myTile, String slotFilter, String upgradeFilter, int shift) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 45, 214);

        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 0, 125, 93, ip.inventory));
        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 1, 125, 93 + 18, ip.inventory));
        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 2, 125, 93 + 36, ip.inventory));

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 4; ++j) {
                addSlotToContainer(new SlotFiltered(slotFilter, myTile.getInternalInventory(), j + i * 4 + shift, 35 + j * 18, 59 + i * 18, ip.inventory));
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

    public ContainerMechanicBlock(EntityPlayer ip, TileMechanicBlock myTile, String upgradeFilter, int shift) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 45, 214);

        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 0, 125, 93, ip.inventory));
        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 1, 125, 93 + 18, ip.inventory));
        addSlotToContainer(new SlotFiltered(upgradeFilter, myTile.getUpgradesInventory(), 2, 125, 93 + 36, ip.inventory));

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 4; ++j) {
                addSlotToContainer(new Slot(myTile.getInternalInventory(), j + i * 4 + shift, 35 + j * 18, 59 + i * 18));
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
