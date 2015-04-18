package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNet;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetViewer;
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
    private PetriNet petriNet;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);
        this.petriNet = new PetriNet();
        setUpFrame(new PetriNetViewer(petriNet.getGraph()).getVisualizationViewer());
    }

    private void setUpFrame(VisualizationViewer<PetriNetVertex, Arc> visualizationViewer) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        getContentPane().add(visualizationViewer);
        pack();
    }
}
