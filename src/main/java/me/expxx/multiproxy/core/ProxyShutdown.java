package me.expxx.multiproxy.core;

import com.velocitypowered.api.proxy.Player;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.expxx.multiproxy.MultiProxy;
import me.expxx.multiproxy.util.Config;

public class ProxyShutdown {

    public static void onShutdown() {
        YamlDocument config = Config.getConfig();
        String thisProxy = config.getString("thisProxy");
        String sendTo = config.getString("proxies." + thisProxy + ".sendTo");
        for(Player p : MultiProxy.getServer().getAllPlayers()) {
            Transfer.transfer(p, sendTo, true);
        }
    }

}
