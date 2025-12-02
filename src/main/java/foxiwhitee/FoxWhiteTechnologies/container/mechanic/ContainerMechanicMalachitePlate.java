package foxiwhitee.FoxWhiteTechnologies.container.mechanic;

import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicMalachitePlate;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicMalachitePlate extends ContainerMechanicBlock {

    public ContainerMechanicMalachitePlate(EntityPlayer ip, TileMechanicMalachitePlate myTile) {
        super(ip, myTile, "mechanicManaBlockUpgrade", 0);
    }
}
