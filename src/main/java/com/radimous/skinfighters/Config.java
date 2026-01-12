package com.radimous.skinfighters;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class Config {
    public static ForgeConfigSpec.IntValue SKIN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> NAMES;
    public static ForgeConfigSpec.BooleanValue DISABLE_STARS;
    public static ForgeConfigSpec.EnumValue<SkinFighters.source> SOURCE;
    public static ForgeConfigSpec.ConfigValue<String> NAME_URL;
    public static ForgeConfigSpec.BooleanValue DEBUG;
    public static ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        NAMES = BUILDER
            .comment("List of player names used for skins")
            .defineList("names", Arrays.asList(
                "HellFirePvP",
                "iskall85",
                "BrryHrry",
                "HBomb94",
                "CaptainSparklez",
                "ChosenArchitect",
                "PeteZahHutt",
                "BaboAbe",
                "tangofrags",
                "KaraCorvus",
                "X33N",
                "rhiga",
                "falsesymmetry",
                "Stressmonster101",
                "RagingSkittles",
                "Seapeekay",
                "neentarts",
                "Douwsky",
                "5uppps",
                "CaptainPuffy",
                "truesymmetry",
                "Tubbo_",
                "KaptainWutax",
                "jmilthedude"), entry -> true);
        SKIN_CHANCE = BUILDER
            .comment("Chance for fighter to have skin")
            .defineInRange("skinChance", 10, 0, 100);
        DISABLE_STARS = BUILDER
            .comment("Disable stars in name that show fighter tier")
            .define("disableStars", false);
        NAME_URL = BUILDER
            .comment("Url of file containing names separated by newline (\\n)")
            .define("nameUrl", "");
        SOURCE = BUILDER
            .comment("Where should skinfighters get skin names from")
            .defineEnum("nameSource", SkinFighters.source.CONFIG, SkinFighters.source.values());
        DEBUG = BUILDER
            .comment("Enable debug logging to track fighter spawns (check logs/latest.log)")
            .define("debug", false);
        SPEC = BUILDER.build();
    }
}
