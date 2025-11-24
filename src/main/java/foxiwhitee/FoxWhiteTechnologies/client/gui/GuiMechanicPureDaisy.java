package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicPureDaisy;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;

public class GuiMechanicPureDaisy extends GuiMechanicBlock {
    public GuiMechanicPureDaisy(ContainerMechanicPureDaisy container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicPureDaisy.png";
    }

}
