package pie.ilikepiefoo2.ftbqborealis;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import pie.ilikepiefoo2.borealis.page.PageType;

public final class ConfigHandler {
    public static class Common {
        public final ForgeConfigSpec.EnumValue<PageType> ftbqBorealisPage;
        public final ForgeConfigSpec.EnumValue<PageType> chaptersPage;
        public final ForgeConfigSpec.EnumValue<PageType> questsPage;
        public final ForgeConfigSpec.BooleanValue includeHiddenChapters;
        public final ForgeConfigSpec.BooleanValue includeHiddenQuests;
        public final ForgeConfigSpec.BooleanValue hideNotStartedChapters;
        public final ForgeConfigSpec.BooleanValue hideNotStartedQuests;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("ftbqBorealisPage");
            ftbqBorealisPage = builder
                    .comment("Enable/Disable the FTBQ Borealis Page entirely.")
                    .defineEnum("ftbqBorealisPage", PageType.ENABLED, PageType.values());
            chaptersPage = builder
                    .comment("Enable/Disable the Chapters Page entirely.")
                    .defineEnum("chaptersPage", PageType.ENABLED, PageType.values());
            includeHiddenChapters = builder
                    .comment("Include hidden Chapters.")
                    .define("includeHiddenChapters",false);
            hideNotStartedChapters = builder
                    .comment("Hide Chapters that haven't been started yet.")
                    .define("hideNotStartedChapters",false);
            questsPage = builder
                    .comment("Enable/Disable the Quests Page entirely.")
                    .defineEnum("questsPage", PageType.ENABLED, PageType.values());
            includeHiddenQuests = builder
                    .comment("Include hidden Quests.")
                    .define("includeHiddenQuests",false);
            hideNotStartedQuests = builder
                    .comment("Hide Quests that haven't been started yet.")
                    .define("hideNotStartedQuests",false);
            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void onConfigLoad()
    {
        //blacklistedMods = COMMON.blacklistedMods.get().stream().map(ResourceLocation::new).collect(Collectors.toSet());
    }
}
