package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicRuneAltar;

public class GuiMechanicRuneAltar extends GuiMechanicBlock {
    public GuiMechanicRuneAltar(ContainerMechanicRuneAltar container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicRuneAltar.png";
    }

}
