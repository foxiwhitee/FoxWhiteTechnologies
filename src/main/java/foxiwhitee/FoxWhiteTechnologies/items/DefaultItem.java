package foxiwhitee.FoxWhiteTechnologies.items;

import foxiwhitee.FoxWhiteTechnologies.FoxWTCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DefaultItem extends Item {

    public DefaultItem(String name, String texture, int maxStackSize) {
        this.setUnlocalizedName(name);
        this.setTextureName(FoxWTCore.MODID + ":" + texture);
        this.setCreativeTab(FoxWTCore.TAB);
        this.maxStackSize = maxStackSize;
    }

    public DefaultItem(String name, String texture) {
        this(name, texture, 64);
    }

    public DefaultItem(String name) {
        this(name, name);
    }

    public DefaultItem(String name, int maxStackSize) {
        this(name, name, maxStackSize);
    }
}
