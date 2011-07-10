package controllers;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import play.Logger;
import play.mvc.Controller;

import java.io.InputStream;
import java.net.URL;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void getpdftext(String url) throws Exception {

        InputStream stream = null;
        PDDocument document = null;

        try {
            URL urlObj = new URL(url);

            stream = urlObj.openStream();

            PDFParser parser = new PDFParser(stream);
            parser.parse();

            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();

            response.contentType = "text/plain";
            renderText(stripper.getText(document));

        } catch(Exception e){
            Logger.error("Could not fetch " + url, e);
            error(e);
        } finally {
            if(document != null)
                document.close();
            if(stream != null)
                stream.close();
        }
    }
}