package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerGreenhouse;
import net.minecraft.inventory.Container;

public class GuiGreenhouse extends FoxBaseGui {
    public GuiGreenhouse(ContainerGreenhouse container) {
        super(container, 266, 266);
        setModID(FoxWTCore.MODID);
    }

    @Override
    protected String getBackground() {
        return "gui/guiGreenhouse.png";
    }
}
