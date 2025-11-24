package foxiwhitee.FoxWhiteTechnologies.container;

import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMechanicElvenTrade extends ContainerMechanicBlock {

    public ContainerMechanicElvenTrade(EntityPlayer ip, TileMechanicElvenTrade myTile) {
        super(ip, myTile, "mechanicManaBlockUpgrade", 0);
    }
}
