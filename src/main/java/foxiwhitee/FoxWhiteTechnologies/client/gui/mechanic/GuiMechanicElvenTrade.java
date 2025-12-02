package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicElvenTrade;

public class GuiMechanicElvenTrade extends GuiMechanicBlock {
    public GuiMechanicElvenTrade(ContainerMechanicElvenTrade container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicElvenTrade.png";
    }

}
