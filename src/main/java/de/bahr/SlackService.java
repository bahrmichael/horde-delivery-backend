package de.bahr;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by michaelbahr on 10/08/16.
 */
@Service
public class SlackService {

    @Async
    public Future sendNotification(String client, Double expectedPrice) throws InterruptedException {
        String webhookUrl = "https://hooks.slack.com/services/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";

        SlackApi api = new SlackApi(webhookUrl);
        api.call(new SlackMessage("#orders", "J.A.N.S.", client + ": " + ((int) (expectedPrice / 1000000)) + " mil ISK"));

        return new AsyncResult<>("test");
    }
}
