package com.example.ollama.spring_rag_llama;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DocumentIngestionService implements CommandLineRunner {
    private static  final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);
    @Value("classpath:/docs/article_thebeatoct2024.pdf")
    private Resource marketPDF;
    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @Override
    public void run(String... args) throws Exception {
        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));


        log.info("VectorStore Loaded with data!");
    }
}
