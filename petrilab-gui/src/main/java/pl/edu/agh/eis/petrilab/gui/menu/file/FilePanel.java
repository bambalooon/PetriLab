package pl.edu.agh.eis.petrilab.gui.menu.file;

import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.util.FileUtilities;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Name: FilePanel
 * Description: FilePanel
 * Date: 2015-05-17
 * Created by BamBalooon
 */
public class FilePanel extends JPanel implements ActionListener {
    private static final String OPEN_FILE_BUTTON_LABEL = "Otwórz plik...";
    private static final String OPEN_FILE_BUTTON_ACTION = "OPEN_FILE_BUTTON_ACTION";
    private static final String SAVE_FILE_BUTTON_LABEL = "Zapisz plik...";
    private static final String SAVE_FILE_BUTTON_ACTION = "SAVE_FILE_BUTTON_ACTION";
    private static final String PETRI_LAB_FILE_EXTENSION = "plj";
    private static final String PETRI_LAB_FILE_DESCRIPTION = "PetriLab file";
    private final JFileChooser fileChooser;

    public FilePanel() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new PetriLabFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        JButton openFileButton = new JButton(OPEN_FILE_BUTTON_LABEL);
        openFileButton.setActionCommand(OPEN_FILE_BUTTON_ACTION);
        openFileButton.addActionListener(this);

        JButton saveFileButton = new JButton(SAVE_FILE_BUTTON_LABEL);
        saveFileButton.setActionCommand(SAVE_FILE_BUTTON_ACTION);
        saveFileButton.addActionListener(this);

        add(openFileButton);
        add(saveFileButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fileChooserReturnValue;
        switch (e.getActionCommand()) {
            case OPEN_FILE_BUTTON_ACTION:
                fileChooserReturnValue = fileChooser.showOpenDialog(this);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        PetriNetGraph loadedGraph = PetriNetGraph
                                .fromMatrix(FileUtilities.openFile(file, PetriNetMatrix.class));
                        PetriLabApplication.getInstance().updatePetriNetGraph(loadedGraph);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Błąd w trakcie odczytu pliku.");
                        ex.printStackTrace();
                    }
                }
                break;
            case SAVE_FILE_BUTTON_ACTION:
                fileChooserReturnValue = fileChooser.showSaveDialog(this);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    PetriNetMatrix petriNetMatrix = PetriNetMatrix
                            .generateMatrix(PetriLabApplication.getInstance().getPetriNetGraph());
                    try {
                        String fileExtension = FileUtilities.getExtension(file);
                        if (!PETRI_LAB_FILE_EXTENSION.equals(fileExtension)) {
                            file = new File(file.getPath() + "." + PETRI_LAB_FILE_EXTENSION);
                        }
                        FileUtilities.saveFile(file, petriNetMatrix);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Błąd w trakcie zapisu pliku.");
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

    private class PetriLabFileFilter extends FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String fileExtension = FileUtilities.getExtension(file);
            return fileExtension.equals(PETRI_LAB_FILE_EXTENSION);
        }

        @Override
        public String getDescription() {
            return PETRI_LAB_FILE_DESCRIPTION;
        }
    }
}
