package pl.edu.agh.eis.petrilab.gui.menu;

import pl.edu.agh.eis.petrilab.model.PTPlace;

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
    private PTPlaceEditionPanel placeEditionPanel = new PTPlaceEditionPanel();

    public PetriNetEditionMenu() {
        add(placeEditionPanel);
    }

    public void editPlace(PTPlace place) {
        placeEditionPanel.edit(place);
    }

    public void acceptPlace() {
        placeEditionPanel.accept();
    }

    public void cancelPlace() {
        placeEditionPanel.cancel();
    }
}
