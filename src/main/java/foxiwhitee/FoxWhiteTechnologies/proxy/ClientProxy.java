package foxiwhitee.FoxWhiteTechnologies.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.client.ManaInfoRenderer;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderCustomSpreader;
import foxiwhitee.FoxWhiteTechnologies.client.render.charger.RenderItemManaCharger;
import foxiwhitee.FoxWhiteTechnologies.client.render.charger.RenderManaCharger;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.tile.TileManaCharger;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileAsgardManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileHelHelmManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileMidgardManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileValhallaManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileAsgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileHelhelmSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileMidgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileValhallaSpreader;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ManaInfoRenderer());
        if (ContentConfig.enableCharger) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.MANA_CHARGER), new RenderItemManaCharger());
            ClientRegistry.bindTileEntitySpecialRenderer(TileManaCharger.class, new RenderManaCharger());
        }
        if (ContentConfig.enablePools) {
            String texture = "textures/blocks/asgardPool.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.ASGARD_POOL), new RenderCustomManaPool<>(TileAsgardManaPool.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileAsgardManaPool.class, new RenderCustomManaPool<>(TileAsgardManaPool.class, texture));

            texture = "textures/blocks/helhelmPool.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.HELHELM_POOL), new RenderCustomManaPool<>(TileHelHelmManaPool.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileHelHelmManaPool.class, new RenderCustomManaPool<>(TileHelHelmManaPool.class, texture));

            texture = "textures/blocks/valhallaPool.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.VALHALLA_POOL), new RenderCustomManaPool<>(TileValhallaManaPool.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileValhallaManaPool.class, new RenderCustomManaPool<>(TileValhallaManaPool.class, texture));

            texture = "textures/blocks/midgardPool.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.MIDGARD_POOL), new RenderCustomManaPool<>(TileMidgardManaPool.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileMidgardManaPool.class, new RenderCustomManaPool<>(TileMidgardManaPool.class, texture));

        }
        if (ContentConfig.enableSpreaders) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileAsgardSpreader.class, new RenderCustomSpreader(new TileAsgardSpreader()));
            ClientRegistry.bindTileEntitySpecialRenderer(TileHelhelmSpreader.class, new RenderCustomSpreader(new TileHelhelmSpreader()));
            ClientRegistry.bindTileEntitySpecialRenderer(TileValhallaSpreader.class, new RenderCustomSpreader(new TileValhallaSpreader()));
            ClientRegistry.bindTileEntitySpecialRenderer(TileMidgardSpreader.class, new RenderCustomSpreader(new TileMidgardSpreader()));
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
