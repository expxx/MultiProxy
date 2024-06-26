package me.expxx.multiproxy.core;

import com.velocitypowered.api.proxy.Player;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.expxx.multiproxy.MultiProxy;
import me.expxx.multiproxy.util.Chat;
import me.expxx.multiproxy.util.Config;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.title.TitlePart;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

public class Transfer {

    public static void transfer(Player player, String target, boolean immediate) {
        if(immediate) {
            String ip = Config.getConfig().getString("proxies." + target + ".ip");
            int port = Config.getConfig().getInt("proxies." + target + ".port");
            MultiProxy.getLogger().info("Sending to: " + ip + ":" + port);
            InetSocketAddress address = new InetSocketAddress(ip, port);
            player.transferToHost(address);
        } else {
            player.sendActionBar(Chat.translate("&6&lWARN &6You will be transferred to another proxy in &n5 seconds&6."));
            player.sendTitlePart(TitlePart.TITLE, Chat.translate("&6&lWARNING"));
            player.sendTitlePart(TitlePart.SUBTITLE, Chat.translate("&6You will be transferred to another proxy in &n5 seconds&6."));
            MultiProxy.getServer().getScheduler()
                    .buildTask(MultiProxy.getInstance(), () -> {
                        player.sendMessage(Chat.translate("&aYou are being transferred."));
                        Transfer.transfer(player, target, true);
                    })
                    .delay(Duration.ofSeconds(5))
                    .schedule();
        }
    }

}
