package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: ModeChangeListener
 * Description: ModeChangeListener
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class ModeChangeListener implements ItemListener {
    private final PickedState<PetriNetVertex> vertexPickedState;
    private final PickedState<Arc> arcPickedState;

    public ModeChangeListener(PickedState<PetriNetVertex> vertexPickedState, PickedState<Arc> arcPickedState) {
        this.vertexPickedState = vertexPickedState;
        this.arcPickedState = arcPickedState;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED && e.getItem() == ModalGraphMouse.Mode.PICKING) {
            vertexPickedState.clear();
            arcPickedState.clear();
        }
    }
}
