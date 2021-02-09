package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.quest.Chapter;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.QuestFile;
import com.feed_the_beast.ftbquests.quest.task.Task;
import net.minecraft.entity.player.ServerPlayerEntity;
import pie.ilikepiefoo2.borealis.integration.kubejs.KubeJSHomePage;
import pie.ilikepiefoo2.borealis.tag.Tag;

import java.util.UUID;

public class TaskPage extends QuestPage {
    protected final Task task;
    public TaskPage(UUID playerUUID, ServerPlayerEntity entity, QuestFile questFile, Chapter chapter, Quest quest, Task task)
    {
        super(playerUUID, entity, questFile, chapter, quest);
        this.task = task;
    }


    @Override
    public void body(Tag body)
    {
        addHomePageTag(body);
        addChaptersTag(body, player, playerUUID);
        addChapterTag(body, player, playerUUID, chapter);
        addQuestTag(body, player, playerUUID, chapter, quest);
        addTaskTag(body, player, playerUUID, chapter, quest, task);
        body.h1("Task: "+getTitle(task));
        body.h2("Task Type: ").a(task.getType().getTypeForNBT(), KubeJSHomePage.homeURL+task.getClass().getName());
        body.br();
        body.text("This page looks quite empty due to how Task Types are handled. Each are handled in a special way requiring hardcoding of each type.");
    }

    public static void addTaskTag(Tag body, ServerPlayerEntity player, UUID playerUUID, Chapter chapter, Quest quest, Task task)
    {
        body.h2("Task ").a(getTitle(task),FTBQuestsHomePage.homeURL+playerUUID.toString()+"/"+chapter.id+"/"+quest.id+"/"+task.id);
    }
}
