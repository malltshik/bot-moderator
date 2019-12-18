package ru.malltshik.botmoderator.services.channels;

import ru.malltshik.botmoderator.transport.BotMessage;

public interface ChannelService {
    void sendMessage(BotMessage msg);
}
