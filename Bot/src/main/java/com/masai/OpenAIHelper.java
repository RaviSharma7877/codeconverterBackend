package com.masai;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAIHelper {
    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private String extractGeneratedContent(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // Extract the assistant's reply
            JsonNode choices = jsonNode.get("choices");
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode choice = choices.get(0);
                JsonNode message = choice.get("message");
                if (message != null) {
                    return message.get("content").asText();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error occurred while extracting content";
    }

    public String generateText(String userMessage) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(OPENAI_API_URL);
            request.addHeader("Authorization", "Bearer " + OPENAI_API_KEY);
            request.addHeader("Content-Type", "application/json");

            String jsonBody = "{" +
                "\"model\": \"gpt-3.5-turbo\"," +
                "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + userMessage + "\"}" +
                "]" +
            "}";

            request.setEntity(new StringEntity(jsonBody));

            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            return extractGeneratedContent(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public String summarizeText(String userMessage) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(OPENAI_API_URL);
            request.addHeader("Authorization", "Bearer " + OPENAI_API_KEY);
            request.addHeader("Content-Type", "application/json");

            String jsonBody = "{" +
                "\"model\": \"gpt-3.5-turbo\"," +
                "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}," +
                    "{\"role\": \"user\", \"content\": \"Summarize: " + userMessage + "\"}" +
                "]" +
            "}";

            request.setEntity(new StringEntity(jsonBody));

            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            return extractGeneratedContent(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public String translateText(String userMessage, String sourceLanguage, String targetLanguage) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(OPENAI_API_URL);
            request.addHeader("Authorization", "Bearer " + OPENAI_API_KEY);
            request.addHeader("Content-Type", "application/json");

            String jsonBody = "{" +
                "\"model\": \"gpt-3.5-turbo\"," +
                "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}," +
                    "{\"role\": \"user\", \"content\": \"Translate the following text from " + sourceLanguage + " to " + targetLanguage + ": " + userMessage + "\"}" +
                "]" +
            "}";

            request.setEntity(new StringEntity(jsonBody));

            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            return extractGeneratedContent(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}

