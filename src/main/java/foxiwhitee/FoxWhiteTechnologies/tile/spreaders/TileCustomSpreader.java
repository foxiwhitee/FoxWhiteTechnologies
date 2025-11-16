package foxiwhitee.FoxWhiteTechnologies.tile.spreaders;

import net.minecraft.entity.Entity;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

import java.util.Random;
import java.util.UUID;

public abstract class TileCustomSpreader extends TileSpreader {
    public TileCustomSpreader() {}

    public int getMaxMana() {
        return getManaPerSec() * 10;
    }

    public void pingback(IManaBurst burst, UUID expectedIdentity) {
        if (getIdentifier().equals(expectedIdentity)) {
            this.pingbackTicks = 10;
            Entity e = (Entity)burst;
            this.lastPingbackX = e.posX;
            this.lastPingbackY = e.posY;
            this.lastPingbackZ = e.posZ;
            setCanShoot(false);
        }
    }

    public abstract String getName();

    public abstract int getManaPerSec();
}
