package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.quest.*;
import com.feed_the_beast.ftbquests.quest.task.Task;
import net.minecraft.entity.player.ServerPlayerEntity;
import pie.ilikepiefoo2.borealis.page.PageType;
import pie.ilikepiefoo2.borealis.tag.Tag;

import java.util.UUID;

public class QuestPage extends ChapterPage {
    protected final Quest quest;

    public QuestPage(UUID playerUUID, ServerPlayerEntity entity, QuestFile questFile, Chapter chapter, Quest quest)
    {
        super(playerUUID, entity, questFile, chapter);
        this.quest = quest;
    }

    @Override
    public PageType getPageType()
    {
        return ConfigHandler.COMMON.ftbqBorealisPage.get();
    }


    @Override
    public void body(Tag body){
        addHomePageTag(body);
        addChaptersTag(body,player,playerUUID);
        addChapterTag(body,player,playerUUID,chapter);
        addQuestTag(body,player,playerUUID,chapter,quest);
        body.h3("Title: "+getTitle(chapter));
        body.text("Subtitle: "+quest.subtitle);
        body.br();
        body.text("Description: "+toHTML(quest.description));
        body.br();

        body.h1("").a("Dependencies","#dependencies");
        Tag dependencyTable = body.table();
        Tag header = dependencyTable.tr();
        header.th().text("Dependency Title");
        header.th().text("Type");
        header.th().text("Status");
        quest.dependencies.listIterator().forEachRemaining(
                dependant -> addDependant(dependencyTable.tr(),chapter,playerData,playerUUID,dependant)
        );

        body.br();
        body.h1("").a("Tasks","#tasks");
        Tag taskTable = body.table();
        Tag taskHeader = taskTable.tr();
        taskHeader.th().text("Task Title");
        taskHeader.th().text("Type");
        taskHeader.th().text("Status");
        quest.tasks.listIterator().forEachRemaining(
                task -> addTask(taskTable.tr(),chapter,playerData,playerUUID,quest,task)
        );
    }

    public static void addDependant(Tag previous, Chapter chapter, PlayerData playerData, UUID playerUUID, QuestObject dependant)
    {
        String url = null;
        if(dependant.getObjectType().equals(QuestObjectType.NULL))
        {
            return;
        }
        if(dependant.getObjectType().equals(QuestObjectType.CHAPTER))
        {
            url = homeURL+playerUUID.toString()+"/"+chapter.id;
        } else if(dependant.getObjectType().equals(QuestObjectType.QUEST))
        {
            url = homeURL+playerUUID.toString()+"/"+chapter.id+"/"+dependant.id;
        }
        if(url != null) {
            previous.td().a(getTitle(dependant), url);
        }else{
            previous.td().text(getTitle(dependant));
        }
        previous.td().a(dependant.getObjectType().id, FTBQuestsHomePage.homeURL+dependant.getObjectType().getDeclaringClass().getName());
        appendProgress(previous.td(),playerData,dependant);
    }

    public static void addTask(Tag previous, Chapter chapter, PlayerData playerData, UUID playerUUID, Quest quest, Task task)
    {
        previous.td().a(getTitle(task),FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id+"/"+quest.id+"/"+task.id);
        previous.td().text(task.getType().getTypeForNBT());
        appendProgress(previous.td(),playerData,task);
    }

    public static void addQuestTag(Tag body, ServerPlayerEntity player, UUID playerUUID, Chapter chapter, Quest quest)
    {
        body.h2("Quest ").a(getTitle(quest),FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id+"/"+quest.id);
    }
}