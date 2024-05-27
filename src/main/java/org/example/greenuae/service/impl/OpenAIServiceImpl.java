package org.example.greenuae.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.example.greenuae.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OpenAIServiceImpl implements OpenAIService {
    @Value("${open.ai.api.key}")
    private String OPENAI_API_KEY;
    @Value("${ai.id.url}")
    private String OPENAI_URL;

    @Override
    public String getTreeInformation(String treeSpecies, String plantingDate, String location) {
        OkHttpClient client = new OkHttpClient();
        String prompt = String.format(
                "I have a tree planting submission with the following details:\n" +
                        "- Tree Species: %s\n" +
                        "- Planting Date: %s\n" +
                        "- Location: %s\n\n" +
                        "Please provide detailed information about the tree species as pullet points, including:\n" +
                        "1. A brief description of the tree species.\n" +
                        "2. Environmental benefits of planting this tree, specifically in terms of carbon sequestration, air quality improvement, water management, biodiversity support, and urban heat island mitigation.\n" +
                        "3. Relevant statistics and data related to the environmental impact of this tree species.\n" +
                        "4. Based on the provided information, categorize the environmental impact of planting this tree as very low, low, medium, big, or significant.",
                treeSpecies, plantingDate, location
        );

        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        JsonArray messagesArray = new JsonArray();
        messagesArray.add((JsonElement) message);

        JsonObject json = new JsonObject();
        json.add("messages", messagesArray);
        json.addProperty("model", "gpt-3.5-turbo"); // Updated model
        json.addProperty("max_tokens", 500);

        RequestBody requestBody = RequestBody.create(
                json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(OPENAI_URL)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Check if the 'choices' element exists and is an array
            if (jsonObject.has("choices") && jsonObject.get("choices").isJsonArray()) {
                String text = jsonObject.get("choices").getAsJsonArray()
                        .get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();
                return text.trim();
            } else {
                System.err.println("Error: 'choices' element not found in the response.");
                return "Error: Unable to retrieve tree information. 'choices' element not found.";
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get tree information", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
