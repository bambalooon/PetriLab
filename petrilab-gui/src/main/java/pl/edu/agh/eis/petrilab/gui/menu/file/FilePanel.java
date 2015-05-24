package pl.edu.agh.eis.petrilab.gui.menu.file;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.util.FileUtilities;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraphInitializer;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrixWithCoordinates;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Name: FilePanel
 * Description: FilePanel
 * Date: 2015-05-17
 * Created by BamBalooon
 */
public class FilePanel extends JPanel implements ActionListener {
    private static final String NEW_FILE_BUTTON_LABEL = "Nowy plik...";
    private static final String NEW_FILE_BUTTON_ACTION = "NEW_FILE_BUTTON_ACTION";
    private static final String OPEN_FILE_BUTTON_LABEL = "Otwórz plik...";
    private static final String OPEN_FILE_BUTTON_ACTION = "OPEN_FILE_BUTTON_ACTION";
    private static final String SAVE_FILE_BUTTON_LABEL = "Zapisz plik...";
    private static final String SAVE_FILE_BUTTON_ACTION = "SAVE_FILE_BUTTON_ACTION";
    private static final String PETRI_LAB_FILE_EXTENSION = "plj";
    private static final String PETRI_LAB_FILE_DESCRIPTION = "PetriLab file";
    private final JFileChooser fileChooser;

    public FilePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new PetriLabFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        JButton newFileButton = new JButton(NEW_FILE_BUTTON_LABEL);
        newFileButton.setActionCommand(NEW_FILE_BUTTON_ACTION);
        newFileButton.addActionListener(this);
        
        JButton openFileButton = new JButton(OPEN_FILE_BUTTON_LABEL);
        openFileButton.setActionCommand(OPEN_FILE_BUTTON_ACTION);
        openFileButton.addActionListener(this);

        JButton saveFileButton = new JButton(SAVE_FILE_BUTTON_LABEL);
        saveFileButton.setActionCommand(SAVE_FILE_BUTTON_ACTION);
        saveFileButton.addActionListener(this);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
        containerPanel.add(newFileButton);
        containerPanel.add(openFileButton);
        containerPanel.add(saveFileButton);

        add(containerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fileChooserReturnValue;
        switch (e.getActionCommand()) {
            case NEW_FILE_BUTTON_ACTION:
                PetriLabApplication.getInstance().loadPetriNetGraph(new PetriNetGraph());
                break;
            case OPEN_FILE_BUTTON_ACTION:
                fileChooserReturnValue = fileChooser.showOpenDialog(this);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        PetriNetMatrixWithCoordinates petriNetMatrix = FileUtilities
                                .openFile(file, PetriNetMatrixWithCoordinates.class);
                        PetriNetGraph loadedGraph = PetriNetGraph.fromMatrix(petriNetMatrix);
                        if (petriNetMatrix.getPlacesCoordinates() != null
                                && petriNetMatrix.getTransitionsCoordinates() != null) {
                            PetriLabApplication.getInstance().loadPetriNetGraph(
                                    loadedGraph,
                                    PetriNetGraphInitializer.loadInitializer(
                                            petriNetMatrix.getPlacesCoordinates(),
                                            petriNetMatrix.getTransitionsCoordinates(),
                                            loadedGraph));
                        } else {
                            PetriLabApplication.getInstance().loadPetriNetGraph(loadedGraph);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Błąd w trakcie odczytu pliku.");
                        ex.printStackTrace();
                    }
                }
                break;
            case SAVE_FILE_BUTTON_ACTION:
                fileChooserReturnValue = fileChooser.showSaveDialog(this);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    PetriNetMatrixWithCoordinates petriNetMatrixWithCoordinates = PetriLabApplication.getInstance()
                            .generatePetriNetMatrixWithCoordinates();
                    try {
                        String fileExtension = FileUtilities.getExtension(file);
                        if (!PETRI_LAB_FILE_EXTENSION.equals(fileExtension)) {
                            file = new File(file.getPath() + "." + PETRI_LAB_FILE_EXTENSION);
                        }
                        FileUtilities.saveFile(file, petriNetMatrixWithCoordinates);
                    } catch (Exception ex) {
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
