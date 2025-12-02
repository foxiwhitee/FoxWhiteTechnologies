package foxiwhitee.FoxWhiteTechnologies.container.mechanic;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicManaPool;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicManaPool extends ContainerMechanicBlock {

    public ContainerMechanicManaPool(EntityPlayer ip, TileMechanicManaPool myTile) {
        super(ip, myTile, "noCatalyst", "mechanicManaBlockUpgrade", 1);

        addSlotToContainer(new SlotFiltered("catalyst", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));
    }
}
