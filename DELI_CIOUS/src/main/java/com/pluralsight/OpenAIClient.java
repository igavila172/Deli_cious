package com.pluralsight;

import com.google.gson.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

public class OpenAIClient {

    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public static String getOrderFromNaturalLanguage(String userInput) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        JsonArray messagesArray = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content",
                "You are a helpful assistant for a sandwich shop. Respond ONLY with a JSON object in this format:\n" +
                        "{\n" +
                        "  \"sandwiches\": [\n" +
                        "    {\"type\": \"BLT\", \"size\": \"8\", \"bread\": \"white\", \"toasted\": true}\n" +
                        "  ],\n" +
                        "  \"drinks\": [\n" +
                        "    {\"flavor\": \"lemonade\", \"size\": \"medium\"}\n" +
                        "  ],\n" +
                        "  \"chips\": [\n" +
                        "    {\"type\": \"sour cream\"}\n" +
                        "  ]\n" +
                        "}\n" +
                        "Never include any explanations or other text — return only valid JSON that matches the structure above.");

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", userInput);

        messagesArray.add(systemMessage);
        messagesArray.add(userMessage);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o-mini");
        requestBody.addProperty("temperature", 0.5);
        requestBody.add("messages", messagesArray);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();

        if (responseJson.has("error")) {
            JsonObject error = responseJson.getAsJsonObject("error");
            String errorMessage = error.has("message") ? error.get("message").getAsString() : "Unknown error";
            throw new RuntimeException("OpenAI API error: " + errorMessage);
        }

        JsonArray choices = responseJson.getAsJsonArray("choices");
        if (choices == null || choices.size() == 0) {
            throw new RuntimeException("No response choices returned from OpenAI.");
        }

        JsonObject messageObject = choices.get(0).getAsJsonObject().getAsJsonObject("message");
        if (messageObject == null || !messageObject.has("content")) {
            throw new RuntimeException("Invalid response structure from OpenAI.");
        }

        return messageObject.get("content").getAsString().trim();
    }
}
