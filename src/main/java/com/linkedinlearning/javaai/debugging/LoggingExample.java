package com.linkedinlearning.javaai.debugging;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class LoggingExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .logRequests(true)
                .logResponses(true)
                .build();

        ConversationalChain chain = ConversationalChain.builder()
                .chatModel(model)
                .build();

        chain.execute("Which famous book was written by JRR Tolkien? Give me just the most famous one");
        chain.execute("What is the name of the main character in that book?");
    }

}
