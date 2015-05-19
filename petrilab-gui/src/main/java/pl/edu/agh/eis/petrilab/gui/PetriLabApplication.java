package pl.edu.agh.eis.petrilab.gui;

import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import javax.swing.SwingUtilities;

/**
 * Name: PetriLabApplication
 * Description: PetriLabApplication
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabApplication {
    public static final String TITLE = "PetriLab";
    private static PetriLabApplication INSTANCE;
    public static PetriLabApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PetriLabApplication();
        }
        return INSTANCE;
    }
    private PetriLabGui petriLabGui;
    private PetriNetGraph petriNetGraph = new PetriNetGraph();
    private Configuration configuration = new Configuration();

    public void startGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                petriLabGui = new PetriLabGui();
                petriLabGui.setVisible(true);
            }
        });
    }

    public PetriNetGraph getPetriNetGraph() {
        return petriNetGraph;
    }

    public void updatePetriNetGraph(PetriNetGraph petriNetGraph) {
        this.petriNetGraph = petriNetGraph;
        this.petriLabGui.updatePetriNetGraph();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String... args) {
        PetriLabApplication.getInstance().startGui();
    }
}
