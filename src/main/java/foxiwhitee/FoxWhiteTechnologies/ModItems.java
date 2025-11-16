package foxiwhitee.FoxWhiteTechnologies;

import cpw.mods.fml.common.registry.EntityRegistry;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxWhiteTechnologies.config.ContentConfig;
import foxiwhitee.FoxWhiteTechnologies.entity.AsgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.HelhelmSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.MidgardSpark;
import foxiwhitee.FoxWhiteTechnologies.entity.ValhallaSpark;
import foxiwhitee.FoxWhiteTechnologies.items.DefaultItem;
import foxiwhitee.FoxWhiteTechnologies.items.ItemCustomSpark;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item ASGARD_SPARK = (Item)new ItemCustomSpark("asgardSpark", ItemCustomSpark.Type.ASGARD);
    public static final Item HELHELM_SPARK = (Item)new ItemCustomSpark("helhelmSpark", ItemCustomSpark.Type.HELHELM);
    public static final Item VALHALLA_SPARK = (Item)new ItemCustomSpark("valhallaSpark", ItemCustomSpark.Type.VALHALLA);
    public static final Item MIDGARD_SPARK = (Item)new ItemCustomSpark("midgardSpark", ItemCustomSpark.Type.MIDGARD);

    public static final Item ASGARDIAN_INGOT = new DefaultItem("asgardianIngot");
    public static final Item HELHEIM_INGOT = new DefaultItem("helheimIngot");
    public static final Item VALHALLA_INGOT = new DefaultItem("valhallaIngot");
    public static final Item MIDGARD_INGOT = new DefaultItem("midgardIngot");

    public static final Item MALACHITE = new DefaultItem("malachite");

    public static void registerItems() {
        if (ContentConfig.enableSparks) {
            EntityRegistry.registerModEntity(AsgardSpark.class, "AsgardSpark", 1, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(HelhelmSpark.class, "HelhelmSpark", 2, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(ValhallaSpark.class, "ValhallaSpark", 3, FoxWTCore.instance, 64, 10, false);
            EntityRegistry.registerModEntity(MidgardSpark.class, "MidgardSpark", 4, FoxWTCore.instance, 64, 10, false);
            RegisterUtils.registerItems(ASGARD_SPARK, HELHELM_SPARK, VALHALLA_SPARK, MIDGARD_SPARK);
        }
        if (ContentConfig.enableIngots) {
            RegisterUtils.registerItems(ASGARDIAN_INGOT, HELHEIM_INGOT, VALHALLA_INGOT, MIDGARD_INGOT);
        }
        if (ContentConfig.enableMalachite) {
            RegisterUtils.registerItem(MALACHITE);
        }
    }
}
