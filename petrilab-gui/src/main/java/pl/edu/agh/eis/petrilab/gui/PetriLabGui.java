package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetManager;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;

/**
 * Name: PetriLabGui
 * Description: PetriLabGui
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabGui extends JFrame {
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 600;
    private PetriNetManager petriNetManager;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);
        petriNetManager = new PetriNetManager();

        VisualizationViewer<PetriNetVertex, Arc> graphViewer = VisualizationViewerGenerator.PETRI_NET
                .generateVisualizationViewer(petriNetManager);

        setUpFrame(graphViewer, new JPanel());
    }

    private void setUpFrame(VisualizationViewer<PetriNetVertex, Arc> graphViewer, JComponent menu) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphViewer, menu);
        splitPane.setResizeWeight(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        pack();
    }

    /*
    private class Menu extends JPanel implements ActionListener {
        private static final String ADD_PLACE_CMD = "ADD_PLACE_CMD";
        private static final String ADD_PLACE_DONE_CMD = "ADD_PLACE_DONE_CMD";
        private static final String ADD_PLACE_CANCEL_CMD = "ADD_PLACE_CANCEL_CMD";
        private static final String ADD_TRANSITION_CMD = "ADD_TRANSITION_CMD";
        private static final String ADD_TRANSITION_DONE_CMD = "ADD_TRANSITION_DONE_CMD";
        private static final String ADD_TRANSITION_CANCEL_CMD = "ADD_TRANSITION_CANCEL_CMD";
        private static final String ADD_ARC_CMD = "ADD_ARC_CMD";
        private static final String ADD_ARC_CANCEL_CMD = "ADD_ARC_CANCEL_CMD";
        private static final int TEXT_FIELD_WIDTH = 50;
        private static final int TEXT_FIELD_HEIGHT = 20;
        private JPanel placeOptionsPanel;
        private JPanel transitionOptionsPanel;
        private JButton addArcButton;

        public Menu() {
            JButton addPlaceBtn = generateButton(ADD_PLACE_CMD, "P");
            placeOptionsPanel = generateOptionsPanel(ADD_PLACE_DONE_CMD, ADD_PLACE_CANCEL_CMD);
            placeOptionsPanel.setVisible(false);
            JButton addTransitionBtn = generateButton(ADD_TRANSITION_CMD, "T");
            transitionOptionsPanel = generateOptionsPanel(ADD_TRANSITION_DONE_CMD, ADD_TRANSITION_CANCEL_CMD);
            transitionOptionsPanel.setVisible(false);
            addArcButton = generateButton(ADD_ARC_CMD, "A");
            add(addPlaceBtn);
            add(placeOptionsPanel);
            add(addTransitionBtn);
            add(transitionOptionsPanel);
            add(addArcButton);
        }

        private JButton generateButton(String actionCmd, String text) {
            JButton button = new JButton();
            button.addActionListener(this);
            button.setActionCommand(actionCmd);
            button.setText(text);
            return button;
        }

        private JPanel generateOptionsPanel(String acceptAction, String cancelAction) {
            JPanel panel = new JPanel();
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
            JButton acceptBtn = new JButton();
            acceptBtn.addActionListener(this);
            acceptBtn.setActionCommand(acceptAction);
            acceptBtn.setText("ok");
            JButton cancelBtn = new JButton();
            cancelBtn.addActionListener(this);
            cancelBtn.setActionCommand(cancelAction);
            cancelBtn.setText("x");
            panel.add(nameField);
            panel.add(acceptBtn);
            panel.add(cancelBtn);
            return panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case ADD_PLACE_CMD:
                    placeOptionsPanel.setVisible(true);
                    revalidate();
                    break;
                case ADD_PLACE_DONE_CMD:
                    PTPlace newPlace = new PTPlace.Builder()
                            .withName(((JTextField) placeOptionsPanel.getComponent(0)).getText())
                            .build();
                    petriNet.addVertex(newPlace);
                    placeOptionsPanel.setVisible(false);
                    PetriLabGui.this.revalidate();
                    PetriLabGui.this.repaint();
                    break;
                case ADD_PLACE_CANCEL_CMD:
                    placeOptionsPanel.setVisible(false);
                    revalidate();
                    break;
                case ADD_TRANSITION_CMD:
                    transitionOptionsPanel.setVisible(true);
                    revalidate();
                    break;
                case ADD_TRANSITION_DONE_CMD:
                    transitionOptionsPanel.setVisible(false);
                    GeneralTransition newTransition = new GeneralTransition.Builder()
                            .withName(((JTextField) transitionOptionsPanel.getComponent(0)).getText())
                            .build();
                    petriNet.addVertex(newTransition);
                    PetriLabGui.this.revalidate();
                    PetriLabGui.this.repaint();
                    break;
                case ADD_TRANSITION_CANCEL_CMD:
                    transitionOptionsPanel.setVisible(false);
                    revalidate();
                    break;
                case ADD_ARC_CMD:
                    addArcButton.setText("X");
                    addArcButton.setActionCommand(ADD_ARC_CANCEL_CMD);
                    break;
                case ADD_ARC_CANCEL_CMD:
                    addArcButton.setText("A");
                    addArcButton.setActionCommand(ADD_ARC_CMD);
            }
        }
    }
    */
}
