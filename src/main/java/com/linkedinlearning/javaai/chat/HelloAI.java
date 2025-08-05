package com.linkedinlearning.javaai.chat;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class HelloAI {

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://localhost:12434/engines/v1") // Points to Docker Model Runner
                .modelName("ai/llama3.2:3B-Q4_K_M") // Model needs to be already pulled locally
//                .apiKey("demo") // Not needed for local models
                .build();

        String answer = model.chat("Say 'Hello World'");
        System.out.println(answer);
    }

}
