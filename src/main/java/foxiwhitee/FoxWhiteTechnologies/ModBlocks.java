package foxiwhitee.FoxWhiteTechnologies;

import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxWhiteTechnologies.blocks.*;
import foxiwhitee.FoxWhiteTechnologies.blocks.mechanic.BlockMechanicRuneAltar;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.items.DefaultItem;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.TileManaCharger;
import foxiwhitee.FoxWhiteTechnologies.tile.mechanic.TileMechanicRuneAltar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class ModBlocks {
    public static final Block MANA_CHARGER = new BlockManaCharger("manaCharger");

    public static final Block ASGARD_POOL = new BlockCustomManaPool("asgardPool", BlockCustomManaPool.Type.ASGARD);
    public static final Block HELHELM_POOL = new BlockCustomManaPool("helhelmPool", BlockCustomManaPool.Type.HELHELM);
    public static final Block VALHALLA_POOL = new BlockCustomManaPool("valhallaPool", BlockCustomManaPool.Type.VALHALLA);
    public static final Block MIDGARD_POOL = new BlockCustomManaPool("midgardPool", BlockCustomManaPool.Type.MIDGARD);

    public static final Block ASGARD_SPREADER = new BlockCustomSpreader("asgardSpreader", BlockCustomSpreader.Type.ASGARD);
    public static final Block HELHELM_SPREADER = new BlockCustomSpreader("helhelmSpreader", BlockCustomSpreader.Type.HELHELM);
    public static final Block VALHALLA_SPREADER = new BlockCustomSpreader("valhallaSpreader", BlockCustomSpreader.Type.VALHALLA);
    public static final Block MIDGARD_SPREADER = new BlockCustomSpreader("midgardSpreader", BlockCustomSpreader.Type.MIDGARD);

    public static final Block MECHANIC_RUNE_ALTAR = new BlockMechanicRuneAltar("mechanicRuneAltar");

    public static final Block ASGARDIAN_BLOCK = new StaticBlock("asgardianBlock");
    public static final Block HELHEIM_BLOCK = new StaticBlock("helheimBlock");
    public static final Block VALHALLA_BLOCK = new StaticBlock("valhallaBlock");
    public static final Block MIDGARD_BLOCK = new StaticBlock("midgardBlock");

    public static final Block MALACHITE_PLATE = new BlockMalachitePlate("malachitePlate");

    public static final Block MALACHITE_ORE = new BlockMalachiteOre("malachiteOre");

    public static void registerBlocks() {
        if (ContentConfig.enableCharger) {
            RegisterUtils.registerBlock(MANA_CHARGER);
            RegisterUtils.registerTile(TileManaCharger.class);
        }
        if (ContentConfig.enablePools) {
            RegisterUtils.registerBlocks(ASGARD_POOL, HELHELM_POOL, VALHALLA_POOL, MIDGARD_POOL);
            RegisterUtils.findClasses("foxiwhitee.FoxWhiteTechnologies.tile.pools", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
        if (ContentConfig.enableSpreaders) {
            RegisterUtils.registerBlocks(ASGARD_SPREADER, HELHELM_SPREADER, VALHALLA_SPREADER, MIDGARD_SPREADER);
            RegisterUtils.findClasses("foxiwhitee.FoxWhiteTechnologies.tile.spreaders", TileEntity.class).forEach(RegisterUtils::registerTile);
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
    }
}
