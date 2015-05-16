package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetModalGraphMouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: MenuModePanel
 * Description: MenuModePanel
 * Date: 2015-05-14
 * Created by BamBalooon
 */
public class PetriNetEditionModePanel extends JPanel implements ActionListener {
    private static final String EDITION_MENU_BUTTON_LABEL = "E";
    private static final String EDITION_MENU_BUTTON_ACTION = "EDITION_MENU_BUTTON_ACTION";
    private static final String PICK_MENU_BUTTON_LABEL = "P";
    private static final String PICK_MENU_BUTTON_ACTION = "PICK_MENU_BUTTON_ACTION";
    private static final String TRANSFORM_MENU_BUTTON_LABEL = "T";
    private static final String TRANSFORM_MENU_BUTTON_ACTION = "TRANSFORM_MENU_BUTTON_ACTION";

    private final PetriNetModalGraphMouse petriNetModalGraphMouse;

    public PetriNetEditionModePanel(PetriNetModalGraphMouse petriNetModalGraphMouse) {
        this.petriNetModalGraphMouse = petriNetModalGraphMouse;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setVisible(true);
        setUpButtons();
    }

    private void setUpButtons() {
        JButton editionModeButton = new JButton();
        editionModeButton.setText(EDITION_MENU_BUTTON_LABEL);
        editionModeButton.setActionCommand(EDITION_MENU_BUTTON_ACTION);
        editionModeButton.addActionListener(this);
        add(editionModeButton);
        JButton pickModeButton = new JButton();
        pickModeButton.setText(PICK_MENU_BUTTON_LABEL);
        pickModeButton.setActionCommand(PICK_MENU_BUTTON_ACTION);
        pickModeButton.addActionListener(this);
        add(pickModeButton);
        JButton transformModeButton = new JButton();
        transformModeButton.setText(TRANSFORM_MENU_BUTTON_LABEL);
        transformModeButton.setActionCommand(TRANSFORM_MENU_BUTTON_ACTION);
        transformModeButton.addActionListener(this);
        add(transformModeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case EDITION_MENU_BUTTON_ACTION:
                petriNetModalGraphMouse.setMode(ModalGraphMouse.Mode.EDITING);
                break;
            case PICK_MENU_BUTTON_ACTION:
                petriNetModalGraphMouse.setMode(ModalGraphMouse.Mode.PICKING);
                break;
            case TRANSFORM_MENU_BUTTON_ACTION:
                petriNetModalGraphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                break;
        }
    }
}
