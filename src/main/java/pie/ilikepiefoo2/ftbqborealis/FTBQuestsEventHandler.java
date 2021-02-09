package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.FTBQuests;
import com.feed_the_beast.ftbquests.quest.Chapter;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestFile;
import com.feed_the_beast.ftbquests.quest.QuestObject;
import com.feed_the_beast.ftbquests.quest.task.Task;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pie.ilikepiefoo2.borealis.BorealisHomePageEvent;
import pie.ilikepiefoo2.borealis.BorealisPageEvent;
import pie.ilikepiefoo2.borealis.page.HomePageEntry;
import pie.ilikepiefoo2.borealis.page.WebPageNotFound;

import java.util.List;
import java.util.UUID;

import static pie.ilikepiefoo2.ftbqborealis.FTBQuestsHomePage.homeURI;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class FTBQuestsEventHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void homePageEvent(BorealisHomePageEvent event)
    {
        event.add(new HomePageEntry("FTB Quest Data",homeURI,"https://media.forgecdn.net/avatars/thumbnails/275/363/64/64/637261948352026071.png"));
    }

    @SubscribeEvent
    public static void onPageEvent(BorealisPageEvent event)
    {
        if(event.getSplitUri()[0].equals(homeURI)) {
            UUID playerUUID;
            ServerPlayerEntity entity;
            QuestFile questFile;
            Chapter chapter;
            Quest quest;
            Task task;
            try {
                switch (event.getSplitUri().length) {
                    case 1:
                        event.returnPage(FTBQuestsHomePage.getInstance());
                        break;
                    case 2:
                        playerUUID = UUID.fromString(event.getSplitUri()[1]);
                        entity = FTBQuestsHomePage.getInstance().getServer().getPlayerList().getPlayerByUUID(playerUUID);
                        questFile = FTBQuests.PROXY.getQuestFile(entity.world);
                        event.returnPage(new ChaptersPage(playerUUID, entity, questFile));
                        break;
                    case 3:
                        playerUUID = UUID.fromString(event.getSplitUri()[1]);
                        entity = FTBQuestsHomePage.getInstance().getServer().getPlayerList().getPlayerByUUID(playerUUID);
                        questFile = FTBQuests.PROXY.getQuestFile(entity.world);
                        chapter = findById(questFile, questFile.chapters, event.getSplitUri()[2]);
                        event.returnPage(new ChapterPage(playerUUID, entity, questFile,chapter));
                        break;
                    case 4:
                        playerUUID = UUID.fromString(event.getSplitUri()[1]);
                        entity = FTBQuestsHomePage.getInstance().getServer().getPlayerList().getPlayerByUUID(playerUUID);
                        questFile = FTBQuests.PROXY.getQuestFile(entity.world);
                        chapter = findById(questFile, questFile.chapters, event.getSplitUri()[2]);
                        quest = findById(questFile, chapter.quests, event.getSplitUri()[3]);
                        event.returnPage(new QuestPage(playerUUID, entity, questFile,chapter,quest));
                        break;
                    case 5:
                        playerUUID = UUID.fromString(event.getSplitUri()[1]);
                        entity = FTBQuestsHomePage.getInstance().getServer().getPlayerList().getPlayerByUUID(playerUUID);
                        questFile = FTBQuests.PROXY.getQuestFile(entity.world);
                        chapter = findById(questFile, questFile.chapters,event.getSplitUri()[2]);
                        quest = findById(questFile, chapter.quests, event.getSplitUri()[3]);
                        task = findById(questFile, quest.tasks, event.getSplitUri()[4]);
                        event.returnPage(new TaskPage(playerUUID, entity, questFile, chapter, quest, task));
                        break;
                    default:
                        LOGGER.error("Page not found: "+event.getUri());
                        event.returnPage(new WebPageNotFound(event.getUri()));
                        break;
                }
            }catch(Exception e) {
                LOGGER.error(e);
                event.returnPage(new WebPageNotFound(event.getUri()));
            }
        }
    }

    public static <T extends QuestObject> T findById(QuestFile file, List<T> quests, String id) throws Exception
    {
        int ID = Integer.parseInt(id);
        for(T quest : quests) {
            if(quest.id == ID) {
                return quest;
            }
        }
        throw new Exception("ID "+ID+" not found.");
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        FTBQuestsHomePage.dirty();
    }
    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event)
    {
        FTBQuestsHomePage.dirty();
    }
}