package com.linkedinlearning.javaai.chat.challenge;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class CalculatorService {

    static final String MODEL_NAME = "ai/qwen3:8B-Q4_0";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(100))
                .tools(new CalculatorTools())
                .build();

        String response = assistant.chat("What is 10 + 15?");
        System.out.println(response);

        response = assistant.chat("Multiply the result by 100");
        System.out.println(response);
    }

    interface Assistant {
        String chat(String userMessage);
    }

    static class CalculatorTools {
        @Tool("Add 2 integers")
        int add(int a, int b) {
            return a + b;
        }

        @Tool("Subtract 2 integers")
        int sub(int a, int b) {
            return a - b;
        }

        @Tool("Multiply 2 integers")
        int multiply(int a, int b) {
            return a * b;
        }

        @Tool("Divide 2 integers")
        int divide(int a, int b) {
            return a / b;
        }
    }

}
