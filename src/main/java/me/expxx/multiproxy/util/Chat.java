package me.expxx.multiproxy.util;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;


public class Chat {

    public static TextComponent translate(String msg) {
        return LegacyComponentSerializer.legacy('&').deserialize(msg);
    }
}
