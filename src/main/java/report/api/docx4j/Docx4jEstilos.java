package report.api.docx4j;

import java.math.BigInteger;

import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.CTVerticalJc;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.STLineSpacingRule;
import org.docx4j.wml.STVerticalJc;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.TcPrInner.TcBorders;
import org.docx4j.wml.TcPrInner.VMerge;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;

import report.enums.Alinhamento;
import report.enums.TipoMesclagemVertical;

public final class Docx4jEstilos {
	
	private Docx4jEstilos(){}
	
	/*public static void idioma(RPr rpr, ObjectFactory factory){
		CTLanguage lang = factory.createCTLanguage();
		lang.setVal("pt-BR");
		rpr.setLang(lang);
	}*/
	
	public static void negrito(RPr rpr){
		BooleanDefaultTrue b = new BooleanDefaultTrue();
		b.setVal(true);
		rpr.setB(b);
	}
	
	public static void italico(RPr rpr){
		BooleanDefaultTrue b = new BooleanDefaultTrue();
		b.setVal(true);
		rpr.setI(b);
	}
	
	public static void sublinhado(RPr rpr){
		U val = new U();
		val.setVal(UnderlineEnumeration.SINGLE);
		rpr.setU(val);
	}
	
	public static void alinhamentoHorizontal(PPr ppr, JcEnumeration alinhamento){
		if (alinhamento != null) {
			Jc jc = new Jc();
			jc.setVal(alinhamento);
			ppr.setJc(jc);
		}
	}
	
	public static void fonte(RPr rpr, TipoFonte fonte){
		RFonts rf = rpr.getRFonts();
        if (rf == null) {
           rf = new RFonts();
        }
        rf.setAscii(fonte.toString());
        rf.setHAnsi(fonte.toString());
        rf.setCs(fonte.toString());
        rpr.setRFonts(rf);
	}
	
	public static void tamanhoFonte(RPr rpr, String fontSize){
		if (fontSize != null && !fontSize.isEmpty()) {
			HpsMeasure size = new HpsMeasure();
			size.setVal(new BigInteger(fontSize));
			rpr.setSz(size);
			rpr.setSzCs(size);
		}
	}
	
	public static void espacamento(PPr ppr, double d, double e, double espacoLinha){
		Spacing spacing = new Spacing();
		spacing.setAfter(BigInteger.valueOf((long) (240*e)));
		spacing.setBefore(BigInteger.valueOf((long) (240*d)));
		spacing.setLine(BigInteger.valueOf((long) (240*espacoLinha)));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		ppr.setSpacing(spacing);
	}
	
	
	public static void alinhamentoVerticalTabela(Tc tableCell, Alinhamento alinhamento){
		if (alinhamento != null) {
            TcPr tableCellProperties = tableCell.getTcPr();
            if (tableCellProperties == null) {
                tableCellProperties = new TcPr();
                tableCell.setTcPr(tableCellProperties);
            }
    
            CTVerticalJc valign = new CTVerticalJc();
            if(alinhamento.equals(Alinhamento.CENTRO))
            	valign.setVal(STVerticalJc.CENTER);
            else if(alinhamento.equals(Alinhamento.TOPO))
            	valign.setVal(STVerticalJc.TOP);
            else if(alinhamento.equals(Alinhamento.BASE))
            	valign.setVal(STVerticalJc.BOTTOM);
            
            tableCellProperties.setVAlign(valign);
        }
	}
	
	public static void corDeFundoTabela(Tc tc, String color){
		if (color != null) {
			TcPr tcpr = tc.getTcPr();
			if (tcpr == null) {
				tcpr = new TcPr();
				tc.setTcPr(tcpr);
			}
			CTShd shd = new CTShd();
			shd.setFill(color);
			tcpr.setShd(shd);
		}
	}
	
	public static void mesclarHorizontal(Tc tc, int mesclagem){
		TcPr tcpr = tc.getTcPr();
		if (tcpr == null) {
			tcpr = new TcPr();
			tc.setTcPr(tcpr);
		}

		GridSpan gridSpan = new GridSpan();
		gridSpan.setVal(new BigInteger(String.valueOf(mesclagem)));         

		tcpr.setGridSpan(gridSpan);
		tc.setTcPr(tcpr);
	}
	
	public static void mesclarVertical(Tc tc, TipoMesclagemVertical mesclagem){
		TcPr tcpr = tc.getTcPr();
		if (tcpr == null) {
			tcpr = new TcPr();
			tc.setTcPr(tcpr);
		}
		
		VMerge vMerge = new VMerge();
		vMerge.setVal(mesclagem.toString());
		tcpr.setVMerge(vMerge);
		tc.setTcPr(tcpr);
	}
	
	public static void bordas(Tc tc, boolean left, boolean top, boolean right, boolean bottom){
		TcPr tableCellProperties = tc.getTcPr();
        if (tableCellProperties == null) {
            tableCellProperties = new TcPr();
            tc.setTcPr(tableCellProperties);
        }
        
        CTBorder border = new CTBorder();
        border.setColor("000000");
        border.setSz(new BigInteger("10"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);            
        
        TcBorders borders = new TcBorders();
        if (bottom) {
            borders.setBottom(border);
        } 
        if (top) {
            borders.setTop(border);
        } 
        if (left) {
            borders.setLeft(border);
        }
        if (right) {
            borders.setRight(border);
        }
        tableCellProperties.setTcBorders(borders);
	}

}
