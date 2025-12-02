package foxiwhitee.FoxWhiteTechnologies.container.mechanic;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicRuneAltar extends ContainerMechanicBlock {

    public ContainerMechanicRuneAltar(EntityPlayer ip, TileMechanicRuneAltar myTile) {
        super(ip, myTile, "noLivingRock", "mechanicManaBlockUpgrade", 1);

        addSlotToContainer(new SlotFiltered("livingRock", myTile.getInternalInventory(), 0, 125, 67, ip.inventory));
    }
}
