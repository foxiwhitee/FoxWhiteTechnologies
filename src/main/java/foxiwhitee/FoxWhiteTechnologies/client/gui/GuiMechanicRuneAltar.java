package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.inventory.Container;

public class GuiMechanicRuneAltar extends GuiMechanicBlock {
    public GuiMechanicRuneAltar(ContainerMechanicRuneAltar container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicRuneAltar.png";
    }

}
