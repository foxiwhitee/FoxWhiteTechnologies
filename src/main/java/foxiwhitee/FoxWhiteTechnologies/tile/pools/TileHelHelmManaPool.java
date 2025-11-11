package foxiwhitee.FoxWhiteTechnologies.tile.pools;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileHelHelmManaPool extends TileCustomManaPool{
    @Override
    public int getMaxMana() {
        return WTConfig.manaHelhelmPool;
    }

    @Override
    public String getName() {
        return ModBlocks.HELHELM_POOL.getUnlocalizedName().replace("tile.", "");
    }
}
