<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:neovoc="neo4j://vocabulary#"
	xmlns:neoind="neo4j://individuals#"
	xmlns:cc="http://creativecommons.org/ns#"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:hp="urn:absolute://harbourpolitica/ontology/"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:hps="urn:absolute://harbourpolitica/ontology/StoryUniverse/"
	xmlns:cert="http://www.w3.org/ns/auth/cert#"
	xmlns:wgs84pos="http://www.w3.org/2003/01/geo/wgs84_pos#"
	xmlns:d0="http://www.ontologydesignpatterns.org/ont/d0.owl#"
	xmlns:dct="http://purl.org/dc/terms/"
	xmlns:xml="http://www.w3.org/XML/1998/namespace"
	xmlns:dul="http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#"
	xmlns:vann="http://purl.org/vocab/vann/"
	xmlns:wikidata="http://www.wikidata.org/entity/"
	xmlns:cidoccrm="http://purl.org/NET/cidoc-crm/core#"
	xmlns:prov="http://www.w3.org/ns/prov#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
    version="3.0">
	
	<xsl:output indent="yes" method="xml"/>
	
	<xsl:param name="start" />
	<xsl:param name="end" />
	
	
	
	<xsl:variable name="fileName" select="'../rdf/rdf_input.xml'"/>
	
	<xsl:variable name="inputDoc" select="document($fileName)"/>
	
	<xsl:variable name="nodes" select="$inputDoc/rdf:RDF/rdf:Description[position() &gt;=$start and position() &lt;= $end]" />
	
	


	<xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
	
	<xsl:template match="rdf:RDF">
		
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
			
			<xsl:copy-of select="$nodes" copy-namespaces="no"/>
		</xsl:copy>	
		
    </xsl:template>
	
</xsl:stylesheet>