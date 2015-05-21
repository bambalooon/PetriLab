package pl.edu.agh.eis.petrilab.gui;

import com.google.common.collect.ImmutableList;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.jung.factory.ArcFactory;
import pl.edu.agh.eis.petrilab.gui.jung.factory.VertexFactory;
import pl.edu.agh.eis.petrilab.gui.listener.ModalPickListener;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.gui.listener.EditionPickListener;
import pl.edu.agh.eis.petrilab.gui.listener.SimulationPickListener;
import pl.edu.agh.eis.petrilab.gui.menu.MenuPanel;
import pl.edu.agh.eis.petrilab.gui.menu.TabComponent;
import pl.edu.agh.eis.petrilab.gui.menu.action.Action;
import pl.edu.agh.eis.petrilab.gui.menu.action.ActionGroup;
import pl.edu.agh.eis.petrilab.gui.menu.action.ModeChangeAction;
import pl.edu.agh.eis.petrilab.gui.menu.action.StartSimulationModeAction;
import pl.edu.agh.eis.petrilab.gui.menu.action.StopSimulationModeAction;
import pl.edu.agh.eis.petrilab.gui.menu.edition.EditionPanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.MainPanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.ModePanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PickingPanel;
import pl.edu.agh.eis.petrilab.gui.menu.file.FilePanel;
import pl.edu.agh.eis.petrilab.gui.menu.simulation.SimulationPanel;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Name: PetriLabGui
 * Description: PetriLabGui
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabGui extends JFrame {
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 600;

    private AbstractModalGraphMouse graphMouse;
    private VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private Component menuPanel;
    private final JSplitPane mainPanel;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);

        setUpGraphViewer();
        setUpMenu();
        mainPanel = setUpMainPanel();
        setUpFrame();
    }

    private void setUpGraphViewer() {
        graphViewer = VisualizationViewerGenerator.PETRI_NET
                .generateVisualizationViewer(PetriLabApplication.getInstance().getPetriNetGraph());

        graphMouse = new EditingModalGraphMouse<>(graphViewer.getRenderContext(),
                new VertexFactory(), new ArcFactory());

        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

        graphViewer.setGraphMouse(graphMouse);
        graphViewer.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.addItemListener(
                new ModeChangeListener(graphViewer.getPickedVertexState(), graphViewer.getPickedEdgeState()));
    }

    private void setUpMenu() {
        PickingPanel pickingPanel = new PickingPanel(graphMouse, graphViewer);
        MainPanel mainPanel = new MainPanel(new ModePanel(graphMouse), new EditionPanel(), pickingPanel);
        graphMouse.addItemListener(mainPanel);

        menuPanel = new MenuPanel(ImmutableList.<TabComponent>builder()
                .add(new TabComponent<>("File", new FilePanel(),
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING), Action.NO_ACTION))
                .add(new TabComponent<>("Edit", mainPanel,
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING), Action.NO_ACTION))
                .add(new TabComponent<>("Simulation", new SimulationPanel(),
                        new ActionGroup(
                                new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING),
                                new StartSimulationModeAction(graphViewer)
                        ), new StopSimulationModeAction(graphViewer)))
                .build());

        PickedState<PetriNetVertex> vertexPickedState = graphViewer.getPickedVertexState();
        PickedState<Arc> arcPickedState = graphViewer.getPickedEdgeState();

        ModalPickListener pickListener = new ModalPickListener(
                new EditionPickListener(vertexPickedState, arcPickedState, pickingPanel),
                new SimulationPickListener(graphViewer, vertexPickedState)
        );

        vertexPickedState.addItemListener(pickListener);
        arcPickedState.addItemListener(pickListener);
    }

    private JSplitPane setUpMainPanel() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphViewer, menuPanel);
        splitPane.setResizeWeight(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        return splitPane;
    }

    private void setUpFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    public void loadPetriNetGraph() {
        graphViewer.setGraphLayout(new CircleLayout<>(PetriLabApplication.getInstance().getPetriNetGraph()));
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
    }

    public void loadSimulationGraph() {
        graphViewer.setGraphLayout(new CircleLayout<>(PetriLabApplication.getInstance().getSimulationGraph()));
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
    }
}
