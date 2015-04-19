package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.menu.PetriNetEditionMenu;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: VertexPickListener
 * Description: VertexPickListener
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class VertexPickListener implements ItemListener {
    private final PickedState<PetriNetVertex> pickedState;
    private final PetriNetEditionMenu menu;

    public VertexPickListener(PickedState<PetriNetVertex> pickedState, PetriNetEditionMenu menu) {
        this.pickedState = pickedState;
        this.menu = menu;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (item instanceof PTPlace) {
            PTPlace place = (PTPlace) item;
            if (pickedState.isPicked(place)) {
                menu.editPlace(place);
            } else {
                menu.cancelPlace();
            }
        } else if (item instanceof GeneralTransition) {
            GeneralTransition transition = (GeneralTransition) item;
            if (pickedState.isPicked(transition)) {
                menu.editTransition(transition);
            } else {
                menu.cancelTransition();
            }
        } else {
            throw new IllegalArgumentException("Vertex of type not supported: " + item);
        }
    }
}
