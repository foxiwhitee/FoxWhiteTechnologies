package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicManaPool extends ContainerMechanicBlock {

    public ContainerMechanicManaPool(EntityPlayer ip, TileMechanicManaPool myTile) {
        super(ip, myTile, "noCatalyst", "mechanicManaBlockUpgrade", 1);

        addSlotToContainer(new SlotFiltered("catalyst", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));
    }
}
