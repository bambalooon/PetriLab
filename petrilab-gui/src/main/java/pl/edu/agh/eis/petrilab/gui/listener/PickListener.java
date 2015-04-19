package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.menu.PetriNetEditionMenu;
import pl.edu.agh.eis.petrilab.model.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: PickListener
 * Description: PickListener
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PickListener implements ItemListener {
    private final PickedState<PetriNetVertex> vertexPickedState;
    private final PickedState<Arc> arcPickedState;
    private final PetriNetEditionMenu menu;

    public PickListener(PickedState<PetriNetVertex> vertexPickedState,
                        PickedState<Arc> arcPickedState,
                        PetriNetEditionMenu menu) {

        this.vertexPickedState = vertexPickedState;
        this.arcPickedState = arcPickedState;
        this.menu = menu;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (item instanceof PTPlace) {
            PTPlace place = (PTPlace) item;
            if (vertexPickedState.isPicked(place)) {
                menu.editPlace(place);
                arcPickedState.clear();
            } else {
                menu.cancelPlace();
            }
        } else if (item instanceof GeneralTransition) {
            GeneralTransition transition = (GeneralTransition) item;
            if (vertexPickedState.isPicked(transition)) {
                menu.editTransition(transition);
                arcPickedState.clear();
            } else {
                menu.cancelTransition();
            }
        } else if (item instanceof Arc) {
            Arc arc = (Arc) item;
            if (arcPickedState.isPicked(arc)) {
                menu.editArc(arc);
                vertexPickedState.clear();
            } else {
                menu.cancelArc();
            }
        } else {
            throw new IllegalArgumentException("Item of type not supported: " + item);
        }
    }
}
