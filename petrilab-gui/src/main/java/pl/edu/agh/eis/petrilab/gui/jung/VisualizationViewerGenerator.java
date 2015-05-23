package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.gui.jung.transform.VertexFillPaintTransformer;
import pl.edu.agh.eis.petrilab.gui.jung.transform.VertexIconTransformer;
import pl.edu.agh.eis.petrilab.gui.jung.transform.VertexShapeTransformer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;

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

            VisualizationViewer<PetriNetVertex, Arc> visualizationViewer = new VisualizationViewer<>
                    (new CircleLayout<>(graph), new Dimension(VIEWER_WIDTH, VIEWER_HEIGHT));

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

            visualizationViewer.getRenderContext().setVertexFillPaintTransformer(new VertexFillPaintTransformer());
            visualizationViewer.getRenderContext().setVertexShapeTransformer(new VertexShapeTransformer());
            visualizationViewer.getRenderContext().setVertexIconTransformer(new VertexIconTransformer());

            return visualizationViewer;
        }
    };

    VisualizationViewerGenerator<Marking, Transition> COVERABILITY_GRAPH =
            new VisualizationViewerGenerator<Marking, Transition>() {

        @Override
        public VisualizationViewer<Marking, Transition> generateVisualizationViewer(DirectedSparseGraph<Marking, Transition> graph) {

            //FIXME: need to remove vv size and maybe think about different way to show graphs than unresizable JOptionPane
            VisualizationViewer<Marking, Transition> visualizationViewer = new VisualizationViewer<>
                    (new CircleLayout<>(graph), new Dimension(1200, 600));

            visualizationViewer.getRenderContext().setVertexLabelTransformer(new Transformer<Marking, String>() {
                @Override
                public String transform(Marking marking) {
                    return marking.toString();
                }
            });

            visualizationViewer.getRenderContext().setEdgeLabelTransformer(new Transformer<Transition, String>() {
                @Override
                public String transform(Transition transition) {
                    return transition.toString();
                }
            });

            return visualizationViewer;
        }
    };
}
