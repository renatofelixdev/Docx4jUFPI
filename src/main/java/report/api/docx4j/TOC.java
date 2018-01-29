package report.api.docx4j;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.Text;
 
@SuppressWarnings("restriction")
public class TOC {
 
    @SuppressWarnings("deprecation")
	public static void addTableOfContent(MainDocumentPart documentPart, ObjectFactory objectFactory) {
        P paragraph = objectFactory.createP();
 
        addFieldBegin(paragraph, objectFactory);
        addTableOfContentField(paragraph, objectFactory);
        addFieldEnd(paragraph, objectFactory);
 
        documentPart.getJaxbElement().getBody().getContent().add(paragraph);
    }
 
    public static void addTableOfContentField(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("TOC \\o \"1-3\" \\h \\z \\u");
        run.getContent().add(objectFactory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }
 
    public static void addFieldBegin(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        FldChar fldchar = objectFactory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        fldchar.setDirty(true);
        run.getContent().add(getWrappedFldChar(fldchar));
        paragraph.getContent().add(run);
    }
 
 
    public static void addFieldEnd(P paragraph, ObjectFactory objectFactory) {
        R run = objectFactory.createR();
        FldChar fldcharend = objectFactory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        run.getContent().add(getWrappedFldChar(fldcharend));
        paragraph.getContent().add(run);
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static JAXBElement getWrappedFldChar(FldChar fldchar) {
        return new JAXBElement(new QName(Namespaces.NS_WORD12, "fldChar"),
                FldChar.class, fldchar);
    }
}