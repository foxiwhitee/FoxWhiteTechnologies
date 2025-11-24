package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicManaPool;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;

public class GuiMechanicManaPool extends GuiMechanicBlock {
    public GuiMechanicManaPool(ContainerMechanicManaPool container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicManaPool.png";
    }

}
