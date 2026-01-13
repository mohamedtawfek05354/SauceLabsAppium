package Reuse;


import okhttp3.*;
import org.json.JSONObject;

import java.time.Duration;
import java.util.Arrays;

public class SeleniumAIAnalyzer {

    private static final String OPENAI_API_KEY =
            System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/responses";

    public static String analyze(Throwable throwable) {
        String aiResponse = null;
        String prompt =
                "You are a senior Selenium automation engineer.\n" +
                        "Analyze the following Selenium test failure and provide:\n" +
                        "1- Root cause\n" +
                        "2- Why it happens\n" +
                        "3- Suggested fixes\n\n" +
                        "Exception: " + throwable.getClass().getSimpleName() + "\n" +
                        "Message: " + throwable.getMessage() + "\n" +
                        "Stacktrace:\n" + Arrays.toString(throwable.getStackTrace());

        try {
            aiResponse = callOpenAI(prompt);

            System.out.println("\n==============================");
            System.out.println("🤖 AI Selenium Failure Analysis");
            System.out.println("==============================");
            System.out.println(aiResponse);

        } catch (Exception e) {
            System.out.println("❌ AI Analysis skipped: " + e.getMessage());
        }
        return aiResponse;
    }

    private static String callOpenAI(String prompt) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofSeconds(30))
                .build();

        JSONObject body = new JSONObject();
        body.put("model", "gpt-4o-mini");
        body.put("input", prompt);

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(
                        body.toString(),
                        MediaType.parse("application/json")
                ))
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException("HTTP Error: " + response.code());
            }

            JSONObject json = new JSONObject(response.body().string());


            return json
                    .getJSONArray("output")
                    .getJSONObject(0)
                    .getJSONArray("content")
                    .getJSONObject(0)
                    .getString("text");
        }
    }
}
