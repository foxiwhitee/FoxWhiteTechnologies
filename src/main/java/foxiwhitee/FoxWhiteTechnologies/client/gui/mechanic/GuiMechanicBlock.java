package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicBlock;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicBlock;

public abstract class GuiMechanicBlock extends FoxBaseGui {
    protected final ContainerMechanicBlock container;
    public GuiMechanicBlock(ContainerMechanicBlock container) {
        super(container, 266, 296);
        this.container = container;
        setModID(FoxWTCore.MODID);
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        TileMechanicBlock<?> tile = (TileMechanicBlock<?>) container.getTileEntity();
        if (tile.getProgress() > 0) {
            double l = ProductivityUtil.gauge(216, tile.getProgress(), tile.getRealSpeed());
            UtilGui.drawTexture(offsetX + 25, offsetY + 171, 0, 323, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6);
        }
        if (tile.getProductivity() > 0) {
            double l = ProductivityUtil.gaugeProductivityProgressBar(tile.getProgress(), tile.getProductivity(), tile.getProgressProductivity(), 176, tile.getRealSpeed());
            if (l > 176) {
                l = l % 176;
            }
            UtilGui.drawTexture(offsetX + 45, offsetY + 182, 0, 330, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6);
        }
    }
}
