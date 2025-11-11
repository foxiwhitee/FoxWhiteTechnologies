package foxiwhitee.FoxWhiteTechnologies.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.*;
import foxiwhitee.FoxWhiteTechnologies.util.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.achievement.ICraftAchievement;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class BlockCustomManaPool extends FoxBaseBlock implements ITileEntityProvider, IWandHUD, IWandable, ICraftAchievement {
    public enum Type { MIDGARD, VALHALLA, HELHELM, ASGARD }

    public static IIcon manaIcon;
    boolean lastFragile;
    private final int maxMana;
    private final Type type;

    public BlockCustomManaPool(String name, Type type) {
        super(name);
        this.maxMana = switch (type) {
            case ASGARD -> WTConfig.manaAsgardPool;
            case HELHELM -> WTConfig.manaHelhelmPool;
            case VALHALLA -> WTConfig.manaValhallaPool;
            case MIDGARD -> WTConfig.manaMidgardPool;
            default -> 0;
        };
        this.type = type;
        this.lastFragile = false;
        this.setBlockName(name);
        this.setBlockTextureName(FoxWTCore.MODID.toLowerCase() + ":" + name);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(FoxWTCore.TAB);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setLightOpacity(255);
        this.setLightLevel(1F);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        BotaniaAPI.blacklistBlockFromMagnet(this, 32767);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        super.onBlockPlacedBy(world, x, y, z, placer, itemIn);
        ((TileCustomManaPool)world.getTileEntity(x, y, z)).setFieldValue("manaCap", maxMana);
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float subX, float subY, float subZ, int meta) {
        return super.onBlockPlaced(world, x, y, z, side, subX, subY, subZ, meta);
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {
        manaIcon = IconHelper.forName(par1IconRegister, "manaWater");
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        TileCustomManaPool pool = (TileCustomManaPool)world.getTileEntity(x, y, z);
        this.lastFragile = pool.fragile;
        super.breakBlock(world, x, y, z, par5, par6);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (!this.lastFragile)
            drops.add(new ItemStack(this));
        return drops;
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return switch (type) {
            case ASGARD -> new TileAsgardManaPool();
            case HELHELM -> new TileHelHelmManaPool();
            case VALHALLA -> new TileValhallaManaPool();
            case MIDGARD -> new TileMidgardManaPool();
        };
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity) {
        if (par5Entity instanceof EntityItem) {
            TileCustomManaPool tile = (TileCustomManaPool)world.getTileEntity(x, y, z);
            if (tile.collideEntityItem((EntityItem)par5Entity))
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
        }
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB bb, List list, Entity player) {
        float f = 0.0625F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, player);
        setBlockBounds(0.0F, 0.0F, 0.0F, f, 0.5F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, player);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, f);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, player);
        setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, player);
        setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 0.5F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, list, player);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return (side == ForgeDirection.DOWN);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public IIcon getIcon(int par1, int par2) {
        return ModBlocks.livingrock.getIcon(0, 0);
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        TileCustomManaPool pool = (TileCustomManaPool)par1World.getTileEntity(par2, par3, par4);
        int val = (int)(pool.getCurrentMana() / 1.0E8D * 15.0D);
        if (pool.getCurrentMana() > 0)
            val = Math.max(val, 1);
        return val;
    }

    @SideOnly(Side.CLIENT)
    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        ((TileCustomManaPool)world.getTileEntity(x, y, z)).renderHUD(mc, res);
    }

    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        ((TileCustomManaPool)world.getTileEntity(x, y, z)).onWanded(player, stack);
        return true;
    }

    public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix) {
        return ModAchievements.manaPoolPickup;
    }

    public int getRenderType() {
        return switch (type){
            case MIDGARD -> RenderIDs.MIDGARD_MANA_POOL.getId();
            case VALHALLA -> RenderIDs.VALHALLA_MANA_POOL.getId();
            case HELHELM -> RenderIDs.HELHELM_MANA_POOL.getId();
            case ASGARD -> RenderIDs.ASGARD_MANA_POOL.getId();
        };
    }

    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}
