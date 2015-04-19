package pl.edu.agh.eis.petrilab.gui.menu;

import pl.edu.agh.eis.petrilab.model.GeneralPlace;
import pl.edu.agh.eis.petrilab.model.PTPlace;

import javax.swing.*;
import java.awt.*;

/**
 * Name: PTPlaceEditionPanel
 * Description: PTPlaceEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PTPlaceEditionPanel extends JPanel {
    private final JTextField nameField;
    private final JSpinner markingSpinner;
    private final JSpinner capacitySpinner;
    private PTPlace place;

    public PTPlaceEditionPanel() {
        setVisible(false);
        nameField = new JTextField();
        nameField.setPreferredSize(
                new Dimension(PetriNetEditionMenu.TEXT_FIELD_WIDTH, PetriNetEditionMenu.TEXT_FIELD_HEIGHT));
        SpinnerNumberModel markingModel = new SpinnerNumberModel(
                GeneralPlace.MARKING_DEFAULT,
                GeneralPlace.MARKING_MIN,
                GeneralPlace.MARKING_MAX,
                1);
        markingSpinner = new JSpinner(markingModel);
        SpinnerNumberModel capacityModel = new SpinnerNumberModel(
                PTPlace.CAPACITY_DEFAULT,
                PTPlace.CAPACITY_MIN,
                PTPlace.CAPACITY_MAX,
                1);
        capacitySpinner = new JSpinner(capacityModel);
        add(nameField);
        add(markingSpinner);
        add(capacitySpinner);
    }

    public void edit(PTPlace place) {
        setVisible(true);
        this.place = place;
        nameField.setText(place.getName());
        markingSpinner.setValue(place.getMarking());
        capacitySpinner.setValue(place.getCapacity());
        revalidate();
    }

    public void accept() {
        new PTPlace.Builder()
                .fromPlace(place)
                .withName(nameField.getText())
                .withMarking((Integer) markingSpinner.getValue())
                .withCapacity((Integer) capacitySpinner.getValue())
                .modify();
        cancel();
    }

    public void cancel() {
        place = null;
        setVisible(false);
        revalidate();
    }

}
