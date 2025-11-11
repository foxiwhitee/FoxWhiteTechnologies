package foxiwhitee.FoxWhiteTechnologies.tile.spreaders;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileHelhelmSpreader extends TileCustomSpreader{
    @Override
    public String getName() {
        return ModBlocks.HELHELM_SPREADER.getUnlocalizedName().replace("tile.", "");
    }

    @Override
    public int getManaPerSec() {
        return WTConfig.manaPerSecHelhelmSpreader;
    }
}
