package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetManager;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.gui.listener.PickListener;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PetriNetEditionMenu;
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

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);
        PetriNetManager petriNetManager = new PetriNetManager();

        PetriNetModalGraphMouse graphMouse = new PetriNetModalGraphMouse(petriNetManager);
        VisualizationViewer<PetriNetVertex, Arc> graphViewer = VisualizationViewerGenerator.PETRI_NET
                .generateVisualizationViewer(petriNetManager.getGraph(), graphMouse);

        PickedState<PetriNetVertex> vertexPickedState = graphViewer.getPickedVertexState();
        PickedState<Arc> arcPickedState = graphViewer.getPickedEdgeState();

        graphMouse.addItemListener(new ModeChangeListener(vertexPickedState, arcPickedState));

        PetriNetEditionMenu menu = new PetriNetEditionMenu(graphViewer);

        PickListener pickListener = new PickListener(vertexPickedState, arcPickedState, menu);
        vertexPickedState.addItemListener(pickListener);
        arcPickedState.addItemListener(pickListener);

        setUpFrame(graphViewer, menu);
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
}
