package foxiwhitee.FoxWhiteTechnologies;

import foxiwhitee.FoxLib.client.render.StaticRender;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxWhiteTechnologies.blocks.*;
import foxiwhitee.FoxWhiteTechnologies.blocks.mechanic.*;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.items.DefaultItem;
import foxiwhitee.FoxWhiteTechnologies.items.ModItemBlock;
import foxiwhitee.FoxWhiteTechnologies.tile.TileGreenhouse;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.TileManaCharger;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class ModBlocks {
    public static final Block MANA_CHARGER = new BlockManaCharger("manaCharger");

    public static final Block ASGARD_POOL = new BlockCustomManaPool("asgardPool", BlockCustomManaPool.Type.ASGARD);
    public static final Block HELHEIM_POOL = new BlockCustomManaPool("helheimPool", BlockCustomManaPool.Type.HELHEIM);
    public static final Block VALHALLA_POOL = new BlockCustomManaPool("valhallaPool", BlockCustomManaPool.Type.VALHALLA);
    public static final Block MIDGARD_POOL = new BlockCustomManaPool("midgardPool", BlockCustomManaPool.Type.MIDGARD);

    public static final Block ASGARD_SPREADER = new BlockCustomSpreader("asgardSpreader", BlockCustomSpreader.Type.ASGARD);
    public static final Block HELHEIM_SPREADER = new BlockCustomSpreader("helheimSpreader", BlockCustomSpreader.Type.HELHEIM);
    public static final Block VALHALLA_SPREADER = new BlockCustomSpreader("valhallaSpreader", BlockCustomSpreader.Type.VALHALLA);
    public static final Block MIDGARD_SPREADER = new BlockCustomSpreader("midgardSpreader", BlockCustomSpreader.Type.MIDGARD);

    @StaticRender(modID = "foxwhitetechnologies", tile = TileMechanicElvenTrade.class,
        model = "models/mechanicElvenTrade.obj", texture = "textures/blocks/mechanic/mechanicElvenTrade.png")
    public static final Block MECHANIC_ELVEN_TRADE = new BlockMechanicElvenTrade("mechanicElvenTrade");

    @StaticRender(modID = "foxwhitetechnologies", tile = TileMechanicMalachitePlate.class,
        model = "models/mechanicMalachitePlate.obj", texture = "textures/blocks/mechanic/mechanicMalachitePlate.png")
    public static final Block MECHANIC_MALACHITE_PLATE = new BlockMechanicMalachitePlate("mechanicMalachitePlate");
    public static final Block MECHANIC_MANA_POOL = new BlockMechanicManaPool("mechanicManaPool");

    public static final Block MECHANIC_PETALS = new BlockMechanicPetals("mechanicPetals");

    @StaticRender(modID = "foxwhitetechnologies", tile = TileMechanicPureDaisy.class,
        model = "models/mechanicPureDaisy.obj", texture = "textures/blocks/mechanic/mechanicPureDaisy.png")
    public static final Block MECHANIC_PURE_DAISY = new BlockMechanicPureDaisy("mechanicPureDaisy");
    public static final Block MECHANIC_RUNE_ALTAR = new BlockMechanicRuneAltar("mechanicRuneAltar");

    public static final Block ASGARDIAN_BLOCK = new StaticBlock("asgardianBlock");
    public static final Block HELHEIM_BLOCK = new StaticBlock("helheimBlock");
    public static final Block VALHALLA_BLOCK = new StaticBlock("valhallaBlock");
    public static final Block MIDGARD_BLOCK = new StaticBlock("midgardBlock");

    public static final Block MALACHITE_PLATE = new BlockMalachitePlate("malachitePlate");

    public static final Block MALACHITE_ORE = new BlockMalachiteOre("malachiteOre");

    public static final Block GREENHOUSE = new BlockGreenhouse("greenhouse");

    public static void registerBlocks() {
        if (ContentConfig.enableCharger) {
            RegisterUtils.registerBlock(MANA_CHARGER);
            RegisterUtils.registerTile(TileManaCharger.class);
        }
        if (ContentConfig.enablePools) {
            RegisterUtils.registerBlocks(ModItemBlock.class, ASGARD_POOL, HELHEIM_POOL, VALHALLA_POOL, MIDGARD_POOL);
            RegisterUtils.findClasses("foxiwhitee.FoxWhiteTechnologies.tile.pools", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
        if (ContentConfig.enableSpreaders) {
            RegisterUtils.registerBlocks(ModItemBlock.class, ASGARD_SPREADER, HELHEIM_SPREADER, VALHALLA_SPREADER, MIDGARD_SPREADER);
            RegisterUtils.findClasses("foxiwhitee.FoxWhiteTechnologies.tile.spreaders", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
        if (ContentConfig.enableMechanicElvenTrader) {
            RegisterUtils.registerBlock(MECHANIC_ELVEN_TRADE);
            RegisterUtils.registerTile(TileMechanicElvenTrade.class);
        }
        if (ContentConfig.enableMechanicMalachitePlate) {
            RegisterUtils.registerBlock(MECHANIC_MALACHITE_PLATE);
            RegisterUtils.registerTile(TileMechanicMalachitePlate.class);
        }
        if (ContentConfig.enableMechanicManaPool) {
            RegisterUtils.registerBlock(MECHANIC_MANA_POOL);
            RegisterUtils.registerTile(TileMechanicManaPool.class);
        }
        if (ContentConfig.enableMechanicPetals) {
            RegisterUtils.registerBlock(MECHANIC_PETALS);
            RegisterUtils.registerTile(TileMechanicPetals.class);
        }
        if (ContentConfig.enableMechanicPureDaisy) {
            RegisterUtils.registerBlock(MECHANIC_PURE_DAISY);
            RegisterUtils.registerTile(TileMechanicPureDaisy.class);
        }
        if (ContentConfig.enableMechanicRuneAltar) {
            RegisterUtils.registerBlock(MECHANIC_RUNE_ALTAR);
            RegisterUtils.registerTile(TileMechanicRuneAltar.class);
        }
        if (ContentConfig.enableIngotsBlocks) {
            RegisterUtils.registerBlocks(ASGARDIAN_BLOCK, HELHEIM_BLOCK,  VALHALLA_BLOCK, MIDGARD_BLOCK);
        }
        if (ContentConfig.enableMalachitePlate) {
            RegisterUtils.registerBlock(MALACHITE_PLATE);
            RegisterUtils.registerTile(TileMalachitePlate.class);
        }
        if (ContentConfig.enableMalachiteOre) {
            RegisterUtils.registerBlock(MALACHITE_ORE);
        }
        if (ContentConfig.enableGreenhouse) {
            RegisterUtils.registerBlock(GREENHOUSE);
            RegisterUtils.registerTile(TileGreenhouse.class);
        }
    }
}
