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
 * Name: VisualizationViewerGenerator
 * Description: VisualizationViewerGenerator
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public interface VisualizationViewerGenerator<V, E> {
    int VIEWER_WIDTH = 400;
    int VIEWER_HEIGHT = 400;
    VisualizationViewer<V, E> generateVisualizationViewer(DirectedSparseGraph<V, E> graph);

    VisualizationViewerGenerator<PetriNetVertex, Arc> PETRI_NET =
            new VisualizationViewerGenerator<PetriNetVertex, Arc>() {

        @Override
        public VisualizationViewer<PetriNetVertex, Arc> generateVisualizationViewer(
                DirectedSparseGraph<PetriNetVertex, Arc> graph) {

            VisualizationViewer<PetriNetVertex, Arc> visualizationViewer = new VisualizationViewer<PetriNetVertex, Arc>
                    (new CircleLayout<PetriNetVertex, Arc>(graph), new Dimension(VIEWER_WIDTH, VIEWER_HEIGHT));

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

            return visualizationViewer;
        }
    };
}
