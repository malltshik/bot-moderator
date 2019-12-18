package ru.malltshik.botmoderator.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChannelMessage {
    private Object chatId;
    private String text;
    private String author;
    private Integer date;
}
