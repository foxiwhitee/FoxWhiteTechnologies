package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicPureDaisy;

public class GuiMechanicPureDaisy extends GuiMechanicBlock {
    public GuiMechanicPureDaisy(ContainerMechanicPureDaisy container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicPureDaisy.png";
    }

}
