package pie.ilikepiefoo2.ftbqborealis;

import com.feed_the_beast.ftbquests.quest.QuestObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import pie.ilikepiefoo2.borealis.Borealis;
import pie.ilikepiefoo2.borealis.page.HTTPWebPage;
import pie.ilikepiefoo2.borealis.tag.Tag;

public class FTBQuestsHomePage extends HTTPWebPage {
    private static MinecraftServer server;
    public static final String homeURI = "ftbquests";
    public static final String homeURL = "/"+homeURI+"/";

    public FTBQuestsHomePage()
    {
        server = Borealis.getServer().getMinecraftServer();
    }

    private static FTBQuestsHomePage instance;
    public static FTBQuestsHomePage getInstance()
    {
        if(instance == null || server == null)
        {
            instance = new FTBQuestsHomePage();
        }
        return instance;
    }

    public MinecraftServer getServer()
    {
        return server;
    }

    @Override
    public void body(Tag body)
    {
        addHomePageTag(body);
        body.br();
        body.h3("Online Players: ");
        Tag table = body.table();
        table.tr().th().text("Player");
        for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
            table.tr().td().a(player.getGameProfile().getName(),homeURL+player.getGameProfile().getId().toString());
        }
    }

    public static void dirty()
    {
        server = null;
    }

    public static void addHomePageTag(Tag body)
    {
        body.br();
        body.h1("").a("FTBQuests Homepage",FTBQuestsHomePage.homeURL);
        body.br();
    }

    public static String getTitle(QuestObject object)
    {
        return object.title.length() == 0 ? "Untitled" : object.title;
    }
}