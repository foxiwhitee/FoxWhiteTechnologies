package foxiwhitee.FoxWhiteTechnologies.entity;

import net.minecraft.world.World;

public class HelheimSpark extends CustomSpark{
    public HelheimSpark(World world) {
        super(world);
    }

    @Override
    public Type getType() {
        return Type.HELHEIM;
    }
}
