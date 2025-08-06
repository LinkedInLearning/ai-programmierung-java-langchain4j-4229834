package com.linkedinlearning.javaai.chat;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class RagExample {

    static final String MODEL_NAME = "ai/llama3.2:3B-Q4_K_M";
    static final String BASE_URL = "http://localhost:12434/engines/v1";

    public static void main(String[] args) {

        Document document = UrlDocumentLoader.load("https://docs.docker.com/ai/model-runner/", new TextDocumentParser());
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();

        String response = assistant.chat("On which URL can you reach Docker Model Runner?");
        System.out.println(response);
    }

    interface Assistant {
        String chat(String userMessage);
    }

}
