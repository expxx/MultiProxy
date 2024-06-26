package me.expxx.multiproxy.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import me.expxx.multiproxy.MultiProxy;
import me.expxx.multiproxy.util.Chat;
import me.expxx.multiproxy.util.Config;

public class Reload implements SimpleCommand {

    public static CommandMeta getMeta(CommandManager mngr) {
        return mngr.metaBuilder("preload")
                .aliases()
                .plugin(MultiProxy.getInstance())
                .build();
    }

    @Override
    public void execute(Invocation invocation) {
        Config.loadConfig();
        invocation.source().sendMessage(Chat.translate("&a&lSUCCESS &aReloaded Config"));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("multiproxy.reload");
    }
}
