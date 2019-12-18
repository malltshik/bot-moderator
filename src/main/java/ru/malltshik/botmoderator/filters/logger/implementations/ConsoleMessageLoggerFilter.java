package ru.malltshik.botmoderator.filters.logger.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.malltshik.botmoderator.filters.logger.MessageLoggerFilter;
import ru.malltshik.botmoderator.transport.ChannelMessage;

@Slf4j
@Component
public class ConsoleMessageLoggerFilter implements MessageLoggerFilter {
    @Override
    public void log(ChannelMessage msg) {
        log.info("Received message: {}", msg);
    }

    @Override
    public void filter(ChannelMessage msg) {
        log(msg);
    }
}
