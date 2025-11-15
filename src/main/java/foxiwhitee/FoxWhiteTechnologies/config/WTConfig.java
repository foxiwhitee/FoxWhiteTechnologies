package foxiwhitee.FoxWhiteTechnologies.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxWhiteTechnologies")
public class WTConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enable_tooltips = true;


    @ConfigValue(category = "Spark", desc = "The amount of mana a asgard spark transfers per second")
    public static int manaPerSecSparkAsgard = 1_250_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a helhelm spark transfers per second")
    public static int manaPerSecSparkHelhelm = 500_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a valhalla spark transfers per second")
    public static int manaPerSecSparkValhalla = 115_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a midgard spark transfers per second")
    public static int manaPerSecSparkMidgard = 25_000;


    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the asgard mana pool")
    public static int manaAsgardPool = 750_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the helhelm mana pool")
    public static int manaHelhelmPool = 250_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the valhalla mana pool")
    public static int manaValhallaPool = 125_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the midgard mana pool")
    public static int manaMidgardPool = 50_000_000;


    @ConfigValue(category = "Spreader", desc = "The amount of mana a asgard spreader transfers per second")
    public static int manaPerSecAsgardSpreader = 100_000_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a helhelm spreader transfers per second")
    public static int manaPerSecHelhelmSpreader = 6_000_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a valhalla spreader transfers per second")
    public static int manaPerSecValhallaSpreader = 2_500_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a midgard spreader transfers per second")
    public static int manaPerSecMidgardSpreader = 700_000;


    @ConfigValue(category = "Mechanic.RuneAltar", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedRuneAltar = 5;

    @ConfigValue(category = "Mechanic.RuneAltar", desc = "Items that are considered runes are not removed when crafting. Template: modID:name:meta")
    public static String[] runes = {
        "Botania:rune",
        "Botania:rune:1",
        "Botania:rune:2",
        "Botania:rune:3",
        "Botania:rune:4",
        "Botania:rune:5",
        "Botania:rune:6",
        "Botania:rune:7",
        "Botania:rune:8",
        "Botania:rune:9",
        "Botania:rune:10",
        "Botania:rune:11",
        "Botania:rune:12",
        "Botania:rune:13",
        "Botania:rune:14",
        "Botania:rune:15",
    };


}
