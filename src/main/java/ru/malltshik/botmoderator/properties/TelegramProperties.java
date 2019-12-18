package ru.malltshik.botmoderator.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@ConfigurationProperties(prefix = "telegram")
@ConditionalOnProperty(value = "channel.type", havingValue = "telegram")
public class TelegramProperties {
    /**
     * Token for telegram bot. More: https://core.telegram.org/bots/api
     */
    @NotEmpty(message = "Telegram channel requires token")
    private String token;
}
