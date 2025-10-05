package com.spring.ollama.chatbot_rag_ollama.service.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.spring.ollama.chatbot_rag_ollama.service.DataLoader;

@Service
public class DataLoaderImpl implements DataLoader{
	
	@Value("classpath:sample_data.json")
	private Resource jsonResource;
	
	@Value("classpath:cricket_rules.pdf")
	private Resource pdfResource;

	@Override
	public List<Document> loadDocumentsFromJSON() {
		
		System.out.println("Started loading JSON");
		
		// reading entire JSON file as a single document
		//List<Document> docList = new JsonReader(jsonResource).read();
		
		// reading data based on the key "project" in the JSON file
		List<Document> docList = new JsonReader(jsonResource, "project").read();
		
		return docList;
	}

	@Override
	public List<Document> loadDocumentsFromPDF() {
		
		System.out.println("Started loading PDF");
		
		// reading entire PDF as a single document with default configuration
		//List<Document> docList = new PagePdfDocumentReader(pdfResource).read();
		
		// reading PDF with custom configuration using PdfDocumentReaderConfig
		List<Document> docList = new PagePdfDocumentReader(pdfResource,
				PdfDocumentReaderConfig.builder()
				.withPageTopMargin(0)
				.withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
						.withNumberOfTopTextLinesToDelete(0)
						.build())
				.withPagesPerDocument(1)
				.build()
				).read();
		
		return docList;
	}

}
