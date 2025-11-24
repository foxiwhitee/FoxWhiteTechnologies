package foxiwhitee.FoxWhiteTechnologies.items;

import foxiwhitee.FoxWhiteTechnologies.config.WTConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemInfinityWaterUpgrade extends DefaultItem {
    public ItemInfinityWaterUpgrade(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
        if (WTConfig.enable_tooltips) {
            p_77624_3_.add(StatCollector.translateToLocal("tooltip.infinityWaterUpgrade.description"));
            p_77624_3_.add(StatCollector.translateToLocal("tooltip.upgradeFor"));
            p_77624_3_.add("§3 - " + StatCollector.translateToLocal("tile.mechanicPetals.name"));

        }
    }
}
