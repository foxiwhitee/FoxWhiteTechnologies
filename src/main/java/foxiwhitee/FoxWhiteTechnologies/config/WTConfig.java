package foxiwhitee.FoxWhiteTechnologies.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxWhiteTechnologies")
public class WTConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enable_tooltips = true;


    @ConfigValue(category = "Spark", desc = "The amount of mana a asgard spark transfers per second")
    public static int manaPerSecSparkAsgard = 1_250_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a helheim spark transfers per second")
    public static int manaPerSecSparkHelheim = 500_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a valhalla spark transfers per second")
    public static int manaPerSecSparkValhalla = 115_000;

    @ConfigValue(category = "Spark", desc = "The amount of mana a midgard spark transfers per second")
    public static int manaPerSecSparkMidgard = 25_000;


    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the asgard mana pool")
    public static int manaAsgardPool = 750_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the helheim mana pool")
    public static int manaHelheimPool = 250_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the valhalla mana pool")
    public static int manaValhallaPool = 125_000_000;

    @ConfigValue(category = "Pool", desc = "The amount of mana stored in the midgard mana pool")
    public static int manaMidgardPool = 50_000_000;


    @ConfigValue(category = "Spreader", desc = "The amount of mana a asgard spreader transfers per second")
    public static int manaPerSecAsgardSpreader = 100_000_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a helheim spreader transfers per second")
    public static int manaPerSecHelheimSpreader = 6_000_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a valhalla spreader transfers per second")
    public static int manaPerSecValhallaSpreader = 2_500_000;

    @ConfigValue(category = "Spreader", desc = "The amount of mana a midgard spreader transfers per second")
    public static int manaPerSecMidgardSpreader = 700_000;


    @ConfigValue(category = "Mechanic.ElvenTrade", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedElvenTrade = 5;

    @ConfigValue(category = "Mechanic.MalachitePlate", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedMalachitePlate = 5;

    @ConfigValue(category = "Mechanic.ManaPool", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedManaPool = 5;

    @ConfigValue(category = "Mechanic.Petals", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedPetals = 5;

    @ConfigValue(category = "Mechanic.PureDaisy", name = "speed", min = "1", desc = "How many seconds will it take to create an item?")
    public static int speedPureDaisy = 5;

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

    @ConfigValue(category = "Mechanic.Upgrades", min = "1", max = "100", desc = "How many percent faster will the mechanical unit work with this improvement?")
    public static int speedUpgradeBonus1 = 10;

    @ConfigValue(category = "Mechanic.Upgrades", min = "1", max = "100", desc = "How many percent faster will the mechanical unit work with this improvement?")
    public static int speedUpgradeBonus2 = 25;

    @ConfigValue(category = "Mechanic.Upgrades", min = "1", max = "100", desc = "How many percent faster will the mechanical unit work with this improvement?")
    public static int speedUpgradeBonus3 = 50;

    @ConfigValue(category = "Mechanic.Upgrades", min = "1", max = "100", desc = "How many percent faster will the mechanical unit work with this improvement?")
    public static int speedUpgradeBonus4 = 80;


    @ConfigValue(category = "Greenhouse", name = "generationLoss", min = "0", max = "100", desc = "What percentage of mana is lost when generating?")
    public static int greenhouseGenerationLoss = 50;

    @ConfigValue(category = "Greenhouse", name = "speed", desc = "How many seconds will it take to generate mana one time?")
    public static int greenhouseSpeed = 10;

}
