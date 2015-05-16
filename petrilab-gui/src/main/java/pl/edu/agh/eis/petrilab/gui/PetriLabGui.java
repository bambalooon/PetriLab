package pl.edu.agh.eis.petrilab.gui;

import com.google.common.collect.ImmutableList;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.jung.mouse.PetriNetModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.gui.listener.PickListener;
import pl.edu.agh.eis.petrilab.gui.menu.MenuPanel;
import pl.edu.agh.eis.petrilab.gui.menu.TabComponent;
import pl.edu.agh.eis.petrilab.gui.menu.action.Action;
import pl.edu.agh.eis.petrilab.gui.menu.action.ModeChangeAction;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PetriNetEditionPanel;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
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

    private final PetriNetModalGraphMouse graphMouse;
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private final MenuPanel menuPanel;

    public PetriLabGui() {
        super(PetriLabApplication.TITLE);

        graphMouse = new PetriNetModalGraphMouse();

        graphViewer = VisualizationViewerGenerator.PETRI_NET
                .generateVisualizationViewer(PetriLabApplication.getInstance().getPetriNetGraph(), graphMouse);

        graphMouse.addItemListener(
                new ModeChangeListener(graphViewer.getPickedVertexState(), graphViewer.getPickedEdgeState()));

        menuPanel = setUpMenu();
        setUpFrame();
    }

    private void setUpFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphViewer, menuPanel);
        splitPane.setResizeWeight(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        pack();
    }

    private MenuPanel setUpMenu() {
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
