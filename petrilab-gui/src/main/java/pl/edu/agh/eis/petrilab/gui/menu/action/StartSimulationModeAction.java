package pl.edu.agh.eis.petrilab.gui.menu.action;

import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.jung.paintable.ActiveTransitionsBoundingRectanglePaintable;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import static pl.edu.agh.eis.petrilab.gui.Configuration.SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE;

/**
 * Name: StartSimulationMode
 * Description: StartSimulationMode
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class StartSimulationModeAction implements Action {
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;

    public StartSimulationModeAction(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        this.graphViewer = graphViewer;
    }

    @Override
    public void invoke() {
        VisualizationServer.Paintable activeTransitionPreRenderPaintable = new ActiveTransitionsBoundingRectanglePaintable(
                graphViewer.getRenderContext(), graphViewer.getGraphLayout());
        PetriLabApplication.getInstance().getConfiguration()
                .setProperty(SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE, activeTransitionPreRenderPaintable);
        graphViewer.addPreRenderPaintable(activeTransitionPreRenderPaintable);
        graphViewer.repaint();
    }
}
