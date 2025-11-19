package foxiwhitee.FoxWhiteTechnologies.world;

import cpw.mods.fml.common.IWorldGenerator;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenMalachiteOre implements IWorldGenerator {

    private final WorldGenMinable oreGen;

    public WorldGenMalachiteOre() {
        oreGen = new WorldGenMinable(ModBlocks.MALACHITE_ORE, 4);
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

        if (world.provider.dimensionId != 0) return;

        generateOverworld(world, rand, chunkX * 16, chunkZ * 16);
    }

    private void generateOverworld(World world, Random rand, int x, int z) {
        int baseAttempts = 1;

        if (rand.nextInt(5) != 0) return;

        for (int i = 0; i < baseAttempts; i++) {

            int posX = x + rand.nextInt(16);
            int posZ = z + rand.nextInt(16);

            int posY = getWeightedHeight(rand);

            oreGen.generate(world, rand, posX, posY, posZ);
        }
    }

    private int getWeightedHeight(Random rand) {
        double r = rand.nextDouble();
        double weighted = 1.0 - Math.sqrt(r);
        return (int) (1 + weighted * 254);
    }
}
