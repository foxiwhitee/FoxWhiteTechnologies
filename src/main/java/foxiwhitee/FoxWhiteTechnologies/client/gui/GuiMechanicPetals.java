package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;

public class GuiMechanicPetals extends GuiMechanicBlock {
    public GuiMechanicPetals(ContainerMechanicPetals container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicPetals.png";
    }

}
