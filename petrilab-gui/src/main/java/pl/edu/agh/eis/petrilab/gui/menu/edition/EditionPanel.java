package pl.edu.agh.eis.petrilab.gui.menu.edition;

import pl.edu.agh.eis.petrilab.gui.Configuration;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: PetriNetEditionVertexSelectionPanel
 * Description: PetriNetEditionVertexSelectionPanel
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class EditionPanel extends JPanel implements ActionListener {
    private static final String SELECT_PLACE_BUTTON_LABEL = "Miejsce";
    private static final String SELECT_PLACE_BUTTON_ACTION = "SELECT_PLACE_BUTTON_ACTION";
    private static final String SELECT_TRANSITION_BUTTON_LABEL = "Przejście";
    private static final String SELECT_TRANSITION_BUTTON_ACTION = "SELECT_TRANSITION_BUTTON_ACTION";

    private final Configuration configuration;

    public EditionPanel() {
        this(PetriLabApplication.getInstance().getConfiguration());
    }

    public EditionPanel(Configuration configuration) {
        this.configuration = configuration;

        JRadioButton selectPlaceButton = new JRadioButton(SELECT_PLACE_BUTTON_LABEL);
        selectPlaceButton.setActionCommand(SELECT_PLACE_BUTTON_ACTION);
        selectPlaceButton.setSelected(true);

        JRadioButton selectTransitionButton = new JRadioButton(SELECT_TRANSITION_BUTTON_LABEL);
        selectTransitionButton.setActionCommand(SELECT_TRANSITION_BUTTON_ACTION);

        ButtonGroup group = new ButtonGroup();
        group.add(selectPlaceButton);
        group.add(selectTransitionButton);

        selectPlaceButton.addActionListener(this);
        selectTransitionButton.addActionListener(this);

        add(selectPlaceButton);
        add(selectTransitionButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SELECT_PLACE_BUTTON_ACTION:
                configuration.setProperty(Configuration.VERTEX_FACTORY_TYPE, Place.class);
                break;
            case SELECT_TRANSITION_BUTTON_ACTION:
                configuration.setProperty(Configuration.VERTEX_FACTORY_TYPE, Transition.class);
                break;
        }
    }
}
