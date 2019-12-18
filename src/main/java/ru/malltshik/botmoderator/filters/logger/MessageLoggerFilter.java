package ru.malltshik.botmoderator.filters.logger;

import ru.malltshik.botmoderator.filters.BotFilter;
import ru.malltshik.botmoderator.transport.ChannelMessage;

public interface MessageLoggerFilter extends BotFilter {
    void log(ChannelMessage msg);
}
