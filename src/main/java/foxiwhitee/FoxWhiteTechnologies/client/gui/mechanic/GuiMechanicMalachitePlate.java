package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicMalachitePlate;

public class GuiMechanicMalachitePlate extends GuiMechanicBlock {
    public GuiMechanicMalachitePlate(ContainerMechanicMalachitePlate container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicMalachitePlate.png";
    }

}
