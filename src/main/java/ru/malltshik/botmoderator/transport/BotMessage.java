package ru.malltshik.botmoderator.transport;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BotMessage {
    private Object chatId;
    private String text;
}
