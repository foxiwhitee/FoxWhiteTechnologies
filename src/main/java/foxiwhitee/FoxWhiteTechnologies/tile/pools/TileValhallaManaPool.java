package foxiwhitee.FoxWhiteTechnologies.tile.pools;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileValhallaManaPool extends TileCustomManaPool{
    @Override
    public int getMaxMana() {
        return WTConfig.manaValhallaPool;
    }

    @Override
    public String getName() {
        return ModBlocks.VALHALLA_POOL.getUnlocalizedName().replace("tile.", "");
    }
}
