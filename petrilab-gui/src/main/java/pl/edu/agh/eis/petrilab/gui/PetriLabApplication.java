package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetManager;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNet;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

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
    private PetriNetManager petriNetManager = new PetriNetManager();

    public void startGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                petriLabGui = new PetriLabGui();
                petriLabGui.setVisible(true);
            }
        });
    }

    public PetriNetManager getPetriNetManager() {
        return petriNetManager;
    }

    public DirectedSparseGraph<PetriNetVertex, Arc> getPetriNetGraph() {
        return petriNetManager.getGraph();
    }

    public PetriNet getPetriNet() {
        return petriNetManager.getPetriNet();
    }

    public static void main(String... args) {
        PetriLabApplication.getInstance().startGui();
    }

}
