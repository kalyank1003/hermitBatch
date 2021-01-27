package com.vachan.hermitBatchProcessor;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vachan.service.InferenceService;
import com.vachan.service.RDFFileService;

@SpringBootApplication
public class HermitBatchProcessorApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HermitBatchProcessorApplication.class, args);
		int total_nodes = 101623;
		int batch = 250;
		int start = 0;
		int end = batch;
		int size = total_nodes / batch;
		int last = total_nodes % batch;
		int count = 0;
		RDFFileService rdfFileService = new RDFFileService();
		InferenceService inService = new InferenceService();

		while (end <= total_nodes) {

			processNodes(start, end, count, rdfFileService, inService);
			start = end + 1;
			end = end + batch;
			count++;

		}
		
		 System.out.println(start); System.out.println(end);
		 System.out.println(count); System.out.println(size);
		 System.out.println(last);
		 
		if (count == size) {
			end = start + last;
			processNodes(start, end, count, rdfFileService, inService);
		}
		rdfFileService.combineALL();

	}

	private static void processNodes(int start, int end, int count, RDFFileService rdfFileService,
			InferenceService inService) throws IOException, TransformerException, Exception {
		rdfFileService.mergeToOWL(start, end, count);
		inService.inferData(count);
		rdfFileService.splitFromOWL(count);
	}

}
