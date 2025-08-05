package com.linkedinlearning.javaai.chat;

import dev.langchain4j.model.openai.OpenAiLanguageModel;
import dev.langchain4j.model.output.Response;

public class LanguageModelExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {
        OpenAiLanguageModel model = OpenAiLanguageModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        Response<String> response = model.generate("Please write 500 words about the fall of Rome.");
        System.out.println(response.content());
    }

}
