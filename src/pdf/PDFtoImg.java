package pdf;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class PDFtoImg {
	private static final String  DICTIONARY_PATH ="tessdata/";
    public static String extractFromPDF(BufferedImage img) {
		ITesseract instance = new Tesseract();
		try {
		    instance.setLanguage("jpn");
		    instance.setDatapath(DICTIONARY_PATH);
		    String result = instance.doOCR(img);
		    return result;
		} catch (TesseractException ex) {
		    ex.printStackTrace();
		    return "";
		}
    }
}
