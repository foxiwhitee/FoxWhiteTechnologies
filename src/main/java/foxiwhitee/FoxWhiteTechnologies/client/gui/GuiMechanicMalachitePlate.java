package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;

public class GuiMechanicMalachitePlate extends GuiMechanicBlock {
    public GuiMechanicMalachitePlate(ContainerMechanicMalachitePlate container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicMalachitePlate.png";
    }

}
