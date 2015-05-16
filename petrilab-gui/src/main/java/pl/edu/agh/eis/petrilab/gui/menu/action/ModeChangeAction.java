package pl.edu.agh.eis.petrilab.gui.menu.action;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.mouse.PetriNetModalGraphMouse;

/**
 * Name: ModeChangeAction
 * Description: ModeChangeAction
 * Date: 2015-05-15
 * Created by BamBalooon
 */
public class ModeChangeAction implements Action {
    private final PetriNetModalGraphMouse graphMouse;
    private final ModalGraphMouse.Mode mode;

    public ModeChangeAction(PetriNetModalGraphMouse graphMouse, ModalGraphMouse.Mode mode) {
        this.graphMouse = graphMouse;
        this.mode = mode;
    }

    @Override
    public void invoke() {
        graphMouse.setMode(mode);
    }
}
