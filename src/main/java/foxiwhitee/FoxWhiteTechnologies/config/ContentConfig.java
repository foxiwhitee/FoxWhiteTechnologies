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


    @ConfigValue(category = "Content.Mechanic", desc = "Enable Mechanic Rune Altar")
    public static boolean enableMechanicRuneAltar = true;
}
