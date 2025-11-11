package foxiwhitee.FoxWhiteTechnologies.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.ModItems;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
