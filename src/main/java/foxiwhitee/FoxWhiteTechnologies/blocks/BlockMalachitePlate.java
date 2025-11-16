package foxiwhitee.FoxWhiteTechnologies.blocks;

import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.item.ModItems;

public class BlockMalachitePlate extends FoxBaseBlock {
    public static IIcon overlay;
    IIcon[] icons;

    public BlockMalachitePlate(String name) {
        super(FoxWTCore.MODID, name);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
        setTileEntityType(TileMalachitePlate.class);
        setCreativeTab(FoxWTCore.TAB);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xs, float ys, float zs) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null)
            return false;

        ItemStack drop = stack.copy();
        drop.stackSize = 1;

        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
            if (stack.stackSize <= 0)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        }

        EntityItem item = new EntityItem(world,
            x + 0.5, y + 0.5, z + 0.5,
            drop);

        item.delayBeforeCanPickup = 40;
        item.motionX = item.motionY = item.motionZ = 0;

        if (!world.isRemote)
            world.spawnEntityInWorld(item);

        return true;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess w, int x, int y, int z) {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[3];
        for (int i = 0; i < icons.length; i++)
            icons[i] = reg.registerIcon(FoxWTCore.MODID + ":plate/" + name + i);

        overlay = reg.registerIcon(FoxWTCore.MODID + ":plate/" + name + "Overlay");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[Math.min(2, side)];
    }

    @Override public boolean hasComparatorInputOverride() { return true; }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileMalachitePlate plate = (TileMalachitePlate) world.getTileEntity(x, y, z);
        if (plate == null) return 0;

        float f = (float) plate.getCurrentMana() / TileMalachitePlate.MAX_MANA;
        int redstone = (int) (f * 15F);
        if (plate.getCurrentMana() > 0)
            redstone = Math.max(1, redstone);

        return redstone;
    }
}
