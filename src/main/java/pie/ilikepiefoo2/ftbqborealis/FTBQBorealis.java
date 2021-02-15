package pie.ilikepiefoo2.ftbqborealis;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ftbqborealis")
public class FTBQBorealis {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "ftbqborealis";
    public static final String MOD_NAME = "FTBQBorealis";
    public FTBQBorealis()
    {
        LOGGER.info("FTBQuests Integration for "+ FTBQBorealis.MOD_NAME +" initializing...");
        MinecraftForge.EVENT_BUS.addListener(FTBQuestsEventHandler::homePageEvent);
        MinecraftForge.EVENT_BUS.addListener(FTBQuestsEventHandler::onPageEvent);
        MinecraftForge.EVENT_BUS.addListener(FTBQuestsEventHandler::onPlayerJoin);
        MinecraftForge.EVENT_BUS.addListener(FTBQuestsEventHandler::onPlayerLeave);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener((ModConfig.Reloading e) -> ConfigHandler.onConfigLoad());
        FMLJavaModLoadingContext.get().getModEventBus().addListener((ModConfig.Loading e) -> ConfigHandler.onConfigLoad());

    }
}
