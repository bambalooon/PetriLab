package pl.edu.agh.eis.petrilab.gui.menu.action;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.jung.transform.VertexFillPaintTransformer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

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
        graphViewer.getRenderContext().setVertexFillPaintTransformer(new VertexFillPaintTransformer());
        graphViewer.repaint();
    }
}
