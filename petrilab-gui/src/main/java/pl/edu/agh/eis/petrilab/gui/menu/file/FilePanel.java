package pl.edu.agh.eis.petrilab.gui.menu.file;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.util.FileUtilities;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraphInitializer;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrixWithCoordinates;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
    private static final String NEW_FILE_ICON_RES = "/org/freedesktop/tango/16x16/actions/document-new.png";
    private static final String NEW_FILE_BUTTON_ACTION = "NEW_FILE_BUTTON_ACTION";
    private static final String OPEN_FILE_ICON_RES = "/org/freedesktop/tango/16x16/actions/document-open.png";
    private static final String OPEN_FILE_BUTTON_ACTION = "OPEN_FILE_BUTTON_ACTION";
    private static final String SAVE_FILE_ICON_RES = "/org/freedesktop/tango/16x16/actions/document-save.png";
    private static final String SAVE_FILE_BUTTON_ACTION = "SAVE_FILE_BUTTON_ACTION";
    private static final String SAVE_AS_FILE_ICON_RES = "/org/freedesktop/tango/16x16/actions/document-save-as.png";
    private static final String SAVE_AS_FILE_BUTTON_ACTION = "SAVE_AS_FILE_BUTTON_ACTION";
    private static final String PETRI_LAB_FILE_EXTENSION = "plj";
    private static final String PETRI_LAB_FILE_DESCRIPTION = "PetriLab file";
    private final JFileChooser fileChooser;

    public FilePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new PetriLabFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        JButton newFileButton = createButton(NEW_FILE_BUTTON_ACTION, NEW_FILE_ICON_RES);
        JButton openFileButton = createButton(OPEN_FILE_BUTTON_ACTION, OPEN_FILE_ICON_RES);
        JButton saveFileButton = createButton(SAVE_FILE_BUTTON_ACTION, SAVE_FILE_ICON_RES);
        JButton saveAsFileButton = createButton(SAVE_AS_FILE_BUTTON_ACTION, SAVE_AS_FILE_ICON_RES);

        add(newFileButton);
        add(openFileButton);
        add(saveFileButton);
        add(saveAsFileButton);
        setMaximumSize(getPreferredSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fileChooserReturnValue;
        switch (e.getActionCommand()) {
            case NEW_FILE_BUTTON_ACTION:
                PetriLabApplication.getInstance().getModeManager().setNormalMode();
                PetriLabApplication.getInstance().loadPetriNetGraph(new PetriNetGraph());
                break;
            case OPEN_FILE_BUTTON_ACTION:
                fileChooserReturnValue = fileChooser.showOpenDialog(this);
                if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
                    PetriLabApplication.getInstance().getModeManager().setNormalMode();
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

    private JButton createButton(String action, String resource) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(resource)));
        button.setActionCommand(action);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        return button;
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
