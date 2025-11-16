package foxiwhitee.FoxWhiteTechnologies.entity;

import net.minecraft.world.World;

public class ValhallaSpark extends CustomSpark{
    public ValhallaSpark(World world) {
        super(world);
    }

    @Override
    public Type getType() {
        return Type.VALHALLA;
    }
}
