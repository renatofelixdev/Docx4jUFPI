package report.api.docx4j;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;

public class ContextoDocx4j {

	private ObjectFactory factory = Context.getWmlObjectFactory();
	private XHTMLImporterImpl xHTMLImporter = null;
	private WordprocessingMLPackage wordMLPackage = null;
	private HeaderReference referenciaCabecalho = null;
	private Hdr header = null;
	private HeaderPart headerPart = null;
	private TipoFonte fonte;
	
	public ObjectFactory getFactory() {
		return factory;
	}
	public void setFactory(ObjectFactory factory) {
		this.factory = factory;
	}
	public XHTMLImporterImpl getxHTMLImporter() {
		return xHTMLImporter;
	}
	public void setxHTMLImporter(XHTMLImporterImpl xHTMLImporter) {
		this.xHTMLImporter = xHTMLImporter;
	}
	public WordprocessingMLPackage getWordMLPackage() {
		return wordMLPackage;
	}
	public void setWordMLPackage(WordprocessingMLPackage wordMLPackage) {
		this.wordMLPackage = wordMLPackage;
	}
	public HeaderReference getReferenciaCabecalho() {
		return referenciaCabecalho;
	}
	public void setReferenciaCabecalho(HeaderReference referenciaCabecalho) {
		this.referenciaCabecalho = referenciaCabecalho;
	}
	public Hdr getHeader() {
		return header;
	}
	public void setHeader(Hdr header) {
		this.header = header;
	}
	public HeaderPart getHeaderPart() {
		return headerPart;
	}
	public void setHeaderPart(HeaderPart headerPart) {
		this.headerPart = headerPart;
	}
	public TipoFonte getFonte() {
		return fonte;
	}
	public void setFonte(TipoFonte fonte) {
		this.fonte = fonte;
	}	
}
