package ru.malltshik.botmoderator.extensions.news;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ru.malltshik.botmoderator.extensions.BotExtension;
import ru.malltshik.botmoderator.properties.NewsApiProperties;
import ru.malltshik.botmoderator.transport.BotMessage;
import ru.malltshik.botmoderator.transport.ChannelMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsBotExtension implements BotExtension {

    private final static String CMD = "/news";
    private final static String URL = "https://newsapi.org/v2/everything";
    private final RestTemplate restTemplate;
    private final NewsApiProperties props;

    @Override
    public Optional<BotMessage> process(ChannelMessage msg) {
        if (msg.getText().startsWith(CMD)) {
            BotMessage.BotMessageBuilder builder = BotMessage.builder().chatId(msg.getChatId());
            builder.text(loadNews(msg));
            return Optional.of(builder.build());
        }
        return Optional.empty();
    }

    private String loadNews(ChannelMessage msg) {
        try {
            String q = msg.getText().substring(CMD.length());
            if (StringUtils.isEmpty(q)) {
                return "You need to add query after /news to get news";
            }
            ResponseEntity<JsonNode> entity = restTemplate.getForEntity(
                    String.format("%s?q=%s&sortBy=popularity&apiKey=%s",
                            URL, URLEncoder.encode(q, "UTF-8"), props.getToken()),
                    JsonNode.class);
            if (entity.getStatusCode().is2xxSuccessful()) {
                JsonNode body = entity.getBody();
                if(body == null || !body.hasNonNull("totalResults") || body.findValue("totalResults").asInt() == 0) {
                    return "Those kind of news not found";
                }
                int i = new Random().nextInt(20);
                String author = body.findValuesAsText("author").get(i);
                String title = body.findValuesAsText("title").get(i);
                String description = body.findValuesAsText("description").get(i);
                String url = body.findValuesAsText("url").get(i);
                return String.format("**%s**\n%s\n%s\n%s", author, title, description, url);
            } else {
                log.error("News service returns error response {}", entity.getStatusCode());
                return "News service is unavailable";
            }
        } catch (UnsupportedEncodingException e) {
            log.debug("Fail to encode user message", e);
            return "Unfortunately your query cannot be encoded to request news";
        }
    }

}
