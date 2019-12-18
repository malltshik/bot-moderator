package ru.malltshik.botmoderator.services.channels.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.malltshik.botmoderator.properties.TelegramProperties;
import ru.malltshik.botmoderator.services.BotService;
import ru.malltshik.botmoderator.services.channels.ChannelService;
import ru.malltshik.botmoderator.transport.BotMessage;
import ru.malltshik.botmoderator.transport.ChannelMessage;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@Component
@ConditionalOnProperty(value = "channel.type", havingValue = "telegram")
public class TelegramChannelService implements ChannelService {

    private final BotService botService;
    private final TelegramProperties props;
    private TelegramBot api;

    public TelegramChannelService(@Lazy BotService botService, TelegramProperties props) {
        this.botService = botService;
        this.props = props;
    }

    @PostConstruct
    private void initChannel() {
        try {
            api = new TelegramBot(props.getToken());
        } catch (Exception e) {
            log.error("Unable to initialize telegram bot!", e);
            throw e;
        }
        api.setUpdatesListener(update -> {
            update.stream().map(Update::message)
                    .filter(Objects::nonNull)
                    .filter(m -> !StringUtils.isEmpty(m.text()))
                    .map(m -> new ChannelMessage(m.chat().id(), m.text(), m.authorSignature(), m.date()))
                    .forEach(botService::processMessage);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public void sendMessage(BotMessage msg) {
        SendResponse execute = api.execute(new SendMessage(msg.getChatId(), msg.getText()));
        if(!execute.isOk()) {
            log.warn("Unable to send message to telegram {}", execute.description());
            api.execute(new SendMessage(msg.getChatId(), "Something goes wrong.."));
        }
    }

}
