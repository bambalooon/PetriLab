package pl.edu.agh.eis.petrilab.gui.menu.action;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;

/**
 * Name: StartSimulationMode
 * Description: StartSimulationMode
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class StartSimulationModeAction implements Action {
    @Override
    public void invoke() {
        PetriLabApplication.getInstance().getModeManager().setSimulationMode();
    }
}
