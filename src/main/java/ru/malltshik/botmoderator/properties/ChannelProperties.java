package ru.malltshik.botmoderator.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@ConfigurationProperties(prefix = "channel")
public class ChannelProperties {
    /**
     * Main channel for bot processing. For instance telegram, viber, facebook, etc.
     */
    @NotEmpty(message = "Channel type is required for application")
    private String type;
}
