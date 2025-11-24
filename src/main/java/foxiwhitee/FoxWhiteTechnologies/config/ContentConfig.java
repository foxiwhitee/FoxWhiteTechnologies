package foxiwhitee.FoxWhiteTechnologies.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxWhiteTechnologies-Content")
public class ContentConfig {
    @ConfigValue(category = "Content", desc = "Enable Mana Charger")
    public static boolean enableCharger = true;

    @ConfigValue(category = "Content", desc = "Enable New Sparks")
    public static boolean enableSparks = true;

    @ConfigValue(category = "Content", desc = "Enable New Mana Pool")
    public static boolean enablePools = true;

    @ConfigValue(category = "Content", desc = "Enable New Mana Spreaders")
    public static boolean enableSpreaders = true;

    @ConfigValue(category = "Content", desc = "Enable New Ingots")
    public static boolean enableIngots = true;

    @ConfigValue(category = "Content", desc = "Enable New Malachite")
    public static boolean enableMalachite = true;

    @ConfigValue(category = "Content", desc = "Enable New Blocks For New Ingots")
    public static boolean enableIngotsBlocks = true;

    @ConfigValue(category = "Content", desc = "Enable New Malachite Plate")
    public static boolean enableMalachitePlate = true;

    @ConfigValue(category = "Content", desc = "Enable New Malachite Ore")
    public static boolean enableMalachiteOre = true;


    @ConfigValue(category = "Content.Mechanic.Upgrades", desc = "Enable Infinity Mana Upgrade")
    public static boolean enableInfinityManaUpgrade = true;

    @ConfigValue(category = "Content.Mechanic.Upgrades", desc = "Enable Infinity Water Upgrade")
    public static boolean enableInfinityWaterUpgrade = true;

    @ConfigValue(category = "Content.Mechanic.Upgrades", desc = "Enable Speed Upgrades")
    public static boolean enableSpeedUpgrades = true;

    @ConfigValue(category = "Content.Mechanic.Upgrades", desc = "Enable Storage Upgrades")
    public static boolean enableStorageUpgrades = true;


    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Elven Trade")
    public static boolean enableMechanicElvenTrader = true;

    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Malachite Plate")
    public static boolean enableMechanicMalachitePlate = true;

    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Mana Pool")
    public static boolean enableMechanicManaPool = true;

    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Petal Apothecary")
    public static boolean enableMechanicPetals = true;

    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Pure Daisy")
    public static boolean enableMechanicPureDaisy = true;

    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Rune Altar")
    public static boolean enableMechanicRuneAltar = true;
}
