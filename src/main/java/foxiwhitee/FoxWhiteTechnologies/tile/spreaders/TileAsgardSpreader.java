package foxiwhitee.FoxWhiteTechnologies.tile.spreaders;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileAsgardSpreader extends TileCustomSpreader{
    @Override
    public String getName() {
        return ModBlocks.ASGARD_SPREADER.getUnlocalizedName().replace("tile.", "");
    }

    @Override
    public int getManaPerSec() {
        return WTConfig.manaPerSecAsgardSpreader;
    }
}
