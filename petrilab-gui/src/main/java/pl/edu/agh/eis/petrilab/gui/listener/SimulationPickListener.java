package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: SimulationPickListener
 * Description: SimulationPickListener
 * Date: 2015-05-21
 * Created by BamBalooon
 */
public class SimulationPickListener implements ItemListener {
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private final PickedState<PetriNetVertex> vertexPickedState;

    public SimulationPickListener(VisualizationViewer<PetriNetVertex, Arc> graphViewer,
                                  PickedState<PetriNetVertex> vertexPickedState) {
        this.graphViewer = graphViewer;
        this.vertexPickedState = vertexPickedState;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        PetriNetGraph simulationGraph = PetriLabApplication.getInstance().getSimulationGraph();
        if (e.getItem() instanceof Transition) {
            Transition transition = (Transition) e.getItem();
            if (vertexPickedState.isPicked(transition)) {
                simulationGraph.fireTransition(transition);
                vertexPickedState.pick(transition, false);
                graphViewer.repaint();
            }
        } else {
            vertexPickedState.pick((PetriNetVertex) e.getItem(), false);
        }
    }
}
