package foxiwhitee.FoxWhiteTechnologies.container.mechanic;

import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicElvenTrade;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicElvenTrade extends ContainerMechanicBlock {

    public ContainerMechanicElvenTrade(EntityPlayer ip, TileMechanicElvenTrade myTile) {
        super(ip, myTile, "mechanicManaBlockUpgrade", 0);
    }
}
