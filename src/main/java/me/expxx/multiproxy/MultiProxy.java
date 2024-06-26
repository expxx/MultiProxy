package me.expxx.multiproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.expxx.multiproxy.command.EndRewrite;
import me.expxx.multiproxy.command.Reload;
import me.expxx.multiproxy.command.SendProxy;
import me.expxx.multiproxy.core.ProxyShutdown;
import me.expxx.multiproxy.util.Config;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "multiproxy",
        name = "MultiProxy",
        version = "1.0.0"
)
public class MultiProxy {

    private static Logger logger;
    private Path dir;
    private static ProxyServer server;
    private static MultiProxy instance;

    @Inject
    public MultiProxy(
            ProxyServer srv,
            Logger log,
            @DataDirectory Path dataDir
    ) {
        server = srv;
        this.dir = dataDir;
        logger = log;
        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        new Config(dir);
        Config.loadConfig();
        logger.info("Loaded Config");

        CommandManager manager = server.getCommandManager();

        manager.register(Reload.getMeta(manager), new Reload());
        manager.register(SendProxy.getMeta(manager), new SendProxy());
        manager.register(EndRewrite.getMeta(manager), new EndRewrite());
        logger.info("Registered Commands");

        getServer().getEventManager().register(this, new ProxyShutdown());
        logger.info("Registered Events");
    }

    public static ProxyServer getServer() {
        return server;
    }

    public static MultiProxy getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }
}
