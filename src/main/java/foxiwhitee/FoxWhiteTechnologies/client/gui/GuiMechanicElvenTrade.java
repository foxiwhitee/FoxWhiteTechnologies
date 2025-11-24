package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicElvenTrade;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;

public class GuiMechanicElvenTrade extends GuiMechanicBlock {
    public GuiMechanicElvenTrade(ContainerMechanicElvenTrade container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicElvenTrade.png";
    }

}
