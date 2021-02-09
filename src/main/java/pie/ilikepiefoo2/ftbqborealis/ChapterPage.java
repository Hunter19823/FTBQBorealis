package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.quest.Chapter;
import com.feed_the_beast.ftbquests.quest.PlayerData;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestFile;
import net.minecraft.entity.player.ServerPlayerEntity;
import pie.ilikepiefoo2.borealis.tag.Tag;

import java.util.UUID;

public class ChapterPage extends ChaptersPage {
    protected final Chapter chapter;

    public ChapterPage(UUID playerUUID, ServerPlayerEntity entity, QuestFile questFile, Chapter chapter)
    {
        super(playerUUID,entity,questFile);
        this.chapter = chapter;
    }

    @Override
    public void body(Tag body)
    {
        addHomePageTag(body);
        addChaptersTag(body,player,playerUUID);
        addChapterTag(body,player,playerUUID,chapter);
        body.h3("Title: "+getTitle(chapter));
        body.text("Subtitle: "+toHTML(chapter.subtitle));
        body.br();

        body.h1("").a("Quests","#quests");

        Tag questTable = body.table();
        Tag topRow = questTable.tr();

        topRow.th().text("Title");
        topRow.th().text("Status");
        topRow.th().text("Subtext");

        chapter.quests.listIterator().forEachRemaining(
                quest -> addQuest(questTable.tr(), chapter, playerData, playerUUID, quest)
        );
        //FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id+"/";
    }

    public static void addQuest(Tag previous, Chapter chapter, PlayerData playerData, UUID playerUUID, Quest quest)
    {
        previous.td().a(getTitle(quest),homeURL+playerUUID.toString()+"/"+chapter.id+"/"+quest.id);
        appendProgress(previous.td(),playerData,quest);
        previous.td().p(quest.subtitle);
    }

    public static void addChapterTag(Tag body, ServerPlayerEntity player, UUID playerUUID, Chapter chapter)
    {
        body.h2("Chapter ").a(getTitle(chapter),FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id);
    }

}