package Reuse;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;

public class SeleniumAIAnalyzer2 {
    private static final String GITHUB_TOKEN =
            System.getenv("GITHUB_TOKEN");
    private static final String API_URL = "https://models.github.ai/inference/chat/completions";
    public static String analyze(Throwable throwable) {
        String aiResponse = null;
        String prompt =
                "You are a senior Selenium automation engineer.\n" +
                        "Analyze the following Selenium test failure and provide:\n" +
                        "1- Root cause\n" +
                        "2- Why it happens\n" +
                        "3- Suggested fixes in java\n\n" +
                        "Failure Details:\n" +
                        throwable.toString() + "\n";
        try {
            aiResponse = callGPT(prompt);

            System.out.println("\n==============================");
            System.out.println("🤖 AI Selenium Failure Analysis (GPT-4o-mini)");
            System.out.println("==============================");
            System.out.println(aiResponse);

        } catch (Exception e) {
            System.out.println("❌ AI Analysis skipped: " + e.getMessage());
        }
        return aiResponse;
    }

    private static String callGPT(String prompt) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(40))
                .build();

        JSONObject body = new JSONObject();
        body.put("model", "GPT-4o-mini");
        body.put("temperature", 0.2);

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", prompt)
        );

        body.put("messages", messages);

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + GITHUB_TOKEN)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(
                        body.toString(),
                        MediaType.parse("application/json")
                ))
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        "HTTP Error: " + response.code() +
                                " - " + response.body().string()
                );
            }

            JSONObject json = new JSONObject(response.body().string());

            return json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }
}
