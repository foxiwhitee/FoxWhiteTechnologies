package foxiwhitee.FoxWhiteTechnologies.client.gui.mechanic;

import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import foxiwhitee.FoxWhiteTechnologies.container.mechanic.ContainerMechanicPetals;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicPetals;

public class GuiMechanicPetals extends GuiMechanicBlock {
    public GuiMechanicPetals(ContainerMechanicPetals container) {
        super(container);
    }

    @Override
    protected String getBackground() {
        return "gui/guiMechanicPetals.png";
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        TileMechanicPetals tile = (TileMechanicPetals) container.getTileEntity();
        int count = 0;
        if (tile.getTankInfo(null)[0].fluid != null) {
            count = tile.getTankInfo(null)[0].fluid.amount;
        }
        if (tile.isInfinityWater() || count > 0) {
            double l;
            if (tile.isInfinityWater()) {
                l = 91;
            } else {
                l = ProductivityUtil.gauge(92, count, TileMechanicPetals.TANK_CAPACITY);
            }

            UtilGui.drawTexture(offsetX + 87, offsetY + 194, 0, 337, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6);
        }
    }
}
