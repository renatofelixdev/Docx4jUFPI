package report.api.docx4j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.OutlineLvl;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.Text;

import report.enums.Alinhamento;
import report.model.ItemImagem;
import report.model.ItemLista;
import report.model.ItemParagrafo;

@SuppressWarnings("restriction")
public final class Docx4jItens {
	private static ObjectFactory factory = Context.getWmlObjectFactory();

	private Docx4jItens(){}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static P paragrafo(ItemParagrafo itemParagrafo){
		P paragrafo = factory.createP();
		R run = factory.createR();
		PPr ppr = factory.createPPr();
		RPr rpr = factory.createRPr();

		//Docx4jEstilos.idioma(rpr, factory);

		if(itemParagrafo.isNegrito()){
			Docx4jEstilos.negrito(rpr);
		}

		if(itemParagrafo.isItalico()){
			Docx4jEstilos.italico(rpr);
		}

		if(itemParagrafo.isSublinhado()){
			Docx4jEstilos.sublinhado(rpr);
		}

		Ind ind = factory.createPPrBaseInd();

		if(itemParagrafo.isRecuoPrimeiraLinha()){
			ind.setFirstLine(BigInteger.valueOf(708));
		}
		
		if(itemParagrafo.getRecuoPersonalizado() == null)
			ind.setLeft(BigInteger.valueOf(itemParagrafo.getRecuo()*280));
		else
			ind.setLeft(BigInteger.valueOf(itemParagrafo.getRecuoPersonalizado()));
		
		Docx4jEstilos.fonte(rpr, itemParagrafo.getFonte());
		Docx4jEstilos.tamanhoFonte(rpr, itemParagrafo.getTamanhoFonte().toString());
		Docx4jEstilos.espacamento(ppr, itemParagrafo.getEspacoAntes(), itemParagrafo.getEspacoDepois(), itemParagrafo.getEspacoLinha());
		if(itemParagrafo.getAlinhamento() != null) {
			if(itemParagrafo.getAlinhamento().equals(Alinhamento.CENTRO)){
				Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.CENTER);
			}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.ESQUERDA)){
				Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.LEFT);
			}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.DIREITA)){
				Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.RIGHT);
			}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.JUSTIFICADO)){
				Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);
			}
		}

		if(itemParagrafo.getReferencia() != null){		
			R r = factory.createR();
			FldChar fldchar = factory.createFldChar();
			fldchar.setFldCharType(STFldCharType.BEGIN);
			fldchar.setDirty(true);
			r.getContent().add(new JAXBElement( new QName(Namespaces.NS_WORD12, "fldChar"), FldChar.class, fldchar));
			paragrafo.getContent().add(r);

			R r1 = factory.createR();
			Text txt = new Text();
			txt.setSpace("preserve");
			txt.setValue("REF "+((ItemLista) itemParagrafo.getReferencia()).getMarcacao()+" \\r \\h");
			r1.getContent().add(factory.createRInstrText(txt) );
			paragrafo.getContent().add(r1);

			FldChar fldcharend = factory.createFldChar();
			fldcharend.setFldCharType(STFldCharType.END);
			R r2 = factory.createR();
			r2.getContent().add(new JAXBElement( new QName(Namespaces.NS_WORD12, "fldChar"), FldChar.class, fldcharend));
			paragrafo.getContent().add(r2);
		}else{
			Text text = new Text();
			text.setValue(itemParagrafo.getConteudo());
			run.getContent().add(text);
		}
		
		if(itemParagrafo.isEstarNoSumario()){
			OutlineLvl outlineLvl = factory.createPPrBaseOutlineLvl();
			outlineLvl.setVal(BigInteger.valueOf(itemParagrafo.getNivel()));
			ppr.setOutlineLvl(outlineLvl);
		}

		paragrafo.setPPr(ppr);
		run.setRPr(rpr);
		paragrafo.getContent().add(run);

		return paragrafo;
	}

	public static List<Object> paragrafoHtml(XHTMLImporter xHTMLImporter, ItemParagrafo itemParagrafo, TipoFonte fonte){
		try {

			List<Object> paragrafos = xHTMLImporter.convert("<p>"+StringEscapeUtils.unescapeHtml4(itemParagrafo.getConteudo())+"</p>", null);

			for(Object o : paragrafos){
				if(o instanceof P) {
					P p = ((P) o);				
					PPr ppr = p.getPPr();
					R run = factory.createR();
					Ind ind = ppr.getInd();
					if(itemParagrafo.isRecuoPrimeiraLinha()){
						ind.setFirstLine(BigInteger.valueOf(708));
					}
					
					if(itemParagrafo.getRecuoPersonalizado() == null)
						ind.setLeft(BigInteger.valueOf(itemParagrafo.getRecuo()*280));
					else
						ind.setLeft(BigInteger.valueOf(itemParagrafo.getRecuoPersonalizado()));

					if(itemParagrafo.getAlinhamento() != null) {
						if(itemParagrafo.getAlinhamento().equals(Alinhamento.CENTRO)){
							Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.CENTER);
						}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.ESQUERDA)){
							Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.LEFT);
						}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.DIREITA)){
							Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.RIGHT);
						}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.JUSTIFICADO)){
							Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);
						}
					}
					
					if(itemParagrafo.isEstarNoSumario()){
						OutlineLvl outlineLvl = factory.createPPrBaseOutlineLvl();
						outlineLvl.setVal(BigInteger.valueOf(itemParagrafo.getNivel()));
						ppr.setOutlineLvl(outlineLvl);
					}

					for(Object ob : p.getContent()){
						if(ob instanceof R){

							//Docx4jEstilos.idioma(((R) ob).getRPr(), factory);
							Docx4jEstilos.fonte(((R) ob).getRPr(), fonte);
							Docx4jEstilos.tamanhoFonte(((R) ob).getRPr(), itemParagrafo.getTamanhoFonte().toString());
						}
					}
					p.getContent().add(run);
					Docx4jEstilos.espacamento(ppr, itemParagrafo.getEspacoAntes(), itemParagrafo.getEspacoDepois(), itemParagrafo.getEspacoLinha());			
				}
			}
			return paragrafos;
		} catch (Docx4JException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static P imagem(ItemImagem itemImagem, WordprocessingMLPackage wordMLPackage, HeaderPart headerPart){
		P paragrafo;

		BinaryPartAbstractImage imagePart;
		Inline inline = null;
		try {
			if(headerPart != null)
				imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, headerPart, IOUtils.toByteArray(itemImagem.getImagem()));
			else
				imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, IOUtils.toByteArray(itemImagem.getImagem()));
			if(itemImagem.getLargura() != null)
				inline = imagePart.createImageInline(null, null, 0, 1, itemImagem.getLargura().longValue(), false);
			else
				inline = imagePart.createImageInline(null, null, 0, 1, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		paragrafo = factory.createP();
		PPr ppr = factory.createPPr();
		if(itemImagem.getAlinhamento().equals(Alinhamento.CENTRO)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.CENTER);
		}else if(itemImagem.getAlinhamento().equals(Alinhamento.ESQUERDA)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.LEFT);
		}else if(itemImagem.getAlinhamento().equals(Alinhamento.DIREITA)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.RIGHT);
		}else if(itemImagem.getAlinhamento().equals(Alinhamento.JUSTIFICADO)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);
		}
		paragrafo.setPPr(ppr);
		R run = factory.createR();
		paragrafo.getContent().add(run);
		Drawing drawing = factory.createDrawing();
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);

		return paragrafo;		
	}

	public static P legenda(ItemParagrafo itemParagrafo, TipoFonte fonte){
		P p = factory.createP();//Docx4jItens.paragrafo(itemParagrafo);
		

		RPr rpr = factory.createRPr();
		Docx4jEstilos.fonte(rpr, fonte);
		Docx4jEstilos.tamanhoFonte(rpr, itemParagrafo.getTamanhoFonte().toString());

		PPr ppr = factory.createPPr();
		PStyle pStyle = factory.createPPrBasePStyle();
		pStyle.setVal("Legenda");
		p.setPPr(ppr);

		if(itemParagrafo.getAlinhamento().equals(Alinhamento.CENTRO)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.CENTER);
		}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.ESQUERDA)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.LEFT);
		}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.DIREITA)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.RIGHT);
		}else if(itemParagrafo.getAlinhamento().equals(Alinhamento.JUSTIFICADO)){
			Docx4jEstilos.alinhamentoHorizontal(ppr, JcEnumeration.BOTH);
		}
		
		BooleanDefaultTrue b = factory.createBooleanDefaultTrue();
		b.setVal(false);

		/*R run3 = factory.createR();
		RPr rpr3 = factory.createRPr();
		rpr3.setB(b);
		rpr3.setNoProof(new BooleanDefaultTrue());
		run3.getContent().add(rpr);
		run3.getContent().add(rpr3);
		Text t3 = factory.createText();
		String tipo = "Figura";
		if(itemParagrafo.getTipoLegenda().equals(TipoLegenda.FIGURA))
			t3.setValue("Figura ");
		else if(itemParagrafo.getTipoLegenda().equals(TipoLegenda.TABELA)) {
			t3.setValue("Quadro ");
			tipo = "Tabela";
		}
		t3.setSpace("preserve");
		run3.getContent().add(t3);

		CTSimpleField cts1 = factory.createCTSimpleField();
		cts1.setInstr(" STYLEREF 1 \\n ");
		R run1 = factory.createR();
		run1.getContent().add(rpr);
		run1.getContent().add(rpr3);
		Text text1 = factory.createText();
		text1.setValue("");
		run1.getContent().add(text1);

		
		cts1.getContent().add(run1);
		JAXBElement<org.docx4j.wml.CTSimpleField> simplefieldWrapped1 = factory.createPFldSimple(cts1);


		R run2 = factory.createR();
		Text t2 = factory.createText();
		if(itemParagrafo.getNivelLegendaQuadro() > 0)
			t2.setValue(itemParagrafo.getNivelLegendaQuadro()+".");
		else
			t2.setValue(".");
		run2.getContent().add(rpr);
		run2.getContent().add(t2);
		run2.getContent().add(rpr3);

		CTSimpleField cts = factory.createCTSimpleField();
		if(itemParagrafo.isLegendaInicio()) {
			cts.setInstr(" SEQ "+tipo+" \\* ARABIC \\r 1");
		}else {
			cts.setInstr(" SEQ "+tipo+" \\* ARABIC \\n ");
		}
		
		JAXBElement<org.docx4j.wml.CTSimpleField> simplefieldWrapped = factory.createPFldSimple(cts);*/

		R run4 = factory.createR();
		Text t4 = factory.createText();
		t4.setValue(itemParagrafo.getConteudo());
		t4.setSpace("preserve");

		run4.getContent().add(rpr);
		run4.getContent().add(t4);

		//p.getContent().add(run3);
		
		
		/*
		if(itemParagrafo.getNivelLegendaQuadro() > 0)*/
			p.getContent().addAll(Arrays.asList(/*run2, simplefieldWrapped, */run4));/*
		else
			p.getContent().addAll(Arrays.asList(simplefieldWrapped1, run2, simplefieldWrapped, run4));*/
		return p;
	}


}
