package foxiwhitee.FoxWhiteTechnologies.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.ModItems;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import net.minecraft.block.Block;
import vazkii.botania.common.block.BlockLivingrock;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }

    public void init(FMLInitializationEvent event) {
        SlotFiltered.filters.put("noLivingRock", stack -> !(Block.getBlockFromItem(stack.getItem()) instanceof BlockLivingrock));
        SlotFiltered.filters.put("livingRock", stack -> Block.getBlockFromItem(stack.getItem()) instanceof BlockLivingrock);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.registerRecipes();
    }
}
