package pl.edu.agh.eis.petrilab.gui.menu.simulation;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.api.CoverabilityGraph;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Name: SimulationPanel
 * Description: SimulationPanel
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class SimulationPanel extends JPanel implements ActionListener {
    private static final String GENERATE_COVERABILITY_GRAPH_BUTTON_LABEL = "Generuj graf pokrycia";
    private static final String GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION = "GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION";
    private static final String GENERATE_REACHABILITY_GRAPH_BUTTON_LABEL = "Generuj graf osiągalności";
    private static final String GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION = "GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION";
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_DEFAULT = 10;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_MIN = 1;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_MAX = Integer.MAX_VALUE;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_STEP = 5;

    private final JLabel markingLabel = new JLabel();
    private final JSpinner nodesLimit;

    public SimulationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Aktualne znakowanie:");
        JButton generateCoverabilityGraphButton = new JButton(GENERATE_COVERABILITY_GRAPH_BUTTON_LABEL);
        generateCoverabilityGraphButton.setActionCommand(GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION);
        generateCoverabilityGraphButton.addActionListener(this);
        JButton generateReachabilityGraphButton = new JButton(GENERATE_REACHABILITY_GRAPH_BUTTON_LABEL);
        nodesLimit = new JSpinner(new SpinnerNumberModel(
                REACHABILITY_GRAPH_NODES_LIMIT_DEFAULT,
                REACHABILITY_GRAPH_NODES_LIMIT_MIN,
                REACHABILITY_GRAPH_NODES_LIMIT_MAX,
                REACHABILITY_GRAPH_NODES_LIMIT_STEP));
        nodesLimit.setMaximumSize(nodesLimit.getPreferredSize());
        generateReachabilityGraphButton.setActionCommand(GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION);
        generateReachabilityGraphButton.addActionListener(this);
        add(label);
        add(markingLabel);
        add(nodesLimit);
        add(generateCoverabilityGraphButton);
        add(generateReachabilityGraphButton);
    }

    public void initMarking() {
        Integer[] marking = PetriLabApplication.getInstance().getSimulationGraph().getMarking();
        markingLabel.setText(Arrays.toString(marking));
    }

    public JLabel getMarkingLabel() {
        return markingLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final PetriNetMatrix petriNetMatrix = PetriNetMatrix
                .generateMatrix(PetriLabApplication.getInstance().getPetriNetGraph());
        ModalGraphMouse graphMouse = new DefaultModalGraphMouse<>();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        DirectedSparseGraph<Marking, Transition> graph;
        final VisualizationViewer<Marking, Transition> viewer;
        switch (e.getActionCommand()) {
            case GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION:
                graph = CoverabilityGraph.getCoverabilityGraph(petriNetMatrix);
                viewer = VisualizationViewerGenerator.COVERABILITY_GRAPH.generateVisualizationViewer(graph);
                viewer.setGraphMouse(graphMouse);
                JOptionPane.showMessageDialog(null, viewer);
                break;
            case GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION:
                graph = CoverabilityGraph.getReachabilityGraph(petriNetMatrix, (Integer) nodesLimit.getValue());
                viewer = VisualizationViewerGenerator.COVERABILITY_GRAPH.generateVisualizationViewer(graph);
                viewer.setGraphMouse(graphMouse);
                JOptionPane.showMessageDialog(null, viewer);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported action.");
        }
    }
}
