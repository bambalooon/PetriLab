package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNet;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: PetriLabGui
 * Description: PetriLabGui
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabGui extends JFrame {
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 600;
    private PetriNet petriNet;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);
        this.petriNet = new PetriNet();
        setUpFrame(VisualizationViewerGenerator.PETRI_NET.generateVisualizationViewer(petriNet.getGraph()), new Menu());
    }

    private void setUpFrame(VisualizationViewer<PetriNetVertex, Arc> graphViewer, JComponent menu) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        getContentPane().add(graphViewer, BorderLayout.CENTER);
        getContentPane().add(menu, BorderLayout.EAST);
        pack();
    }

    private class Menu extends JToolBar implements ActionListener {
        private static final String PETRI_LAB_TOOLBAR = "PetriLab Toolbar";
        private static final String ADD_PLACE_CMD = "ADD_PLACE_CMD";
        private static final String ADD_TRANSITION_CMD = "ADD_TRANSITION_CMD";

        public Menu() {
            super(PETRI_LAB_TOOLBAR, JToolBar.VERTICAL);
            JButton addPlaceBtn = generateButton(ADD_PLACE_CMD, "P");
            JButton addTransitionBtn = generateButton(ADD_TRANSITION_CMD, "T");
            add(addPlaceBtn);
            add(addTransitionBtn);
        }

        private JButton generateButton(String actionCmd, String text) {
            JButton button = new JButton();
            button.addActionListener(this);
            button.setActionCommand(actionCmd);
            button.setText(text);
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
