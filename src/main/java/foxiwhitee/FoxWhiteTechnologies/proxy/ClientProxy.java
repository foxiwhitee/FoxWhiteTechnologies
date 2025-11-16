package foxiwhitee.FoxWhiteTechnologies.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.client.ManaInfoRenderer;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderCustomManaPool;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderCustomSpreader;
import foxiwhitee.FoxWhiteTechnologies.client.render.RenderMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.client.render.charger.RenderItemManaCharger;
import foxiwhitee.FoxWhiteTechnologies.client.render.charger.RenderManaCharger;
import foxiwhitee.FoxWhiteTechnologies.client.render.entity.RenderCustomSpark;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.entity.AsgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.HelhelmSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.MidgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.ValhallaSpark;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.TileManaCharger;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileAsgardManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileHelHelmManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileMidgardManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.pools.TileValhallaManaPool;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileAsgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileHelhelmSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileMidgardSpreader;
import foxiwhitee.FoxWhiteTechnologies.tile.spreaders.TileValhallaSpreader;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    public static int sparkColorAsgard;
    public static int sparkColorHelhelm;
    public static int sparkColorValhalla;
    public static int sparkColorMidgard;

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
            String texture = "textures/blocks/asgardSpreader.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.ASGARD_SPREADER), new RenderCustomSpreader<>(TileAsgardSpreader.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileAsgardSpreader.class, new RenderCustomSpreader<>(TileAsgardSpreader.class, texture));

            texture = "textures/blocks/helhelmSpreader.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.HELHELM_SPREADER), new RenderCustomSpreader<>(TileHelhelmSpreader.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileHelhelmSpreader.class, new RenderCustomSpreader<>(TileHelhelmSpreader.class, texture));

            texture = "textures/blocks/valhallaSpreader.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.VALHALLA_SPREADER), new RenderCustomSpreader<>(TileValhallaSpreader.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileValhallaSpreader.class, new RenderCustomSpreader<>(TileValhallaSpreader.class, texture));

            texture = "textures/blocks/midgardSpreader.png";
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.MIDGARD_SPREADER), new RenderCustomSpreader<>(TileMidgardSpreader.class, texture));
            ClientRegistry.bindTileEntitySpecialRenderer(TileMidgardSpreader.class, new RenderCustomSpreader<>(TileMidgardSpreader.class, texture));
        }
        if (ContentConfig.enableSparks) {
            initHex();
            RenderingRegistry.registerEntityRenderingHandler(AsgardSpark.class, new RenderCustomSpark<>());
            RenderingRegistry.registerEntityRenderingHandler(HelhelmSpark.class, new RenderCustomSpark<>());
            RenderingRegistry.registerEntityRenderingHandler(ValhallaSpark.class, new RenderCustomSpark<>());
            RenderingRegistry.registerEntityRenderingHandler(MidgardSpark.class, new RenderCustomSpark<>());
        }
        if (ContentConfig.enableMalachitePlate) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileMalachitePlate.class, new RenderMalachitePlate());
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public static void initHex() {
        sparkColorAsgard = parseColor("#D66122", 16711680);
        sparkColorHelhelm = parseColor("#6f00cc", 16711680);
        sparkColorValhalla = parseColor("#d9c836", 16711680);
        sparkColorMidgard = parseColor("#1D4DE1", 16711680);
    }

    public static int parseColor(String hex, int defaultValue) {
        if (hex.startsWith("#"))
            hex = hex.substring(1);
        try {
            return Integer.parseInt(hex, 16);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
