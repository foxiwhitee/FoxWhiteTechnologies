package foxiwhitee.FoxWhiteTechnologies;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxWhiteTechnologies.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static foxiwhitee.FoxWhiteTechnologies.FoxWTCore.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class FoxWTCore {
    public static final String
        MODID = "foxwhitetechnologies",
        MODNAME = "FoxWhiteTechnologies",
        VERSION = "1.0.0";

    public static final CreativeTabs TAB = new CreativeTabs("FOX_WHITE_TECHNOLOGIES_TAB") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.bedrock);
        }
    };

    @SidedProxy(clientSide = "foxiwhitee.FoxWhiteTechnologies.proxy.ClientProxy", serverSide = "foxiwhitee.FoxWhiteTechnologies.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
