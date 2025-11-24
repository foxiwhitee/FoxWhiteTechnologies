package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicMalachitePlate extends ContainerMechanicBlock {

    public ContainerMechanicMalachitePlate(EntityPlayer ip, TileMechanicMalachitePlate myTile) {
        super(ip, myTile, "mechanicManaBlockUpgrade", 0);
    }
}
