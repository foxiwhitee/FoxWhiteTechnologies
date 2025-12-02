package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicManaPool;

public class GuiMechanicManaPool extends GuiMechanicBlock {
    public GuiMechanicManaPool(ContainerMechanicManaPool container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicManaPool.png";
    }

}
