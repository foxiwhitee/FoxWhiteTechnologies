package foxiwhitee.FoxWhiteTechnologies;

import cpw.mods.fml.common.registry.EntityRegistry;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.entity.AsgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.HelheimSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.MidgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.ValhallaSpark;
import foxiwhitee.FoxWhiteTechnologies.items.*;
import foxiwhitee.FoxWhiteTechnologies.items.ItemCustomSpark;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item ASGARD_SPARK = new ItemCustomSpark("asgardSpark", ItemCustomSpark.Type.ASGARD);
    public static final Item HELHEIM_SPARK = new ItemCustomSpark("helheimSpark", ItemCustomSpark.Type.HELHEIM);
    public static final Item VALHALLA_SPARK = new ItemCustomSpark("valhallaSpark", ItemCustomSpark.Type.VALHALLA);
    public static final Item MIDGARD_SPARK = new ItemCustomSpark("midgardSpark", ItemCustomSpark.Type.MIDGARD);

    public static final Item ASGARDIAN_INGOT = new DefaultItem("asgardianIngot");
    public static final Item HELHEIM_INGOT = new DefaultItem("helheimIngot");
    public static final Item VALHALLA_INGOT = new DefaultItem("valhallaIngot");
    public static final Item MIDGARD_INGOT = new DefaultItem("midgardIngot");

    public static final Item MALACHITE = new DefaultItem("malachite");

    public static final Item INFINITY_MANA_UPGRADE = new ItemInfinityManaUpgrade("infinityManaUpgrade");
    public static final Item INFINITY_WATER_UPGRADE = new ItemInfinityWaterUpgrade("infinityWaterUpgrade");
    public static final Item SPEED_UPGRADE = new ItemSpeedUpgrade("speedUpgrade");
    public static final Item STORAGE_UPGRADE = new ItemStorageUpgrade("storageUpgrade");
    public static final Item RESOURCES_EFFICIENCY_UPGRADE = new ItemResourceEfficiencyUpgrade("resourcesEfficiencyUpgrade");

    public static void registerItems() {
        if (ContentConfig.enableSparks) {
            EntityRegistry.registerModEntity(AsgardSpark.class, "AsgardSpark", 1, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(HelheimSpark.class, "HelheimSpark", 2, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(ValhallaSpark.class, "ValhallaSpark", 3, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(MidgardSpark.class, "MidgardSpark", 4, FoxWTCore.instance, 64, 10, false);
            RegisterUtils.registerItems(ASGARD_SPARK, HELHEIM_SPARK, VALHALLA_SPARK, MIDGARD_SPARK);
        }
        if (ContentConfig.enableIngots) {
            RegisterUtils.registerItems(ASGARDIAN_INGOT, HELHEIM_INGOT, VALHALLA_INGOT, MIDGARD_INGOT);
        }
        if (ContentConfig.enableMalachite) {
            RegisterUtils.registerItem(MALACHITE);
        }
        if (ContentConfig.enableInfinityManaUpgrade) {
            RegisterUtils.registerItem(INFINITY_MANA_UPGRADE);
        }
        if (ContentConfig.enableInfinityWaterUpgrade) {
            RegisterUtils.registerItem(INFINITY_WATER_UPGRADE);
        }
        if (ContentConfig.enableSpeedUpgrades) {
            RegisterUtils.registerItems(SPEED_UPGRADE);
        }
        if (ContentConfig.enableStorageUpgrades) {
            RegisterUtils.registerItems(STORAGE_UPGRADE);
        }
        if (ContentConfig.enableResourcesEfficiencyUpgrades) {
            RegisterUtils.registerItems(RESOURCES_EFFICIENCY_UPGRADE);
        }
    }
}
