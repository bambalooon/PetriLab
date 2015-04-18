package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import java.awt.*;

/**
 * Name: PetriNetViewer
 * Description: PetriNetViewer
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNetViewer {
    private static final int VIEWER_WIDTH = 400;
    private static final int VIEWER_HEIGHT = 400;
    private final VisualizationViewer<PetriNetVertex, Arc> visualizationViewer;

    public PetriNetViewer(DirectedSparseGraph<PetriNetVertex, Arc> graph) {
        visualizationViewer = new VisualizationViewer<PetriNetVertex, Arc>(
                new CircleLayout<PetriNetVertex, Arc>(graph), new Dimension(VIEWER_WIDTH, VIEWER_HEIGHT));
        visualizationViewer.getRenderContext().setVertexLabelTransformer(new Transformer<PetriNetVertex, String>() {
            @Override
            public String transform(PetriNetVertex vertex) {
                return vertex.getName();
            }
        });
        visualizationViewer.getRenderContext().setEdgeLabelTransformer(new Transformer<Arc, String>() {
            @Override
            public String transform(Arc arc) {
                return String.valueOf(arc.getWeight());
            }
        });
        visualizationViewer.getRenderer().setVertexRenderer(new PetriNetVertexRenderer());
        final DefaultModalGraphMouse<String,Number> graphMouse = new DefaultModalGraphMouse<String, Number>();
        visualizationViewer.setGraphMouse(graphMouse);
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
    }

    public VisualizationViewer<PetriNetVertex, Arc> getVisualizationViewer() {
        return visualizationViewer;
    }
}
