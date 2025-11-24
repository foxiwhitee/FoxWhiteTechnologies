package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPureDaisy;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicPureDaisy extends ContainerMechanicBlock {

    public ContainerMechanicPureDaisy(EntityPlayer ip, TileMechanicPureDaisy myTile) {
        super(ip, myTile, "mechanicPureDaisyUpgrade", 0);
    }
}
