package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;

/**
 * Name: Menu
 * Description: Menu
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PetriNetEditionMenu extends JPanel {
    public static final int TEXT_FIELD_WIDTH = 50;
    public static final int TEXT_FIELD_HEIGHT = 20;
    private final PTPlaceEditionPanel placeEditionPanel;
    private final GeneralTransitionEditionPanel transitionEditionPanel;

    public PetriNetEditionMenu(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        placeEditionPanel = new PTPlaceEditionPanel(graphViewer);
        transitionEditionPanel = new GeneralTransitionEditionPanel(graphViewer);
        add(placeEditionPanel);
        add(transitionEditionPanel);
    }

    public void editPlace(PTPlace place) {
        placeEditionPanel.edit(place);
    }

    public void cancelPlace() {
        placeEditionPanel.cancel();
    }

    public void editTransition(GeneralTransition transition) {
        transitionEditionPanel.edit(transition);
    }

    public void cancelTransition() {
        transitionEditionPanel.cancel();
    }
}
