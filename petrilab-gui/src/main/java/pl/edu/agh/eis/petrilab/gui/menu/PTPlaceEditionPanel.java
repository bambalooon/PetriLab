package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralPlace;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;

/**
 * Name: PTPlaceEditionPanel
 * Description: PTPlaceEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PTPlaceEditionPanel extends AbstractEditionPanel<PTPlace> {
    private final JTextField nameField;
    private final JSpinner markingSpinner;
    private final JSpinner capacitySpinner;

    public PTPlaceEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        SpinnerNumberModel markingModel = new SpinnerNumberModel(
                GeneralPlace.MARKING_DEFAULT,
                GeneralPlace.MARKING_MIN,
                GeneralPlace.MARKING_MAX,
                1);
        markingSpinner = new JSpinner(markingModel);
        markingSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        SpinnerNumberModel capacityModel = new SpinnerNumberModel(
                PTPlace.CAPACITY_DEFAULT,
                PTPlace.CAPACITY_MIN,
                PTPlace.CAPACITY_MAX,
                1);
        capacitySpinner = new JSpinner(capacityModel);
        capacitySpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        add(nameField);
        add(markingSpinner);
        add(capacitySpinner);
        add(acceptButton);
    }

    @Override
    protected void loadItemState(PTPlace place) {
        nameField.setText(place.getName());
        markingSpinner.setValue(place.getMarking());
        capacitySpinner.setValue(place.getCapacity());
    }

    @Override
    protected void modify(PTPlace place) {
        new PTPlace.Builder()
                .fromPlace(place)
                .withName(nameField.getText())
                .withMarking((Integer) markingSpinner.getValue())
                .withCapacity((Integer) capacitySpinner.getValue())
                .modify();
    }
}
