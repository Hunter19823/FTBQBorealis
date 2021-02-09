package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.quest.Chapter;
import com.feed_the_beast.ftbquests.quest.PlayerData;
import com.feed_the_beast.ftbquests.quest.QuestFile;
import com.feed_the_beast.ftbquests.quest.QuestObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import pie.ilikepiefoo2.borealis.tag.Tag;

import java.util.List;
import java.util.UUID;

public class ChaptersPage extends FTBQuestsHomePage {
    protected final ServerPlayerEntity player;
    protected final QuestFile questFile;
    protected final UUID playerUUID;
    protected final PlayerData playerData;
    public ChaptersPage(UUID playerUUID, ServerPlayerEntity entity, QuestFile questFile)
    {
        this.playerUUID = playerUUID;
        this.player = entity;
        this.questFile = questFile;
        this.playerData = questFile.getData(this.playerUUID);
    }

    @Override
    public void body(Tag body)
    {
        addHomePageTag(body);
        addChaptersTag(body,player,playerUUID);
        body.h1("").a("Chapters","#chapters");
        Tag chapterTable = body.table();
        Tag topRow = chapterTable.tr();
        topRow.th().text("Chapter Title");
        topRow.th().text("Status");
        topRow.th().text("Subtitle");
        questFile.chapters.listIterator().forEachRemaining(
                chapter -> {
                    addChapter(chapterTable.tr(),chapter,playerData, playerUUID);
                }
        );
    }

    public static void addChapter(Tag previous, Chapter chapter, PlayerData playerData, UUID playerUUID)
    {
        // TODO add prevention of invisible chapters.
        previous.td().a(getTitle(chapter),FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id);
        appendProgress(previous.td(),playerData,chapter);
        previous.td().text(toHTML(chapter.subtitle));
    }

    public static void addChaptersTag(Tag body, ServerPlayerEntity player, UUID playerUUID)
    {
        body.h2("Chapters Page for ").a(player.getGameProfile().getName(),FTBQuestsHomePage.homeURL+playerUUID.toString());
    }

    public static void appendProgress(Tag previous, PlayerData data, QuestObject quest)
    {
        if(data.isComplete(quest))
        {
            previous.text("Completed");
        }else if(data.isStarted(quest))
        {
            previous.text("Started (%"+data.getRelativeProgress(quest)+")");
        }else{
            previous.text("Not Started/Unknown");
        }
    }

    public static String toHTML(List<String> subtext)
    {
        StringBuilder output = new StringBuilder();
        subtext.listIterator().forEachRemaining(string -> output.append(string+"\n"));
        return output.toString();
    }

}