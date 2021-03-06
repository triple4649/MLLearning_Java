package pdf;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFMaker {
	public static void main(String args[])throws Exception{
		//読み込むPDFファイル
		PDDocument document = PDDocument.load(new File(args[0]));
		//PDFページを処理する
		Stream<PDPage>stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				document.getDocumentCatalog().getPages().iterator(),
						Spliterator.ORDERED),false);
		
		System.out.println("start");
		Files.write(Paths.get(args[1]), 
				stream.map(s->exePDFpage(s)).collect(Collectors.toList()), 
				Charset.forName("MS932"),
				StandardOpenOption.CREATE);
		System.out.println("end");
	}
	
	//PDFのPageを処理する
	public static String exePDFpage(PDPage p){
		Stream<COSName>stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				p.getResources().getXObjectNames().iterator(),Spliterator.ORDERED),false);
		return stream.map(s->exeImage(s,p.getResources()))
		.reduce((s,v)->s+v).get();
	}
	
	//PDFのPageをJpgに変換する
	public static String exeImage(COSName n,PDResources resources){
		try{
			PDXObject xobject = resources.getXObject(n);
			if(xobject instanceof PDImageXObject){
				PDImageXObject image2 = (PDImageXObject) resources.getXObject(n);
				return PDFtoImg.extractFromPDF(image2.getImage());
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}	
	}
}
