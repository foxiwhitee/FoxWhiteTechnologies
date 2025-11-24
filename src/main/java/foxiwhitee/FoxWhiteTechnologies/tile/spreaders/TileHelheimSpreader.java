package foxiwhitee.FoxWhiteTechnologies.tile.spreaders;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileHelheimSpreader extends TileCustomSpreader{
    @Override
    public String getName() {
        return ModBlocks.HELHEIM_SPREADER.getUnlocalizedName().replace("tile.", "");
    }

    @Override
    public int getManaPerSec() {
        return WTConfig.manaPerSecHelheimSpreader;
    }
}
