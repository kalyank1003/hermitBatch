package com.vachan.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RDFFileService {

	private static Logger log = LoggerFactory.getLogger(RDFFileService.class);

	private static String path = getPath();

	private static String template = path + "/data/template/base_model.xml";

	private static String owl = path + "/data/owl/hp_onto.xml";

	private static String xsl_path = "/data/xsl/";

	private static String merge_path = "/data/xsl/mergeResult/";

	private static String split_path = "/data/xsl/splitResult/";

	private static String output_path = "/data/output/";

	private static String getPath() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}

	public void mergeToOWL(int start, int end, int count) throws IOException, TransformerException {
		String xpth = path + xsl_path + "rdf_merge.xsl";
		String resultPath = path + merge_path + "hermit_input_" + count + ".owl";
		File owlFile = new File(owl);
		File xslFile = new File(xpth);
		File resultFile = new File(resultPath);

		Source xmlSource = new StreamSource(owlFile);
		Source xsltSource = new StreamSource(xslFile);
		Result xmlResult = new StreamResult(resultFile);
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);
		trans.setParameter("start", start);
		trans.setParameter("end", end);

		trans.transform(xmlSource, xmlResult);
		log.info("Created file for inference "+resultPath);
	}

	public void splitFromOWL(int count) throws IOException, TransformerException {
		String xpth = path + xsl_path + "rdf_split.xsl";
		String outPath = path + split_path + "hermit_result_" + count + ".xml";

		String infrd_file = "infResult/hermit_output_" + count + ".xml";

		File infFile = new File(template);
		File xslFile = new File(xpth);
		File resultFile = new File(outPath);

		Source xmlSource = new StreamSource(infFile);
		Source xsltSource = new StreamSource(xslFile);
		Result xmlResult = new StreamResult(resultFile);
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);
		trans.setParameter("inf", infrd_file);

		trans.transform(xmlSource, xmlResult);
		log.info("Generate inferred file"+infrd_file);
	}

	public void combineALL() throws IOException, TransformerException {
		String xpth = path + xsl_path + "rdf_combine.xsl";
		String outPath = path + output_path + "hermit_final_result.xml";

		File infFile = new File(template);
		File xslFile = new File(xpth);
		File resultFile = new File(outPath);

		Source xmlSource = new StreamSource(infFile);
		Source xsltSource = new StreamSource(xslFile);
		Result xmlResult = new StreamResult(resultFile);
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, xmlResult);
		
		log.info("Complete full inference");
	}

}
