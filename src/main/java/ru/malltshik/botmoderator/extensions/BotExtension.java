package ru.malltshik.botmoderator.extensions;

import ru.malltshik.botmoderator.transport.BotMessage;
import ru.malltshik.botmoderator.transport.ChannelMessage;

import java.util.Optional;

public interface BotExtension {
    Optional<BotMessage> process(ChannelMessage msg);
}
