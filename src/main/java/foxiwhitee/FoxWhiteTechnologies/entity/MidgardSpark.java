package foxiwhitee.FoxWhiteTechnologies.entity;

import net.minecraft.world.World;

public class MidgardSpark extends CustomSpark{
    public MidgardSpark(World world) {
        super(world);
    }

    @Override
    public Type getType() {
        return Type.MIDGARD;
    }
}
