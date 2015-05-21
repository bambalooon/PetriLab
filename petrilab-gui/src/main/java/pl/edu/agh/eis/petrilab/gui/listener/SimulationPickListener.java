package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: SimulationPickListener
 * Description: SimulationPickListener
 * Date: 2015-05-21
 * Created by BamBalooon
 */
public class SimulationPickListener implements ItemListener {
    private final PickedState<PetriNetVertex> vertexPickedState;

    public SimulationPickListener(PickedState<PetriNetVertex> vertexPickedState) {
        this.vertexPickedState = vertexPickedState;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("pick: " + e);
    }
}
