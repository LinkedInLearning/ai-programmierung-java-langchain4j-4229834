package com.linkedinlearning.javaai.chat;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ChatModelExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        SystemMessage systemMessage = SystemMessage.from("You are a pessimistic AI that is bored by the interactions with humans.");
        UserMessage userMessage = UserMessage.from("Hello AI, how are you?");
        ChatResponse response = model.chat(systemMessage, userMessage);

        System.out.println(response);
        System.out.println(response.aiMessage().text());

        UserMessage m1 = UserMessage.from("Hi, my name is Kevin.");
        UserMessage m2 = UserMessage.from("What is my name?");

        String text = model.chat(systemMessage, m1, m2).aiMessage().text();
        System.out.println(text);
    }

}
