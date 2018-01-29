package report.api.docx4j;


import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringEscapeUtils;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.toc.Toc;
import org.docx4j.toc.TocException;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.Body;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.CTSimpleField;
import org.docx4j.wml.Document;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.PPrBase.OutlineLvl;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

import report.interfaces.IItemBuilder;
import report.model.ItemColuna;
import report.model.ItemImagem;
import report.model.ItemLinha;
import report.model.ItemLista;
import report.model.ItemParagrafo;
import report.model.ItemQuebraPagina;
import report.model.ItemRelatorio;
import report.model.ItemSecao;
import report.model.ItemSumario;
import report.model.ItemTabela;

@SuppressWarnings("restriction")
public class Docx4jItemBuilder implements IItemBuilder{

	private ObjectFactory factory = Context.getWmlObjectFactory();
	private XHTMLImporterImpl xHTMLImporter = null;
	private WordprocessingMLPackage wordMLPackage = null;
	private HeaderReference referenciaCabecalho = null;
	private Hdr header = null;
	private HeaderPart headerPart = null;
	private ContextoDocx4j contexto;
	private int id = 0;
	
	public void addParagrafo(ItemParagrafo itemParagrafo, Object... args) {
		this.identificaObjetos(args);	
		
		if(itemParagrafo.isHtml()){
			wordMLPackage.getMainDocumentPart().getContent().addAll(Docx4jItens.paragrafoHtml(xHTMLImporter, itemParagrafo, contexto.getFonte()));
		}else{
			wordMLPackage.getMainDocumentPart().getContent().add(Docx4jItens.paragrafo(itemParagrafo));
		}		
	}

	public void addImagem(ItemImagem itemImagem, Object... args) {
		this.identificaObjetos(args);
		
		wordMLPackage.getMainDocumentPart().getContent().add(Docx4jItens.imagem(itemImagem, wordMLPackage, headerPart));		
	}

	public void addSecao(ItemSecao itemSecao, Object... args) {
		this.identificaObjetos(args);
		if(itemSecao.isUltimaSecao()){
			List<SectionWrapper> sections = this.wordMLPackage.getDocumentModel().getSections();			
			SectPr sectionProperties = sections.get(sections.size() - 1).getSectPr();
			String papersize= Docx4jProperties.getProperties().getProperty("docx4j.PageSize", itemSecao.getTamanhoDaPagina().toString());
	        boolean landscape= itemSecao.isPaisagem();
	        
			PageDimensions page = new PageDimensions();
			page.setPgSize(PageSizePaper.valueOf(papersize), landscape);
			
			SectPr.Type type = Context.getWmlObjectFactory().createSectPrType();
	        type.setVal(itemSecao.getTipoSecao().getTipo());
	        sectionProperties.setType(type);
			
	        sectionProperties.setPgSz(  page.getPgSz() );
	        sectionProperties.setPgMar( page.getPgMar() );
	        if(itemSecao.isCabecalho() && referenciaCabecalho != null)
	        	sectionProperties.getEGHdrFtrReferences().add(referenciaCabecalho);
		}else{
			P paragrafo = factory.createP();
			PPr ppr = factory.createPPr();
			
			String papersize= Docx4jProperties.getProperties().getProperty("docx4j.PageSize", itemSecao.getTamanhoDaPagina().toString());
	        boolean landscape= itemSecao.isPaisagem();
	        
	        PageDimensions page = new PageDimensions();
	        page.setPgSize(PageSizePaper.valueOf(papersize), landscape);
	       
	        SectPr sectPr = this.factory.createSectPr();
	        sectPr.setPgSz(  page.getPgSz() );
	        sectPr.setPgMar( page.getPgMar() );
	        SectPr.Type type = Context.getWmlObjectFactory().createSectPrType();
	        type.setVal(itemSecao.getTipoSecao().getTipo());
	        sectPr.setType(type);
	        if(itemSecao.isCabecalho() && referenciaCabecalho != null)
	            sectPr.getEGHdrFtrReferences().add(referenciaCabecalho);
	        ppr.setSectPr(sectPr);
	        paragrafo.setPPr(ppr);
	                
	        wordMLPackage.getMainDocumentPart().getContent().add(paragrafo);
		}
	}

	public void addLista(ItemLista itemLista, Object... args) {
		this.identificaObjetos(args);
		if(itemLista.isHtml()){
			try {
				RFonts arialRFonts = Context.getWmlObjectFactory().createRFonts();
			    arialRFonts.setAscii(contexto.getFonte().toString());
			    arialRFonts.setHAnsi(contexto.getFonte().toString());
			    XHTMLImporterImpl.addFontMapping(contexto.getFonte().toString(), arialRFonts);
				xHTMLImporter  = new XHTMLImporterImpl(wordMLPackage);
				System.out.println(itemLista.getConteudo());
				List<Object> paragrafos = xHTMLImporter.convert("<p>"+StringEscapeUtils.unescapeHtml4(itemLista.getConteudo())+"</p>", null);
				boolean primeiroParagrafo = true;
				for(Object o : paragrafos){
					P p = ((P) o);
					PPr ppr = p.getPPr();
					ppr = this.factory.createPPr();
					R run = factory.createR();
					
					for(Object ob : p.getContent()){
						if(ob instanceof R){
							if(itemLista.getNivel() < 2)
								Docx4jEstilos.negrito(((R) ob).getRPr());
							//Docx4jEstilos.idioma(((R) ob).getRPr(), factory);
							Docx4jEstilos.fonte(((R) ob).getRPr(), contexto.getFonte());
							Docx4jEstilos.tamanhoFonte(((R) ob).getRPr(), "24");
						}
					}
					
					Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);
					Docx4jEstilos.espacamento(ppr, itemLista.getEspacoAntes(), itemLista.getEspacoDepois(), itemLista.getEspacoLinha());
					if(primeiroParagrafo){
						/*Ilvl ilvl = factory.createPPrBaseNumPrIlvl();
						ilvl.setVal(BigInteger.valueOf(itemLista.getNivel()));
						
						NumId numId = factory.createPPrBaseNumPrNumId();
						numId.setVal(BigInteger.valueOf(itemLista.getNumbering()));
						
						NumPr numPr = factory.createPPrBaseNumPr();
						numPr.setIlvl(ilvl);
						numPr.setNumId(numId);
						
						ppr.setNumPr(numPr);*/
						
						
						//nivel para sumario
						if(itemLista.isEstarNoSumario()){
							OutlineLvl outlineLvl = factory.createPPrBaseOutlineLvl();
							outlineLvl.setVal(BigInteger.valueOf(itemLista.getNivel()));
							ppr.setOutlineLvl(outlineLvl);
						}
						primeiroParagrafo = false;
						
						if(itemLista.isMarcado()){	
							// Next, bookmark start
							CTBookmark bm = factory.createCTBookmark();
							bm.setId(BigInteger.valueOf(id));
							bm.setName(itemLista.getMarcacao());		
							JAXBElement<CTBookmark> bmStart =  factory.createBodyBookmarkStart(bm);
							p.getContent().add(bmStart);
							
							// Add bookmark end first
							CTMarkupRange mr = factory.createCTMarkupRange();
							mr.setId(BigInteger.valueOf(id));
							JAXBElement<CTMarkupRange> bmEnd = factory.createBodyBookmarkEnd(mr);
							p.getContent().add(bmEnd); 
							id++;
						}
						
						PPrBase.PStyle pStyle = factory.createPPrBasePStyle();
						if(itemLista.isTitulo()){
							pStyle.setVal("Ttulo1");
							ppr.setPStyle(pStyle);
						}else{
							pStyle.setVal("PargrafodaLista");
							ppr.setPStyle(pStyle);
						}
					}
					
					p.setPPr(ppr);
					p.getContent().add(run);
					
				}
				wordMLPackage.getMainDocumentPart().getContent().addAll(paragrafos);
			} catch (Docx4JException e) {
				e.printStackTrace();
			}
			
		}else{
			P paragrafo;
			
			//this.identificaObjetos(args);
			
			paragrafo = factory.createP();
			R run = factory.createR();
			PPr ppr = factory.createPPr();
			RPr rpr = factory.createRPr();

			//Docx4jEstilos.idioma(rpr, factory);
			if(itemLista.getNivel() < 2)
				Docx4jEstilos.negrito(rpr);
			Docx4jEstilos.tamanhoFonte(rpr, "24");
			Docx4jEstilos.fonte(rpr, contexto.getFonte());
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);

			Docx4jEstilos.espacamento(ppr, itemLista.getEspacoAntes(), itemLista.getEspacoDepois(), itemLista.getEspacoLinha());		
			run.setRPr(rpr);
			
			Text text = new Text();
			text.setValue(itemLista.getConteudo());
			
			/*Ilvl ilvl = factory.createPPrBaseNumPrIlvl();
			ilvl.setVal(BigInteger.valueOf(itemLista.getNivel()));
			
			NumId numId = factory.createPPrBaseNumPrNumId();
			numId.setVal(BigInteger.valueOf(itemLista.getNumbering()));
			
			NumPr numPr = factory.createPPrBaseNumPr();
			numPr.setIlvl(ilvl);
			numPr.setNumId(numId);
			
			ppr.setNumPr(numPr);*/
			
			//nivel para sumario
			if(itemLista.isEstarNoSumario()){
				OutlineLvl outlineLvl = factory.createPPrBaseOutlineLvl();
				outlineLvl.setVal(BigInteger.valueOf(itemLista.getNivel()));
				ppr.setOutlineLvl(outlineLvl);
			}
			
			
			
			paragrafo.setPPr(ppr);
			
			if(itemLista.isMarcado()){			
				
				// Next, bookmark start
				CTBookmark bm = factory.createCTBookmark();
				bm.setId(BigInteger.valueOf(id));
				bm.setName(itemLista.getMarcacao());		
				JAXBElement<CTBookmark> bmStart =  factory.createBodyBookmarkStart(bm);
				paragrafo.getContent().add(bmStart);
				
				// Add bookmark end first
				CTMarkupRange mr = factory.createCTMarkupRange();
				mr.setId(BigInteger.valueOf(id));
				JAXBElement<CTMarkupRange> bmEnd = factory.createBodyBookmarkEnd(mr);
				paragrafo.getContent().add(bmEnd); 
				id++;
			}
			
			PPrBase.PStyle pStyle = new PPrBase.PStyle();
			if(itemLista.isTitulo()){
				pStyle.setVal("Ttulo1");
				ppr.setPStyle(pStyle);
			}else{
				pStyle.setVal("PargrafodaLista");
				ppr.setPStyle(pStyle);
			}
		
			run.getContent().add(text);
			paragrafo.getContent().add(run);
	
			wordMLPackage.getMainDocumentPart().getContent().add(paragrafo);
		}
	}
	
	public void addQuebraPagina(ItemQuebraPagina itemQuebraPagina, Object... args){
		P paragrafo;
		
		this.identificaObjetos(args);
		
		Br br = new Br();
		br.setType(STBrType.PAGE);
		paragrafo = factory.createP();
		paragrafo.getContent().add(br);
		wordMLPackage.getMainDocumentPart().getContent().add(paragrafo);
	}
	
	@SuppressWarnings("deprecation")
	public void addTabela(ItemTabela itemTabela, Object... args){
		this.identificaObjetos(args);
		Tbl table = this.factory.createTbl();
		//TODO REFATORAR
		TblPr tablePpr = this.factory.createTblPr();
		TblWidth tw = this.factory.createTblWidth();
		tw.setW(BigInteger.valueOf(5000));
		tw.setType("pct");
		tablePpr.setTblW(tw);
		
		table.setTblPr(tablePpr);
		int i = 0;
		for(ItemLinha il : itemTabela.getLinhas()){
			Tr tr = this.factory.createTr();
			for(ItemColuna ic : il.getColunas()){
				Tc tc = this.factory.createTc();
				Docx4jEstilos.corDeFundoTabela(tc, ic.getCorDeFundo());
				for(ItemRelatorio ir : ic.getItensRelatorio()){
					//TODO REFATORAR
					if(ir instanceof ItemParagrafo){
						if(((ItemParagrafo) ir).isLegenda())
							tc.getContent().add(Docx4jItens.legenda((ItemParagrafo) ir, contexto.getFonte()));
						else if(((ItemParagrafo) ir).isHtml()){
							xHTMLImporter  = new XHTMLImporterImpl(wordMLPackage);
							tc.getContent().addAll(Docx4jItens.paragrafoHtml(xHTMLImporter, (ItemParagrafo) ir, contexto.getFonte()));
						}else{
							if(ir.getConteudo() != null && ir.getConteudo().equals("#serca_numeracao_pagina#")){
								CTSimpleField pgnum = factory.createCTSimpleField();
								pgnum.setInstr(" PAGE \\* MERGEFORMAT ");
								RPr RPr = factory.createRPr();
								RPr.setNoProof(new BooleanDefaultTrue());
								PPr ppr = factory.createPPr();
								Jc jc = factory.createJc();
								jc.setVal(JcEnumeration.CENTER);
								ppr.setJc(jc);
								PPrBase.Spacing pprbase = factory.createPPrBaseSpacing();
								//pprbase.setBefore(BigInteger.valueOf(240));
								pprbase.setAfter(BigInteger.valueOf(0));
								ppr.setSpacing(pprbase);
								
								Docx4jEstilos.fonte(RPr, contexto.getFonte());
								Docx4jEstilos.tamanhoFonte(RPr, ((ItemParagrafo) ir).getTamanhoFonte().toString());

								R run3 = factory.createR();
								run3.getContent().add(RPr);								
								pgnum.getContent().add(RPr);
								pgnum.getContent().add(run3);
								
								R run = factory.createR();
								Text text = new Text();
								text.setValue("Fl.");
								run.getContent().add(RPr);
								run.getContent().add(text);

								JAXBElement<CTSimpleField> fldSimple = factory.createPFldSimple(pgnum);
								P para = factory.createP();
								para.getContent().add(RPr);
								para.getContent().add(run);
								para.getContent().add(fldSimple);
								para.setPPr(ppr);
								tc.getEGBlockLevelElts().add(para);
								
							}else
								tc.getContent().add(Docx4jItens.paragrafo((ItemParagrafo) ir));
						}
					}else
						tc.getContent().add(Docx4jItens.imagem((ItemImagem) ir, wordMLPackage, headerPart));
				}
				
				if(ic.getMesclarHorizontal() > 0){
					Docx4jEstilos.mesclarHorizontal(tc, ic.getMesclarHorizontal());
				}
				
				if(ic.getMesclarVertical() != null){
					Docx4jEstilos.mesclarVertical(tc, ic.getMesclarVertical());
				}
				Docx4jEstilos.bordas(tc, ic.isBorderLeft(),  ic.isBorderTop(), ic.isBorderRight(), ic.isBorderBottom());
				Docx4jEstilos.alinhamentoVerticalTabela(tc, ic.getAlinhamentoVertical());
				tr.getContent().add(tc);				
			}
			if(i < itemTabela.getTamanhoCabecalho()) {
				TrPr trpr = factory.createTrPr();
				trpr.getCnfStyleOrDivIdOrGridBefore().add(factory.createCTTrPrBaseTblHeader(new BooleanDefaultTrue()));
				tr.setTrPr(trpr);
			}i++;
			table.getContent().add(tr);
		}
		
		if(header != null)
			header.getContent().add(table);
		else
			this.wordMLPackage.getMainDocumentPart().getContent().add(table);
	}

	public void addLegenda(ItemParagrafo itemParagrafo, Object... args){		
		this.identificaObjetos(args);		
	}
	
	private void identificaObjetos(Object... args){
		/*for(Object o : args){
			if(o instanceof XHTMLImporterImpl){
				xHTMLImporter = (XHTMLImporterImpl) o;
			}else if(o instanceof WordprocessingMLPackage){
				wordMLPackage = (WordprocessingMLPackage) o;
			}else if(o instanceof HeaderReference){
				referenciaCabecalho = (HeaderReference) o;
			}else if(o instanceof Hdr){
				header = (Hdr) o;
			}else if(o instanceof HeaderPart){
				headerPart = (HeaderPart) o;
			}
		}*/
		this.contexto = (ContextoDocx4j) args[0];
		this.xHTMLImporter = this.contexto.getxHTMLImporter();
		this.wordMLPackage = this.contexto.getWordMLPackage();
		this.referenciaCabecalho = this.contexto.getReferenciaCabecalho();
		this.header = this.contexto.getHeader();
		this.headerPart = this.contexto.getHeaderPart();
		
	}

	@SuppressWarnings("deprecation")
	public void addSumario(ItemSumario itemSumario, Object[] args) {
		this.identificaObjetos(args);
		TocGenerator tocGenerator;
		try {

			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
	        Document wmlDocumentEl = (Document)documentPart.getJaxbElement();
	        Body body =  wmlDocumentEl.getBody();	
	        tocGenerator = new TocGenerator(wordMLPackage);
			Toc.setTocHeadingText("Sumário");
			tocGenerator.generateToc(body.getContent().size(), "TOC \\o \"1-3\" \\h \\z \\u", false);
			
			wordMLPackage.getMainDocumentPart().getDocumentSettingsPart()
								.getJaxbElement().setUpdateFields(new BooleanDefaultTrue());
		} catch (TocException e) {
			e.printStackTrace();
		} 
	}
	

}
