package ru.malltshik.botmoderator.filters;

import ru.malltshik.botmoderator.exceptions.NonPassedException;
import ru.malltshik.botmoderator.transport.ChannelMessage;

public interface BotFilter {
    void filter(ChannelMessage msg) throws NonPassedException;
}
