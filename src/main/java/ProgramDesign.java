import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ProgramDesign {
	private JFrame frmGenerujTekstZ;
	private JTextField pathToPresentation;
	private JButton btnNewButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgramDesign window = new ProgramDesign();
					window.frmGenerujTekstZ.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProgramDesign() {
		initialize();
		frmGenerujTekstZ.setLocationRelativeTo(null);
	}

	private void initialize() {
		final JFileChooser fileChooser = new PowerPointOOXMLFileChooser();

		frmGenerujTekstZ = new JFrame();
		frmGenerujTekstZ.setTitle("Generuj tekst z prezentacji");
		frmGenerujTekstZ.setBounds(100, 100, 450, 166);
		frmGenerujTekstZ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGenerujTekstZ.getContentPane().setLayout(null);

		pathToPresentation = new JTextField();
		pathToPresentation.setEditable(false);
		pathToPresentation.setBounds(184, 12, 240, 20);
		frmGenerujTekstZ.getContentPane().add(pathToPresentation);
		pathToPresentation.setColumns(10);

		JButton btnWybierzPrezentacj = new JButton("Wybierz prezentację");
		btnWybierzPrezentacj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(frmGenerujTekstZ);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					pathToPresentation.setText(file.getAbsolutePath());
				}
			}
		});
		btnWybierzPrezentacj.setBounds(10, 11, 164, 23);
		frmGenerujTekstZ.getContentPane().add(btnWybierzPrezentacj);

		btnNewButton = new JButton("Wygeneruj tekst");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ProgramUtils.generateTextFileFromPowerPointPresentation(pathToPresentation.getText(),
							"TEKST Z PREZENTACJI");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Cos poszlo nie tak");
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(135, 81, 134, 23);
		frmGenerujTekstZ.getContentPane().add(btnNewButton);
	}
}