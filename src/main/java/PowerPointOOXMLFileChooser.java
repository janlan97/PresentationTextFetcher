import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PowerPointOOXMLFileChooser extends JFileChooser {
	private static final long serialVersionUID = 1L;
	private final String[] OOXML_EXTENSIONS = { "pptx", "pptm", "potx", "potm", "ppam", "ppsx", "ppsm", "sldx",
			"sldm" };
	private static final String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop/";

	public PowerPointOOXMLFileChooser() {
		this.setFileFilter(new FileNameExtensionFilter("Prezentacje", OOXML_EXTENSIONS));
		this.setCurrentDirectory(new File(DESKTOP_PATH));
		this.setMultiSelectionEnabled(false);
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
}