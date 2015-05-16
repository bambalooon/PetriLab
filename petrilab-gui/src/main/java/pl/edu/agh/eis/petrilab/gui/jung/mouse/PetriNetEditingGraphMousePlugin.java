package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetManager;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Name: PetriNetEditingGraphMousePlugin
 * Description: PetriNetEditingGraphMousePlugin
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNetEditingGraphMousePlugin extends EditingGraphMousePlugin<PetriNetVertex, Arc> {
    private final PetriNetManager petriNetManager;

    public PetriNetEditingGraphMousePlugin() {
        this(PetriLabApplication.getInstance().getPetriNetManager());
    }

    public PetriNetEditingGraphMousePlugin(PetriNetManager petriNetManager) {
        super(null, null);
        this.petriNetManager = petriNetManager;
    }

    private PetriNetVertex createVertex(MouseEvent e) {
        if (e.isControlDown()) {
            return new GeneralTransition.Builder().build();
        }
        return new PTPlace.Builder().build();
    }

    private Arc createArc(PetriNetVertex startVertex, PetriNetVertex endVertex) {
        try {
            Arc arc = new Arc.Builder().from(startVertex).to(endVertex).build();
            if (startVertex instanceof PTPlace) {
                new PTPlace.Builder().fromPlace((PTPlace) startVertex).withArc(arc).modify();
                new GeneralTransition.Builder().fromTransition((GeneralTransition) endVertex).withArc(arc).modify();
            } else {
                new GeneralTransition.Builder().fromTransition((GeneralTransition) startVertex).withArc(arc).modify();
                new PTPlace.Builder().fromPlace((PTPlace) endVertex).withArc(arc).modify();
            }
            return arc;
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, "Nie można łączyć miejsc i przejść między sobą.");
            return null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(checkModifiers(e)) {
            final VisualizationViewer<PetriNetVertex, Arc> vv =
                    (VisualizationViewer<PetriNetVertex, Arc>)e.getSource();
            final Point2D p = e.getPoint();
            GraphElementAccessor<PetriNetVertex, Arc> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
                Graph<PetriNetVertex, Arc> graph = vv.getModel().getGraphLayout().getGraph();
                // set default edge type
                if(graph instanceof DirectedGraph) {
                    edgeIsDirected = EdgeType.DIRECTED;
                } else {
                    edgeIsDirected = EdgeType.UNDIRECTED;
                }

                final PetriNetVertex vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
                if(vertex != null) { // get ready to make an edge
                    startVertex = vertex;
                    down = e.getPoint();
                    transformEdgeShape(down, down);
                    vv.addPostRenderPaintable(edgePaintable);
                    if((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0
                            && vv.getModel().getGraphLayout().getGraph() instanceof UndirectedGraph == false) {
                        edgeIsDirected = EdgeType.DIRECTED;
                    }
                    if(edgeIsDirected == EdgeType.DIRECTED) {
                        transformArrowShape(down, e.getPoint());
                        vv.addPostRenderPaintable(arrowPaintable);
                    }
                } else { // make a new vertex

                    PetriNetVertex newVertex = createVertex(e);
                    Layout<PetriNetVertex, Arc> layout = vv.getModel().getGraphLayout();
                    petriNetManager.addVertex(newVertex);
                    layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));
                }
            }
            vv.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(checkModifiers(e)) {
            final VisualizationViewer<PetriNetVertex, Arc> vv =
                    (VisualizationViewer<PetriNetVertex, Arc>)e.getSource();
            final Point2D p = e.getPoint();
            Layout<PetriNetVertex, Arc> layout = vv.getModel().getGraphLayout();
            GraphElementAccessor<PetriNetVertex, Arc> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
                final PetriNetVertex vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
                if(vertex != null && startVertex != null) {
                    Arc newArc = createArc(startVertex, vertex);
                    if (newArc != null) {
                        petriNetManager.addEdge(newArc);
                    }
                }
            }
            startVertex = null;
            down = null;
            edgeIsDirected = EdgeType.UNDIRECTED;
            vv.removePostRenderPaintable(edgePaintable);
            vv.removePostRenderPaintable(arrowPaintable);
            vv.repaint();
        }
    }

    private void transformEdgeShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);

        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        float dist = (float) Math.sqrt(dx*dx + dy*dy);
        xform.scale(dist / rawEdge.getBounds().getWidth(), 1.0);
        edgeShape = xform.createTransformedShape(rawEdge);
    }

    private void transformArrowShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);

        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        arrowShape = xform.createTransformedShape(rawArrowShape);
    }
}
