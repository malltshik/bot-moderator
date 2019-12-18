package ru.malltshik.botmoderator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.malltshik.botmoderator.exceptions.NonPassedException;
import ru.malltshik.botmoderator.extensions.BotExtension;
import ru.malltshik.botmoderator.filters.BotFilter;
import ru.malltshik.botmoderator.services.channels.ChannelService;
import ru.malltshik.botmoderator.transport.ChannelMessage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotService {

    private final List<BotFilter> filters;
    private final List<BotExtension> extensions;
    private final ChannelService channel;

    public void processMessage(ChannelMessage msg) {
        if (msg == null) return;
        for (BotFilter filter : filters) {
            try {
                filter.filter(msg);
            } catch (NonPassedException e) {
                log.info("Message {} didn't pass filter {} cause {}", msg, e.getFilter(), e.getMessage());
                log.trace("Details", e);
                return;
            }
        }
        extensions.stream().map(e -> e.process(msg))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(channel::sendMessage);
    }

}
