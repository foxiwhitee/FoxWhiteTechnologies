package foxiwhitee.FoxWhiteTechnologies.client.gui;

import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.container.ContainerMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.inventory.Container;

public class GuiMechanicRuneAltar extends FoxBaseGui {
    private final ContainerMechanicRuneAltar container;
    public GuiMechanicRuneAltar(ContainerMechanicRuneAltar container) {
        super(container, 266, 296);
        this.container = container;
        setModID(FoxWTCore.MODID);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicRuneAltar.png";
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        TileMechanicRuneAltar tile = (TileMechanicRuneAltar) container.getTileEntity();
        if (tile.getProgress() > 0) {
            double l = ProductivityUtil.gauge(216, tile.getProgress(), 20 * WTConfig.speedRuneAltar);
            UtilGui.drawTexture(offsetX + 25, offsetY + 171, 0, 323, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6);
        }
    }
}
