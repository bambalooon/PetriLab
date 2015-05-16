package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * Name: PetriNetSelectGraphMousePlugin
 * Description: PetriNetSelectGraphMousePlugin
 * Date: 2015-05-16
 * Created by BamBalooon
 */
@Deprecated
public class PetriNetSelectGraphMousePlugin extends AbstractGraphMousePlugin implements MouseListener {
    private PTPlace selectedFromPlace;
    private PTPlace selectedToPlace;
    private GeneralTransition selectedTransition;

    public PetriNetSelectGraphMousePlugin() {
        super(MouseEvent.BUTTON1_MASK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (checkModifiers(e)) {
            final VisualizationViewer<PetriNetVertex, Arc> eventVv =
                    (VisualizationViewer<PetriNetVertex, Arc>) e.getSource();
            Point2D eventPoint = e.getPoint();
            GraphElementAccessor<PetriNetVertex, Arc> pickSupport = eventVv.getPickSupport();
            if (pickSupport != null) {
                PetriNetVertex vertex = pickSupport
                        .getVertex(eventVv.getGraphLayout(), eventPoint.getX(), eventPoint.getY());

                if (vertex instanceof PTPlace) {
                    if (selectedFromPlace == null) {
                        selectedFromPlace = (PTPlace) vertex;
                    } else if (selectedToPlace == null) {
                        selectedToPlace = (PTPlace) vertex;
                    } else {
                        //TODO: add error message popup
                        clearSelection(eventVv);
                        return;
                    }
                } else if (vertex instanceof  GeneralTransition) {
                    if (selectedTransition == null) {
                        selectedTransition = (GeneralTransition) vertex;
                    } else {
                        //TODO: add error message popup
                        clearSelection(eventVv);
                        return;
                    }
                }
                pickVertices(eventVv.getPickedVertexState());
            }
        }
    }

    private void pickVertices(PickedState<PetriNetVertex> pickedVertexState) {
        if (selectedFromPlace != null) {
            pickedVertexState.pick(selectedFromPlace, true);
        }
        if (selectedToPlace != null) {
            pickedVertexState.pick(selectedToPlace, true);
        }
        if (selectedTransition != null) {
            pickedVertexState.pick(selectedTransition, true);
        }
    }

    private void clearSelection(VisualizationViewer<PetriNetVertex, Arc> eventVv) {
        eventVv.getPickedVertexState().clear();
        selectedFromPlace = null;
        selectedToPlace = null;
        selectedTransition = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
