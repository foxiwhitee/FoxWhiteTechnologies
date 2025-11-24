package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicPetals extends ContainerMechanicBlock {

    public ContainerMechanicPetals(EntityPlayer ip, TileMechanicPetals myTile) {
        super(ip, myTile, "noSeeds", "mechanicPetalsUpgrade", 1);

        addSlotToContainer(new SlotFiltered("seeds", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));
    }
}
