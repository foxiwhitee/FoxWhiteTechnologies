package foxiwhitee.FoxWhiteTechnologies.tile.pools;

import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;

public class TileHelheimManaPool extends TileCustomManaPool{
    @Override
    public int getMaxMana() {
        return WTConfig.manaHelheimPool;
    }

    @Override
    public String getName() {
        return ModBlocks.HELHEIM_POOL.getUnlocalizedName().replace("tile.", "");
    }
}
