import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ProgramUtils {
	private static final String TEXT_TAG = "a:t";
	private static final String TEXT_EXTENSION = ".txt";
	private static final String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop/";

	public static void generateTextFileFromPowerPointPresentation(final String presentationPath,
			final String generatedFileName) throws ParserConfigurationException {
		List<String> fetchedTextLines = new ArrayList<String>();
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		getSlides(createXMLSlideShowInstance(presentationPath), slideShow -> {
			List<XSLFSlide> fetchedSlides = slideShow.getSlides();
			try {
				slideShow.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fetchedSlides;
		}).forEach(slide -> {
			try {
				Document doc = documentBuilder
						.parse(new InputSource(new StringReader(slide.getXmlObject().toString())));
				NodeList nodeList = doc.getElementsByTagName(TEXT_TAG);
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element e = (Element) nodeList.item(i);
					fetchedTextLines.add(e.getTextContent());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		try {
			Files.write(Paths.get(DESKTOP_PATH + generatedFileName + TEXT_EXTENSION), fetchedTextLines,
					StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "OK!");
	}

	public static XMLSlideShow createXMLSlideShowInstance(final String path) {
		XMLSlideShow XMLSlideShowInstance = null;
		try {
			XMLSlideShowInstance = new XMLSlideShow(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return XMLSlideShowInstance;
	}

	public static List<XSLFSlide> getSlides(XMLSlideShow slideShow, Function<XMLSlideShow, List<XSLFSlide>> f) {
		return f.apply(slideShow);
	}
}