package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

import javax.swing.JPanel;

/**
 * Name: Menu
 * Description: Menu
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PickingPanel extends JPanel {
    private final PlaceEditionPanel placeEditionPanel;
    private final TransitionEditionPanel transitionEditionPanel;
    private final ArcEditionPanel arcEditionPanel;

    public PickingPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        placeEditionPanel = new PlaceEditionPanel(graphViewer);
        transitionEditionPanel = new TransitionEditionPanel(graphViewer);
        arcEditionPanel = new ArcEditionPanel(graphViewer);
        add(placeEditionPanel);
        add(transitionEditionPanel);
        add(arcEditionPanel);
    }

    public void editPlace(Place place) {
        placeEditionPanel.edit(place);
    }

    public void cancelPlace() {
        placeEditionPanel.cancel();
    }

    public void editTransition(Transition transition) {
        transitionEditionPanel.edit(transition);
    }

    public void cancelTransition() {
        transitionEditionPanel.cancel();
    }

    public void editArc(Arc arc) {
        arcEditionPanel.edit(arc);
    }

    public void cancelArc() {
        arcEditionPanel.cancel();
    }
}
