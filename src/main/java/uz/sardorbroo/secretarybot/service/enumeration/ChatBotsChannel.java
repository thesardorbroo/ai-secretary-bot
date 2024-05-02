package uz.sardorbroo.secretarybot.service.enumeration;

import lombok.Getter;

@Getter
public enum ChatBotsChannel {
    TG("tg"),
    IG("ig"),
    FB("fb"),
    WA("wa"),
    ;
    private final String channel;

    ChatBotsChannel(String channel) {
        this.channel = channel;
    }
}
