package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.menu.PetriNetEditionMenu;
import pl.edu.agh.eis.petrilab.model.Arc;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: EdgePickListener
 * Description: EdgePickListener
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class EdgePickListener implements ItemListener {
    private final PickedState<Arc> pickedState;
    private final PetriNetEditionMenu menu;

    public EdgePickListener(PickedState<Arc> pickedState, PetriNetEditionMenu menu) {
        this.pickedState = pickedState;
        this.menu = menu;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (item instanceof Arc) {
            Arc arc = (Arc) item;
            if (pickedState.isPicked(arc)) {
                menu.editArc(arc);
            } else {
                menu.cancelArc();
            }
        } else {
            throw new IllegalArgumentException("Edge of type not supported: " + item);
        }
    }
}
