package com.linkedinlearning.javaai.chat;

import dev.langchain4j.agent.tool.*;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.List;

public class ToolCallingExample {

    static final String MODEL_NAME = "ai/qwen3:8B-Q4_0";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        String response = model.chat("How is the weather in Gelsenkirchen tomorrow?");
        System.out.println(response);

        List<ToolSpecification> tools = ToolSpecifications.toolSpecificationsFrom(WeatherTools.class);

        UserMessage userMessage = UserMessage.from("How is the weather in Gelsenkirchen tomorrow?");
        ChatRequest request = ChatRequest.builder()
                .toolSpecifications(tools)
                .messages(userMessage)
                .build();

        AiMessage aiMessage = model.chat(request).aiMessage();
        if (aiMessage.hasToolExecutionRequests()) {

            // get first tool execution request
            ToolExecutionRequest toolExecutionRequest = aiMessage.toolExecutionRequests().getFirst();

            if (toolExecutionRequest.name().equals("getWeather")) {
                WeatherTools weatherTools = new WeatherTools();
                String argument = toolExecutionRequest.arguments();

                // Poor man's JSON parsing
                int start = argument.indexOf(":\"") + 2;
                int end = argument.indexOf("\"", start);

                String argumentValue = argument.substring(start, end);

                String toolResult = weatherTools.getWeather(argumentValue);

                ToolExecutionResultMessage toolExecutionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest, toolResult);

                ChatRequest toolResultRequest = ChatRequest.builder()
                        .toolSpecifications(tools)
                        .messages(userMessage, aiMessage, toolExecutionResultMessage)
                        .build();

                AiMessage finalResponse = model.chat(toolResultRequest).aiMessage();
                System.out.println(finalResponse.text());
            }

        } else {
            System.out.println("No tool execution requests in the response.");
        }

    }

    public static class WeatherTools {

        @Tool("Returns the weather forecast for a given city")
        public String getWeather(@P("The city for which the weather forecast should be returned") String city) {
            // This is a mock implementation. In a real-world scenario, you would call a weather API.
            if (city.equals("Gelsenkirchen")) {
                return "It's always sunny in Gelsenkirchen!";
            } else {
                return "It is rainy.";
            }
        }
    }
}
