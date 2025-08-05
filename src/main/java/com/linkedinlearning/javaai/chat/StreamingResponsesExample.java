package com.linkedinlearning.javaai.chat;

import dev.langchain4j.model.LambdaStreamingResponseHandler;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.concurrent.CountDownLatch;

public class StreamingResponsesExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) throws InterruptedException {
        OpenAiStreamingChatModel model = OpenAiStreamingChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        model.chat("Write a short story about a whale that carries containers on its back.", new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String s) {
                System.out.println("Partial response: " + s);
            }

            @Override
            public void onCompleteResponse(ChatResponse chatResponse) {
                System.out.println("Complete response: " + chatResponse);
                countDownLatch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LambdaStreamingResponseHandler.onPartialResponseBlocking(model, "Write a short story about an octopus juggling containers", System.out::print);

    }

}
