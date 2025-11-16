package foxiwhitee.FoxWhiteTechnologies.entity;

import net.minecraft.world.World;

public class AsgardSpark extends CustomSpark{
    public AsgardSpark(World world) {
        super(world);
    }

    @Override
    public Type getType() {
        return Type.ASGARD;
    }

}
