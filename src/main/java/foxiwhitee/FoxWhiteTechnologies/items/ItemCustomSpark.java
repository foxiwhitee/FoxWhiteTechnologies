package foxiwhitee.FoxWhiteTechnologies.items;

import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import foxiwhitee.FoxWhiteTechnologies.entity.*;
import foxiwhitee.FoxWhiteTechnologies.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaGivingItem;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.achievement.ICraftAchievement;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.item.ModItems;

import java.util.List;

public class ItemCustomSpark extends Item implements ICraftAchievement, IManaGivingItem {
    public enum Type{ASGARD, HELHELM, VALHALLA, MIDGARD}

    private final int manaPerSec;
    private final Type type;

    public ItemCustomSpark(String name, Type type) {
        this.manaPerSec = switch (type) {
            case ASGARD -> WTConfig.manaPerSecSparkAsgard;
            case HELHELM -> WTConfig.manaPerSecSparkHelhelm;
            case VALHALLA -> WTConfig.manaPerSecSparkValhalla;
            case MIDGARD -> WTConfig.manaPerSecSparkMidgard;
        };
        this.type = type;
        setUnlocalizedName(name);
        setCreativeTab(FoxWTCore.TAB);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float xv, float yv, float zv) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof ISparkAttachable attach) {
            if (attach.canAttachSpark(stack) && attach.getAttachedSpark() == null) {
                stack.stackSize--;
                if (!world.isRemote) {
                    CustomSpark spark = switch (type) {
                        case ASGARD -> new AsgardSpark(world);
                        case HELHELM -> new HelhelmSpark(world);
                        case VALHALLA -> new ValhallaSpark(world);
                        case MIDGARD -> new MidgardSpark(world);
                    };
                    spark.setPosition(x + 0.5D, y + 1.5D, z + 0.5D);
                    world.spawnEntityInWorld(spark);
                    attach.attachSpark(spark);
                    VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
                }
                return true;
            }
        }
        return false;
    }

    public void registerIcons(IIconRegister par1IconRegister) {}

    public IIcon getIconFromDamage(int meta) {
        return ModItems.spark.getIconFromDamage(0);
    }

    public int getColorFromItemStack(ItemStack stack, int pass) {
        return switch (type) {
            case ASGARD -> ClientProxy.sparkColorAsgard;
            case HELHELM -> ClientProxy.sparkColorHelhelm;
            case VALHALLA -> ClientProxy.sparkColorValhalla;
            case MIDGARD -> ClientProxy.sparkColorMidgard;
        };
    }

    public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix) {
        return ModAchievements.sparkCraft;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
        if (WTConfig.enable_tooltips) {
            p_77624_3_.add(LocalizationUtils.localize("tooltip.spark", manaPerSec));
        }
    }
}
