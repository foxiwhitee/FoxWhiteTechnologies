package foxiwhitee.FoxWhiteTechnologies.tile.spreaders;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileMidgardSpreader extends TileCustomSpreader{
    @Override
    public String getName() {
        return ModBlocks.MIDGARD_SPREADER.getUnlocalizedName().replace("tile.", "");
    }

    @Override
    public int getManaPerSec() {
        return WTConfig.manaPerSecMidgardSpreader;
    }
}
