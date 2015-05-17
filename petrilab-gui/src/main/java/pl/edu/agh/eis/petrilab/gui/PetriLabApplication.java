package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.*;

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
    private DirectedSparseGraph<PetriNetVertex, Arc> petriNetGraph = new PetriNetGraph();
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

    public DirectedSparseGraph<PetriNetVertex, Arc> getPetriNetGraph() {
        return petriNetGraph;
    }

    public void updatePetriNetGraph(DirectedSparseGraph<PetriNetVertex, Arc> petriNetGraph) {
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
