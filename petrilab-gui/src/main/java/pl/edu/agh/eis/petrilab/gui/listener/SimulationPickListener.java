package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.menu.simulation.SimulationPanel;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

/**
 * Name: SimulationPickListener
 * Description: SimulationPickListener
 * Date: 2015-05-21
 * Created by BamBalooon
 */
public class SimulationPickListener implements ItemListener {
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private final PickedState<PetriNetVertex> vertexPickedState;
    private final SimulationPanel simulationPanel;

    public SimulationPickListener(VisualizationViewer<PetriNetVertex, Arc> graphViewer,
                                  PickedState<PetriNetVertex> vertexPickedState,
                                  SimulationPanel simulationPanel) {
        this.graphViewer = graphViewer;
        this.vertexPickedState = vertexPickedState;
        this.simulationPanel = simulationPanel;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() instanceof Transition) {
            Transition transition = (Transition) e.getItem();
            if (vertexPickedState.isPicked(transition)) {
                PetriLabApplication.getInstance().getSimulationGraph().fireTransition(transition);
                simulationPanel.updateMarking();
                graphViewer.repaint();
            }
        }
        if (e.getItem() instanceof PetriNetVertex){
            vertexPickedState.pick((PetriNetVertex) e.getItem(), false);
        }
    }
}
