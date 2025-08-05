package com.linkedinlearning.javaai.debugging;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.List;

public class ObservabilityExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {

        ChatModelListener chatModelListener = new ChatModelListener() {
            @Override
            public void onRequest(ChatModelRequestContext requestContext) {
                System.out.println("Received request");
                System.out.println(requestContext.chatRequest());
            }

            @Override
            public void onResponse(ChatModelResponseContext responseContext) {
                System.out.println("Received response");
                System.out.println(responseContext.chatResponse());
            }

            @Override
            public void onError(ChatModelErrorContext errorContext) {
                System.out.println("Received error");
                System.out.println(errorContext.error());
            }
        };

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .listeners(List.of(chatModelListener))
                .build();

        model.chat("Who watches the watchmen?");
    }

}
