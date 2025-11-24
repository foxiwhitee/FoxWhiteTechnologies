package foxiwhitee.FoxWhiteTechnologies.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import foxiwhitee.FoxLib.api.FoxLibApi;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxWhiteTechnologies.ModBlocks;
import foxiwhitee.FoxWhiteTechnologies.ModItems;
import foxiwhitee.FoxWhiteTechnologies.ModRecipes;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.items.*;
import foxiwhitee.FoxWhiteTechnologies.recipes.JSONRecipeMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.tile.TileMalachitePlate;
import foxiwhitee.FoxWhiteTechnologies.worldgen.WorldGenMalachiteOre;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.block.BlockLivingrock;
import vazkii.botania.common.block.mana.BlockAlchemyCatalyst;
import vazkii.botania.common.block.mana.BlockConjurationCatalyst;
import vazkii.botania.common.lexicon.BLexiconEntry;
import vazkii.botania.common.lexicon.page.PageMultiblock;
import vazkii.botania.common.lexicon.page.PageText;

public class CommonProxy {
    private static LexiconCategory malachiteCategory;
    private static LexiconEntry malachiteEntry;

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }

    public void init(FMLInitializationEvent event) {
        SlotFiltered.filters.put("noLivingRock", stack -> !(Block.getBlockFromItem(stack.getItem()) instanceof BlockLivingrock));
        SlotFiltered.filters.put("noCatalyst", stack -> !(Block.getBlockFromItem(stack.getItem()) instanceof BlockAlchemyCatalyst || Block.getBlockFromItem(stack.getItem()) instanceof BlockConjurationCatalyst));
        SlotFiltered.filters.put("noSeeds", stack -> stack.getItem() != Items.wheat_seeds);
        SlotFiltered.filters.put("livingRock", stack -> Block.getBlockFromItem(stack.getItem()) instanceof BlockLivingrock);
        SlotFiltered.filters.put("catalyst", stack -> Block.getBlockFromItem(stack.getItem()) instanceof BlockAlchemyCatalyst || Block.getBlockFromItem(stack.getItem()) instanceof BlockConjurationCatalyst);
        SlotFiltered.filters.put("seeds", stack -> stack.getItem() == Items.wheat_seeds);
        SlotFiltered.filters.put("mechanicManaBlockUpgrade", stack -> stack.getItem() instanceof ItemProductivityCard || stack.getItem() instanceof ItemInfinityManaUpgrade || stack.getItem() instanceof ItemSpeedUpgrade || stack.getItem() instanceof ItemStorageUpgrade);
        SlotFiltered.filters.put("mechanicPetalsUpgrade", stack -> stack.getItem() instanceof ItemProductivityCard || stack.getItem() instanceof ItemInfinityWaterUpgrade || stack.getItem() instanceof ItemSpeedUpgrade);
        SlotFiltered.filters.put("mechanicPureDaisyUpgrade", stack -> stack.getItem() instanceof ItemProductivityCard || stack.getItem() instanceof ItemSpeedUpgrade);

        if (ContentConfig.enableMalachitePlate) {
            FoxLibApi.instance.registries().registerJsonRecipe().register(JSONRecipeMalachitePlate.class, "malachitePlate");
            malachiteCategory = new LexiconCategory("malachite");
            BotaniaAPI.addCategory(malachiteCategory);
            malachiteEntry = new BLexiconEntry("malachitePlate", getMalachiteCategory());
            malachiteEntry.setLexiconPages(new LexiconPage[]{
                new PageText("0"),
                new PageMultiblock("1", TileMalachitePlate.makeMultiblockSet(0)),
                new PageMultiblock("2", TileMalachitePlate.makeMultiblockSet(1)),
                new PageMultiblock("3", TileMalachitePlate.makeMultiblockSet(2)),
                new PageMultiblock("4", TileMalachitePlate.makeMultiblockSet(3)),
            });
        }
        if (ContentConfig.enableMalachiteOre) {
            GameRegistry.registerWorldGenerator(new WorldGenMalachiteOre(), 0);
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.registerRecipes();
    }

    public static LexiconCategory getMalachiteCategory() {
        return malachiteCategory;
    }

    public static LexiconEntry getMalachiteEntry() {
        return malachiteEntry;
    }
}
