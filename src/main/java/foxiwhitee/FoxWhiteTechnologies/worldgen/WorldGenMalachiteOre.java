package foxiwhitee.FoxWhiteTechnologies.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenMalachiteOre implements IWorldGenerator {
    public WorldGenMalachiteOre() {

    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

        if (world.provider.dimensionId != 0) return;

        generateOverworld(world, rand, chunkX * 16, chunkZ * 16);
    }

    private void generateOverworld(World world, Random rand, int x, int z) {
        this.addOreSpawn(ModBlocks.MALACHITE_ORE, world, rand, x, z, 3, 5, 2, 1, 16);
    }

    public void addOreSpawn(Block block, World world, Random random, int chunkXPos, int chunkZPos, int minVainSize, int maxVainSize, int chancesToSpawn, int minY, int maxY) {
        for(int i = 0; i < chancesToSpawn; ++i) {
            int posX = chunkXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = chunkZPos + random.nextInt(16);
            (new WorldGenMinable(block, 0, minVainSize + random.nextInt(maxVainSize - minVainSize), Blocks.stone)).generate(world, random, posX, posY, posZ);
        }
    }

}
