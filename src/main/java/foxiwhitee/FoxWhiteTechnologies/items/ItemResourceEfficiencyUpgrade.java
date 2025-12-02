package foxiwhitee.FoxWhiteTechnologies.items;

import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemResourceEfficiencyUpgrade extends DefaultItem {
    private final static String[] prefixes = {"1", "2"};
    private final IIcon[] icons = new IIcon[prefixes.length];
    private final String name;

    public ItemResourceEfficiencyUpgrade(String name) {
        super(name);
        this.name = name;
        hasSubtypes = true;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
        if (WTConfig.enable_tooltips) {
            p_77624_3_.add(StatCollector.translateToLocal("tooltip.resourceEfficiencyUpgrade.description" + p_77624_1_.getItemDamage()));
            p_77624_3_.add(StatCollector.translateToLocal("tooltip.upgradeFor"));
            p_77624_3_.add("§3 - " + StatCollector.translateToLocal("tile.greenhouse.name"));
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        meta = Math.max(0, Math.min(meta, prefixes.length - 1));
        return icons[meta];
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < prefixes.length; i++) {
            icons[i] = register.registerIcon(FoxWTCore.MODID + ":upgrades/" + name + prefixes[i]);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        int meta = Math.min(prefixes.length - 1, (stack != null) ? stack.getItemDamage() : 0);
        return LocalizationUtils.localize(getUnlocalizedName() + ".name", prefixes[meta]);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < prefixes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
