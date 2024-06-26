package me.expxx.multiproxy.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import me.expxx.multiproxy.MultiProxy;
import me.expxx.multiproxy.core.ProxyShutdown;
import me.expxx.multiproxy.util.Chat;

import java.time.Duration;

public class EndRewrite implements SimpleCommand {

    public static CommandMeta getMeta(CommandManager mngr) {
        return mngr.metaBuilder("end")
                .aliases("proxyend", "proxystop", "proxyreboot", "stop")
                .plugin(MultiProxy.getInstance())
                .build();
    }

    @Override
    public void execute(Invocation invocation) {
        invocation.source().sendMessage(Chat.translate("&6&lWARNING &6The proxy will shutdown in 5 seconds. You will now be transferred to another proxy."));
        ProxyShutdown.onShutdown();
        MultiProxy.getServer().getScheduler().buildTask(
                MultiProxy.getInstance(),
                () -> MultiProxy.getServer().shutdown(
                        Chat.translate("&cThis proxy has shutdown. If you see this, you unfortunately were not transferred in time.")
                )
        )
        .delay(Duration.ofSeconds(5))
        .schedule();
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("multiproxy.end");
    }
}
