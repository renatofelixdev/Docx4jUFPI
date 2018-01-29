package report.api.docx4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.DocumentSettingsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTLanguage;
import org.docx4j.wml.CTSettings;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.Numbering;
import org.docx4j.wml.Styles;

import report.interfaces.IRelatorioBuilder;
import report.model.IRelatorio;
import report.model.ItemRelatorio;
import report.model.ItemSecao;

public class Docx4jReportBuilder implements IRelatorioBuilder {

	/*private ObjectFactory factory;
	private XHTMLImporterImpl xHTMLImporter;
	private WordprocessingMLPackage wordMLPackage;
	private HeaderReference referenciaCabecalho;*/
	private ContextoDocx4j contexto;


	public InputStream construirRelatorio(IRelatorio relatorio) {
		contexto = new ContextoDocx4j();
		contexto.setFonte(relatorio.getFonte());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			this.contexto.setWordMLPackage(WordprocessingMLPackage.createPackage());
			this.contexto.setFactory(Context.getWmlObjectFactory()); 
			this.contexto.setxHTMLImporter(new XHTMLImporterImpl(this.contexto.getWordMLPackage()));

			//ADD ESTILO PARA UMA LISTA NO DOCUMENTO
			NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
			InputStream numbering = this.getClass().getClassLoader().getResourceAsStream("docx4j-numbering.xml");
			ndp.setContents((Numbering)XmlUtils.unmarshal(numbering));			
			this.contexto.getWordMLPackage().getMainDocumentPart().addTargetPart(ndp);

			StyleDefinitionsPart sdp = new StyleDefinitionsPart();
			InputStream styles = this.getClass().getClassLoader().getResourceAsStream("docx4j-styles.xml");
			sdp.setContents((Styles)XmlUtils.unmarshal(styles));
			this.contexto.getWordMLPackage().getMainDocumentPart().addTargetPart(sdp);

			DocumentSettingsPart dsp = new DocumentSettingsPart();
			dsp.setJaxbElement(createSettings());
			this.contexto.getWordMLPackage().getMainDocumentPart().addTargetPart(dsp);

			if(relatorio.getCabecalho() != null){
				this.cabecalho(relatorio.getCabecalho().getItens());
			}


			//ADD ITENS DO RELATORIO CONFORME TIPO DE ITEM
			addItens(relatorio.getItensRelatorio(), 0, null, null);	

			// Save it
			this.contexto.getWordMLPackage().save(baos);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(baos.toByteArray());	
	}

	public CTSettings createSettings() {

		org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
		CTSettings settings = wmlObjectFactory.createCTSettings();
		CTLanguage language = wmlObjectFactory.createCTLanguage();
		settings.setThemeFontLang(language);
		language.setVal( "pt-BR");
		language.setEastAsia( "ko-KR");

		return settings;
	} 

	private void cabecalho(List<ItemRelatorio> itens) throws Exception{
		HeaderPart headerPart = new HeaderPart();
		headerPart.setPackage(this.contexto.getWordMLPackage());
		Hdr header = this.contexto.getFactory().createHdr();
		//add itens
		this.addItens(itens, 1, header, headerPart);

		headerPart.setJaxbElement(header);
		Relationship relationship = this.contexto.getWordMLPackage().getMainDocumentPart()
				.addTargetPart(headerPart);

		this.contexto.setReferenciaCabecalho(this.contexto.getFactory().createHeaderReference());
		this.contexto.getReferenciaCabecalho().setId(relationship.getId());
		this.contexto.getReferenciaCabecalho().setType(HdrFtrRef.DEFAULT);
	}

	//nivel para identificar as seções, as seções devem ser inseridas após os conteúdos
	private void addItens(List<ItemRelatorio> itens, int nivel, Hdr header, HeaderPart headerPart) throws Exception{
		for(ItemRelatorio ie : itens){
			if(ie != null) {
				//if(nivel > 0)
				if(!(ie instanceof ItemSecao))
					addItem(ie, header, headerPart);			
				if(ie.getItensRelatorio() != null && !ie.getItensRelatorio().isEmpty())
					addItens(ie.getItensRelatorio(), nivel+1, header, headerPart);
				//if(nivel == 0)
				if(ie instanceof ItemSecao)
					addItem(ie, header, headerPart);
			}
		}
	}

	private void addItem(ItemRelatorio ie, Hdr header, HeaderPart headerPart) throws Exception{
		this.contexto.setxHTMLImporter(new XHTMLImporterImpl(this.contexto.getWordMLPackage()));
		if(ie != null)
			ie.construir(this.contexto);
	}	
}
