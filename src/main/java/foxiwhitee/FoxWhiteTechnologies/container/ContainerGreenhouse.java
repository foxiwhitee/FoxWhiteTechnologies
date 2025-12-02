package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.TileGreenhouse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerGreenhouse extends FoxBaseContainer {
    public ContainerGreenhouse(EntityPlayer ip, TileGreenhouse myTile) {
        super(ip.inventory, myTile);

        bindPlayerInventory(ip.inventory, 53, 184);

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotFiltered("greenhouseUpgrade", myTile.getUpgradesInventory(), l, 107 + 18 * l, 151, ip.inventory));
        }

        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotFiltered("flammableMaterial", myTile.getInternalInventory(), l, 35 + 18 * l, 59, ip.inventory));
        }
        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotFiltered("tnt", myTile.getInternalInventory(), l + 3, 107 + 18 * l, 59, ip.inventory));
        }
        for (int l = 0; l < 3; l++) {
            addSlotToContainer(new SlotFiltered("food", myTile.getInternalInventory(), l + 6, 179 + 18 * l, 59, ip.inventory));
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new SlotFiltered("endoflames", myTile.getInternalInventory(), j + i * 3, 35 + j * 18, 85 + i * 18, ip.inventory));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new SlotFiltered("entropinnyums", myTile.getInternalInventory(), j + i * 3 + 9, 107 + j * 18, 85 + i * 18, ip.inventory));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlotToContainer(new SlotFiltered("gourmaryllises", myTile.getInternalInventory(), j + i * 3 + 18, 179 + j * 18, 85 + i * 18, ip.inventory));
            }
        }
    }
}
