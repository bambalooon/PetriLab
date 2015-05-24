package pl.edu.agh.eis.petrilab.gui.menu.action;

import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import static pl.edu.agh.eis.petrilab.gui.Configuration.SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE;
import static pl.edu.agh.eis.petrilab.gui.Configuration.SIMULATION_MODE_ACTIVE;

/**
 * Name: StopSimulationMode
 * Description: StopSimulationMode
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class StopSimulationModeAction implements Action {
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;

    public StopSimulationModeAction(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        this.graphViewer = graphViewer;
    }

    @Override
    public void invoke() {
        PetriLabApplication.getInstance().loadPetriNetGraph();
        PetriLabApplication.getInstance().getConfiguration().setProperty(SIMULATION_MODE_ACTIVE, false);
        VisualizationServer.Paintable activeTransitionPreRenderPaintable = PetriLabApplication.getInstance()
                .getConfiguration().removeProperty(SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE);
        graphViewer.removePreRenderPaintable(activeTransitionPreRenderPaintable);
        graphViewer.repaint();
    }
}
