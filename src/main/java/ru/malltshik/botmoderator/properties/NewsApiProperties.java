package ru.malltshik.botmoderator.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.malltshik.botmoderator.extensions.news.NewsBotExtension;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@ConfigurationProperties(prefix = "news-api")
@ConditionalOnBean(NewsBotExtension.class)
public class NewsApiProperties {
    /**
     * News api by google. More: https://newsapi.org/s/google-news-api
     */
    @NotEmpty(message = "News api token required to build news loading service")
    private String token;
}
