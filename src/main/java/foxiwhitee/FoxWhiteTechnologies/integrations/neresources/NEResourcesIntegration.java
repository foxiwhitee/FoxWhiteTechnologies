package foxiwhitee.FoxWhiteTechnologies.integrations.neresources;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.ModItems;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.ModifyOreMessage;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.Priority;
import neresources.registry.MessageRegistry;
import net.minecraft.item.ItemStack;

@Integration(modid = "neresources")
public class NEResourcesIntegration implements IIntegration {
    @Override
    public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {

    }

    @Override
    public void init(FMLInitializationEvent fmlInitializationEvent) {
        MessageRegistry.addMessage(new RegisterOreMessage(new ItemStack(ModBlocks.MALACHITE_ORE), new DistributionSquare(2, 5 , 1, 16)));
    }

    @Override
    public void postInit(FMLPostInitializationEvent fmlPostInitializationEvent) {

    }
}
