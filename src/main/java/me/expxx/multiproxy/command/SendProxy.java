package me.expxx.multiproxy.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.expxx.multiproxy.MultiProxy;
import me.expxx.multiproxy.core.Transfer;
import me.expxx.multiproxy.util.Chat;
import me.expxx.multiproxy.util.Config;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SendProxy implements SimpleCommand {

    public static CommandMeta getMeta(CommandManager mngr) {
        return mngr.metaBuilder("sendproxy")
                .plugin(MultiProxy.getInstance())
                .aliases()
                .build();
    }


    @Override
    public void execute(Invocation invocation) {
        String player = invocation.arguments()[0];
        String target = invocation.arguments()[1];

        Optional<Player> player1 = MultiProxy.getServer().getPlayer(player);
        if(player1.isEmpty()) {
            invocation.source().sendMessage(Chat.translate("&c&lERROR &cThe player you specified could not be found."));
            return;
        }
        Player plr = player1.get();
        YamlDocument config = Config.getConfig();
        boolean testCase = config.getString("proxies." + target + ".ip").isBlank();
        if(testCase) {
            invocation.source().sendMessage(Chat.translate("&c&lERROR &cThe Target Proxy you specified could not be found."));
            return;
        }
        Transfer.transfer(plr, target, false);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        if(invocation.arguments().length == 0) {
            Collection<Player> player = MultiProxy.getServer().getAllPlayers();
            List<String> players = new ArrayList<>();
            player.forEach((e) -> {
                players.add(e.getGameProfile().getName());
            });
            return CompletableFuture.completedFuture(players);
        } else if(invocation.arguments().length == 1) {
            YamlDocument config = Config.getConfig();
            List<String> stringList = new ArrayList<>();
            Section section = config.getSection("proxies");
            Set<Object> set = section.getKeys();
            set.forEach((e) -> {
                MultiProxy.getLogger().warn(e.toString());
                stringList.add(e.toString());
            });
            return CompletableFuture.completedFuture(stringList);
        } else {
            List<String> err = new ArrayList<>();
            return CompletableFuture.completedFuture(err);
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("multiproxy.send");
    }
}
