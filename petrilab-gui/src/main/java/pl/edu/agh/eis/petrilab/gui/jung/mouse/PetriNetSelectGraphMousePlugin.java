package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model.Transition;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * Name: PetriNetSelectGraphMousePlugin
 * Description: PetriNetSelectGraphMousePlugin
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class PetriNetSelectGraphMousePlugin extends AbstractGraphMousePlugin implements MouseListener {
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
                if (vertex instanceof Transition) {
                    System.out.println("Transition clicked!");
                }
            }
        }
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
