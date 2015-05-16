package pl.edu.agh.eis.petrilab.gui;

import com.google.common.collect.ImmutableList;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetManager;
import pl.edu.agh.eis.petrilab.gui.jung.PetriNetModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.gui.listener.PickListener;
import pl.edu.agh.eis.petrilab.gui.menu.MenuPanel;
import pl.edu.agh.eis.petrilab.gui.menu.TabComponent;
import pl.edu.agh.eis.petrilab.gui.menu.action.*;
import pl.edu.agh.eis.petrilab.gui.menu.action.Action;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PetriNetEditionPanel;
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

        setUpFrame(graphViewer, setUpMenu(graphMouse, graphViewer));
    }

    private void setUpFrame(VisualizationViewer<PetriNetVertex, Arc> graphViewer, Component menu) {
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

    private Component setUpMenu(PetriNetModalGraphMouse graphMouse,
                                VisualizationViewer<PetriNetVertex, Arc> graphViewer) {

        PetriNetEditionPanel petriNetEditionPanel = new PetriNetEditionPanel(graphMouse, graphViewer);
        MenuPanel menuPanel = new MenuPanel(ImmutableList.<TabComponent>builder()
                .add(new TabComponent<>("Edit", petriNetEditionPanel,
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING), Action.NO_ACTION))
                .add(new TabComponent<>("Simulation", new JPanel(),
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.ANNOTATING), Action.NO_ACTION))
                .build());

        PickedState<PetriNetVertex> vertexPickedState = graphViewer.getPickedVertexState();
        PickedState<Arc> arcPickedState = graphViewer.getPickedEdgeState();

        PickListener pickListener = new PickListener(vertexPickedState, arcPickedState, petriNetEditionPanel);
        vertexPickedState.addItemListener(pickListener);
        arcPickedState.addItemListener(pickListener);
        return menuPanel;
    }
}
