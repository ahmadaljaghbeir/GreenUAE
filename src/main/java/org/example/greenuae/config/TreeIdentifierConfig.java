package org.example.greenuae.config;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Configuration
public class TreeIdentifierConfig {
    @Value("${plant.id.api.key}")
    private String PLANT_ID_API_KEY;
    @Value("${plant.id.url}")
    private String PLANT_ID_URL;

    public String identifyTreeSpecies(byte[] imageBytes) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Create a temporary file from the byte array
        Path tempFile = Files.createTempFile("tempTreeImage", ".jpg");
        Files.write(tempFile, imageBytes);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("organs", "leaf")
                .addFormDataPart("images", tempFile.getFileName().toString(),
                        RequestBody.create(tempFile.toFile(), MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url(PLANT_ID_URL)
                .addHeader("Api-Key", PLANT_ID_API_KEY)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Delete the temporary file
        Files.deleteIfExists(tempFile);

        if (jsonObject.get("suggestions").getAsJsonArray().size() > 0) {
            String speciesName = jsonObject.get("suggestions").getAsJsonArray()
                    .get(0).getAsJsonObject().get("plant_name").getAsString();
            return speciesName;
        } else {
            return "Unknown";
        }
    }
}
