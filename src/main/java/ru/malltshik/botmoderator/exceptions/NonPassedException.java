package ru.malltshik.botmoderator.exceptions;

import lombok.Getter;
import ru.malltshik.botmoderator.filters.BotFilter;

@Getter
public class NonPassedException extends Exception {
    private String message;
    private BotFilter filter;

    public NonPassedException(String msg, BotFilter filter) {
        super(msg);
        this.filter = filter;
    }
}
