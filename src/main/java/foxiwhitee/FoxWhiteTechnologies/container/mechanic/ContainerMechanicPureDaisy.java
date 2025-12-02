package foxiwhitee.FoxWhiteTechnologies.container.mechanic;

import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPureDaisy;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicPureDaisy extends ContainerMechanicBlock {

    public ContainerMechanicPureDaisy(EntityPlayer ip, TileMechanicPureDaisy myTile) {
        super(ip, myTile, "mechanicPureDaisyUpgrade", 0);
    }
}
