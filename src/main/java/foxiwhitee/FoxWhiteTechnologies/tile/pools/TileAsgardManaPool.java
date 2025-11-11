package foxiwhitee.FoxWhiteTechnologies.tile.pools;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileAsgardManaPool extends TileCustomManaPool{
    @Override
    public int getMaxMana() {
        return WTConfig.manaAsgardPool;
    }

    @Override
    public String getName() {
        return ModBlocks.ASGARD_POOL.getUnlocalizedName().replace("tile.", "");
    }
}
