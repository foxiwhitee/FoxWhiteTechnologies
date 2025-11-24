package foxiwhitee.FoxWhiteTechnologies.blocks;

import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileAsgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileHelheimSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileMidgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileValhallaSpreader;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.api.wand.IWireframeAABBProvider;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.item.ModItems;

public class BlockCustomSpreader extends FoxBaseBlock implements ITileEntityProvider, IWandable, IWandHUD, IWireframeAABBProvider {
    public enum Type {MIDGARD, VALHALLA, HELHEIM, ASGARD}

    private final Type type;

    public BlockCustomSpreader(String name, Type type) {
        super(FoxWTCore.MODID, name);
        this.type = type;
        setHardness(2.0F);
        setStepSound(soundTypeWood);
        setCreativeTab(FoxWTCore.TAB);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase base, ItemStack par6ItemStack) {
        int orientation = BlockPistonBase.determineOrientation(world, x, y, z, base);
        TileSpreader spreader = (TileSpreader)world.getTileEntity(x, y, z);
        switch (orientation) {
            case 0:
                spreader.rotationY = -90.0F;
            case 1:
                spreader.rotationY = 90.0F;
            case 2:
                spreader.rotationX = 270.0F;
            case 3:
                spreader.rotationX = 90.0F;
            case 4:
                return;
        }
        spreader.rotationX = 180.0F;
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public int getManaPerSec() {
        return switch (type) {
            case ASGARD -> WTConfig.manaPerSecAsgardSpreader;
            case HELHEIM -> WTConfig.manaPerSecHelheimSpreader;
            case VALHALLA -> WTConfig.manaPerSecValhallaSpreader;
            case MIDGARD -> WTConfig.manaPerSecMidgardSpreader;
        };
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public IIcon getIcon(int side, int meta) {
        return ModBlocks.livingwood.getIcon(side, 0);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileSpreader spreader))
            return false;
        ItemStack lens = spreader.getStackInSlot(0);
        ItemStack heldItem = player.getCurrentEquippedItem();
        boolean isHeldItemLens = (heldItem != null && heldItem.getItem() instanceof vazkii.botania.api.mana.ILens);
        boolean wool = (heldItem != null && heldItem.getItem() == Item.getItemFromBlock(Blocks.wool));
        if (heldItem != null && heldItem.getItem() == ModItems.twigWand)
            return false;
        if (lens == null && isHeldItemLens) {
            if (!player.capabilities.isCreativeMode)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            spreader.setInventorySlotContents(0, heldItem.copy());
            spreader.markDirty();
        } else if (lens != null && !wool) {
            ItemStack add = lens.copy();
            if (!player.inventory.addItemStackToInventory(add))
                player.dropPlayerItemWithRandomChoice(add, false);
            spreader.setInventorySlotContents(0, null);
            spreader.markDirty();
        }
        if (wool && spreader.paddingColor == -1) {
            spreader.paddingColor = heldItem.getItemDamage();
            heldItem.stackSize--;
            if (heldItem.stackSize == 0)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        } else if (heldItem == null && spreader.paddingColor != -1 && lens == null) {
            ItemStack pad = new ItemStack(Blocks.wool, 1, spreader.paddingColor);
            if (!player.inventory.addItemStackToInventory(pad))
                player.dropPlayerItemWithRandomChoice(pad, false);
            spreader.paddingColor = -1;
            spreader.markDirty();
        }
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof TileSpreader inv))
            return;
        for (int j1 = 0; j1 < inv.getSizeInventory() + 1; j1++) {
            ItemStack itemstack = (j1 >= inv.getSizeInventory()) ? ((inv.paddingColor == -1) ? null : new ItemStack(Blocks.wool, 1, inv.paddingColor)) : inv.getStackInSlot(j1);
            if (itemstack != null)
                dropBlockAsItem(world, x, y + 1, z, itemstack);
        }
        world.notifyBlockChange(x, y, z, par5);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        ((TileSpreader)world.getTileEntity(x, y, z)).onWanded(player, stack);
        return true;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return switch (type) {
            case ASGARD -> new TileAsgardSpreader();
            case HELHEIM -> new TileHelheimSpreader();
            case VALHALLA -> new TileValhallaSpreader();
            case MIDGARD -> new TileMidgardSpreader();
        };
    }

    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileSpreader)world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public AxisAlignedBB getWireframeAABB(World world, int x, int y, int z) {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBox((x + f), (y + f), (z + f), ((x + 1) - f), ((y + 1) - f), ((z + 1) - f));
    }

    public int getRenderType() {
        return switch (type){
            case MIDGARD -> RenderIDs.MIDGARD_SPREADER.getId();
            case VALHALLA -> RenderIDs.VALHALLA_SPREADER.getId();
            case HELHEIM -> RenderIDs.HELHEIM_SPREADER.getId();
            case ASGARD -> RenderIDs.ASGARD_SPREADER.getId();
        };
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
