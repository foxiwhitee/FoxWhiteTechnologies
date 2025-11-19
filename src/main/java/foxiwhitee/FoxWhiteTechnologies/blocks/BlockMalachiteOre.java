package foxiwhitee.FoxWhiteTechnologies.blocks;

import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMalachiteOre extends StaticBlock {
    public BlockMalachiteOre(String name) {
        super(name);
        setHarvestLevel("pickaxe", 3);
        setBlockTextureName(FoxWTCore.MODID + ":" + name);
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta) {
        return true;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return ModItems.MALACHITE;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1 + rand.nextInt(3);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        if (fortune > 0 && this.getItemDropped(0, rand, fortune) != Item.getItemFromBlock(this)) {
            int bonus = rand.nextInt(fortune + 2) - 1;
            if (bonus < 0) bonus = 0;

            return this.quantityDropped(rand) * (bonus + 1);
        }

        return quantityDropped(rand);
    }
}
