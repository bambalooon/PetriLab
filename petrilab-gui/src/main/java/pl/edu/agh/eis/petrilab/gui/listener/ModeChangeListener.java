package pl.edu.agh.eis.petrilab.gui.listener;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.menu.PetriNetEditionMenu;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: ModeChangeListener
 * Description: ModeChangeListener
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class ModeChangeListener implements ItemListener {
    private final PetriNetEditionMenu menu;

    public ModeChangeListener(PetriNetEditionMenu menu) {
        this.menu = menu;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED && e.getItem() == ModalGraphMouse.Mode.PICKING) {
            menu.cancelPlace();
            menu.cancelTransition();
            menu.cancelArc();
        }
    }
}
