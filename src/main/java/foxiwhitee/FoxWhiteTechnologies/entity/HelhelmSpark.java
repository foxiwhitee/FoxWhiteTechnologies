package foxiwhitee.FoxWhiteTechnologies.entity;

import net.minecraft.world.World;

public class HelhelmSpark extends CustomSpark{
    public HelhelmSpark(World world) {
        super(world);
    }

    @Override
    public Type getType() {
        return Type.HELHELM;
    }
}
