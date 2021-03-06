package pl.edu.agh.eis.petrilab.gui.menu.file;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.util.FileUtilities;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraphInitializer;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrixWithCoordinates;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static pl.edu.agh.eis.petrilab.gui.Configuration.GRAPH_FILE;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.createIconButton;

/**
 * Name: FilePanel
 * Description: FilePanel
 * Date: 2015-05-17
 * Created by BamBalooon
 */
public class FilePanel extends JPanel implements ActionListener {
    private static final String NEW_FILE_ICON_RES = "/icons/16x16/page.png";
    private static final String NEW_FILE_BUTTON_ACTION = "NEW_FILE_BUTTON_ACTION";
    private static final String OPEN_FILE_ICON_RES = "/icons/16x16/folder.png";
    private static final String OPEN_FILE_BUTTON_ACTION = "OPEN_FILE_BUTTON_ACTION";
    private static final String SAVE_FILE_ICON_RES = "/icons/16x16/disk.png";
    private static final String SAVE_FILE_BUTTON_ACTION = "SAVE_FILE_BUTTON_ACTION";
    private static final String SAVE_AS_FILE_ICON_RES = "/icons/16x16/save_as.png";
    private static final String SAVE_AS_FILE_BUTTON_ACTION = "SAVE_AS_FILE_BUTTON_ACTION";
    private static final String PETRI_LAB_FILE_EXTENSION = "plj";
    private static final String PETRI_LAB_FILE_DESCRIPTION = "PetriLab file";
    private final JFileChooser fileChooser;

    public FilePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new PetriLabFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        JButton newFileButton = createIconButton(this, NEW_FILE_BUTTON_ACTION, NEW_FILE_ICON_RES);
        JButton openFileButton = createIconButton(this, OPEN_FILE_BUTTON_ACTION, OPEN_FILE_ICON_RES);
        JButton saveFileButton = createIconButton(this, SAVE_FILE_BUTTON_ACTION, SAVE_FILE_ICON_RES);
        JButton saveAsFileButton = createIconButton(this, SAVE_AS_FILE_BUTTON_ACTION, SAVE_AS_FILE_ICON_RES);

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
                PetriLabApplication.getInstance().loadPetriNetGraph(new PetriNetGraph());
                PetriLabApplication.getInstance().getConfiguration().removeProperty(GRAPH_FILE);
                PetriLabApplication.getInstance().getModeManager().setNormalMode();
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
                        PetriLabApplication.getInstance().getConfiguration().setProperty(GRAPH_FILE, file);
                        PetriLabApplication.getInstance().getModeManager().setNormalMode();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Błąd w trakcie odczytu pliku.");
                        ex.printStackTrace();
                    }
                }
                break;
            case SAVE_FILE_BUTTON_ACTION:
                File graphFile = PetriLabApplication.getInstance().getConfiguration().getProperty(GRAPH_FILE);
                if (graphFile != null) {
                    PetriNetMatrixWithCoordinates petriNetMatrixWithCoordinates = PetriLabApplication.getInstance()
                            .generatePetriNetMatrixWithCoordinates();
                    try {
                        FileUtilities.saveFile(graphFile, petriNetMatrixWithCoordinates);
                        PetriLabApplication.getInstance().getConfiguration().setProperty(GRAPH_FILE, graphFile);
                        JOptionPane.showMessageDialog(this, "Zapisano");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Błąd w trakcie zapisu pliku.");
                        ex.printStackTrace();
                    }
                    break;
                }
            case SAVE_AS_FILE_BUTTON_ACTION:
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
                        PetriLabApplication.getInstance().getConfiguration().setProperty(GRAPH_FILE, file);
                        JOptionPane.showMessageDialog(this, "Zapisano");
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
