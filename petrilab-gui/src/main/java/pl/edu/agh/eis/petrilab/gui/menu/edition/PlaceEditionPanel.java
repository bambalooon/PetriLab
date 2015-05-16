package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;

/**
 * Name: PlaceEditionPanel
 * Description: PlaceEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PlaceEditionPanel extends AbstractEditionPanel<Place> {
    private final JTextField nameField;
    private final JSpinner markingSpinner;
    private final JSpinner capacitySpinner;

    public PlaceEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        SpinnerNumberModel markingModel = new SpinnerNumberModel(
                Place.MARKING_DEFAULT,
                Place.MARKING_MIN,
                Place.MARKING_MAX,
                1);
        markingSpinner = new JSpinner(markingModel);
        markingSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        SpinnerNumberModel capacityModel = new SpinnerNumberModel(
                Place.CAPACITY_DEFAULT,
                Place.CAPACITY_MIN,
                Place.CAPACITY_MAX,
                1);
        capacitySpinner = new JSpinner(capacityModel);
        capacitySpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        add(nameField);
        add(markingSpinner);
        add(capacitySpinner);
        add(acceptButton);
    }

    @Override
    protected void loadItemState(Place place) {
        nameField.setText(place.getName());
        markingSpinner.setValue(place.getMarking());
        capacitySpinner.setValue(place.getCapacity());
    }

    @Override
    protected void modify(Place place) {
        place.setName(nameField.getText());
        place.setMarking((Integer) markingSpinner.getValue());
        place.setCapacity((Integer) capacitySpinner.getValue());
    }
}
