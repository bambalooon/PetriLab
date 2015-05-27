package pl.edu.agh.eis.petrilab.gui;

import com.google.common.collect.ImmutableList;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.listener.EditionPickListener;
import pl.edu.agh.eis.petrilab.gui.listener.ModalPickListener;
import pl.edu.agh.eis.petrilab.gui.listener.SimulationPickListener;
import pl.edu.agh.eis.petrilab.gui.menu.MenuPanel;
import pl.edu.agh.eis.petrilab.gui.menu.TabComponent;
import pl.edu.agh.eis.petrilab.gui.menu.action.Action;
import pl.edu.agh.eis.petrilab.gui.menu.action.ActionGroup;
import pl.edu.agh.eis.petrilab.gui.menu.action.ModeChangeAction;
import pl.edu.agh.eis.petrilab.gui.menu.action.StartSimulationModeAction;
import pl.edu.agh.eis.petrilab.gui.menu.action.StopSimulationModeAction;
import pl.edu.agh.eis.petrilab.gui.menu.analysis.AnalysisPanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.EditionPanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.MainPanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.ModePanel;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PickingPanel;
import pl.edu.agh.eis.petrilab.gui.menu.file.FilePanel;
import pl.edu.agh.eis.petrilab.gui.menu.simulation.SimulationPanel;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
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

    private Component menuPanel;
    private JPanel mainPanel;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);

        VisualizationViewer<PetriNetVertex, Arc> graphViewer = PetriLabApplication.getInstance().getGraphViewer();
        EditingModalGraphMouse<PetriNetVertex, Arc> graphMouse = PetriLabApplication.getInstance().getGraphMouse();

        setUpMenu(graphViewer, graphMouse);
        setUpMainPanel(graphViewer);
        setUpFrame();
    }

    private void setUpMenu(VisualizationViewer<PetriNetVertex, Arc> graphViewer, AbstractModalGraphMouse graphMouse) {
        PickingPanel pickingPanel = new PickingPanel(graphViewer);
        MainPanel mainPanel = new MainPanel(new ModePanel(graphMouse), new EditionPanel(), pickingPanel);
        graphMouse.addItemListener(mainPanel);

        SimulationPanel simulationPanel = new SimulationPanel();

        menuPanel = new MenuPanel(ImmutableList.<TabComponent>builder()
                .add(new TabComponent<>(ApplicationMode.NORMAL, "Edycja", mainPanel,
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING), Action.NO_ACTION))
                .add(new TabComponent<>(ApplicationMode.NORMAL, "Analiza", new AnalysisPanel(),
                        new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING), Action.NO_ACTION))
                .add(new TabComponent<>(ApplicationMode.SIMULATION, "Symulacja", simulationPanel,
                        new ActionGroup(
                                new ModeChangeAction(graphMouse, ModalGraphMouse.Mode.PICKING),
                                new StartSimulationModeAction()
                        ), new StopSimulationModeAction()))
                .build());

        PickedState<PetriNetVertex> vertexPickedState = graphViewer.getPickedVertexState();
        PickedState<Arc> arcPickedState = graphViewer.getPickedEdgeState();

        ModalPickListener pickListener = new ModalPickListener(
                new EditionPickListener(vertexPickedState, arcPickedState, pickingPanel),
                new SimulationPickListener(graphViewer, vertexPickedState, simulationPanel)
        );

        vertexPickedState.addItemListener(pickListener);
        arcPickedState.addItemListener(pickListener);
    }

    private void setUpMainPanel(Component graphViewer) {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new FilePanel());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphViewer, menuPanel);
        splitPane.setResizeWeight(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolBar, BorderLayout.PAGE_START);
        mainPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void setUpFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
    }
}
