package foxiwhitee.FoxWhiteTechnologies.tile.pools;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileMidgardManaPool extends TileCustomManaPool{
    @Override
    public int getMaxMana() {
        return WTConfig.manaMidgardPool;
    }

    @Override
    public String getName() {
        return ModBlocks.MIDGARD_POOL.getUnlocalizedName().replace("tile.", "");
    }
}
