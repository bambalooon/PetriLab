package pl.edu.agh.eis.petrilab.gui.menu.analysis;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.api.CoverabilityGraph;
import pl.edu.agh.eis.petrilab.api.Properties;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

import static pl.edu.agh.eis.petrilab.gui.menu.analysis.SingleComponentFrame.*;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.COMPONENT_DEFAULT_SIZE;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.createTextButton;

/**
 * Name: AnalyzePanel
 * Description: AnalyzePanel
 * Date: 2015-05-24
 * Created by BamBalooon
 */
public class AnalysisPanel extends JPanel implements ActionListener {
    private static final String GENERATE_COVERABILITY_GRAPH_BUTTON_LABEL = "Graf pokrycia";
    private static final String GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION = "GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION";
    private static final String GENERATE_REACHABILITY_GRAPH_BUTTON_LABEL = "Graf osiągalności";
    private static final String GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION = "GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION";
    private static final String GENERATE_MATRIX_BUTTON_LABEL = "Macierz";
    private static final String GENERATE_MATRIX_BUTTON_ACTION = "GENERATE_MATRIX_BUTTON_ACTION";
    private static final String GENERATE_PROPERTIES_BUTTON_LABEL = "Własności";
    private static final String GENERATE_PROPERTIES_BUTTON_ACTION = "GENERATE_PROPERTIES_BUTTON_ACTION";
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_DEFAULT = 10;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_MIN = 1;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_MAX = Integer.MAX_VALUE;
    private static final int REACHABILITY_GRAPH_NODES_LIMIT_STEP = 5;

    private final JSpinner nodesLimit;

    public AnalysisPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JButton generateCoverabilityGraphButton = createTextButton(
                this, GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION, GENERATE_COVERABILITY_GRAPH_BUTTON_LABEL);
        add(generateCoverabilityGraphButton, gbc);

        JLabel nodesLimitDesc = new JLabel("Limit węzłów:");
        add(nodesLimitDesc, gbc);

        nodesLimit = new JSpinner(new SpinnerNumberModel(
                REACHABILITY_GRAPH_NODES_LIMIT_DEFAULT,
                REACHABILITY_GRAPH_NODES_LIMIT_MIN,
                REACHABILITY_GRAPH_NODES_LIMIT_MAX,
                REACHABILITY_GRAPH_NODES_LIMIT_STEP));
        nodesLimit.setPreferredSize(COMPONENT_DEFAULT_SIZE);
        add(nodesLimit, gbc);

        JButton generateReachabilityGraphButton = createTextButton(
                this, GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION, GENERATE_REACHABILITY_GRAPH_BUTTON_LABEL);
        add(generateReachabilityGraphButton, gbc);

        JButton generatePetriNetMatrix = createTextButton(
                this, GENERATE_MATRIX_BUTTON_ACTION, GENERATE_MATRIX_BUTTON_LABEL);
        add(generatePetriNetMatrix, gbc);

        JButton generatePetriNetProperties = createTextButton(
                this, GENERATE_PROPERTIES_BUTTON_ACTION, GENERATE_PROPERTIES_BUTTON_LABEL);
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        add(generatePetriNetProperties, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PetriNetGraph petriNetGraph = PetriLabApplication.getInstance().getPetriNetGraph();
        final PetriNetMatrix petriNetMatrix = PetriNetMatrix
                .generateMatrix(petriNetGraph);
        ModalGraphMouse graphMouse = new DefaultModalGraphMouse<>();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        DirectedSparseGraph<Marking, Transition> graph;
        final VisualizationViewer<Marking, Transition> viewer;
        switch (e.getActionCommand()) {
            case GENERATE_COVERABILITY_GRAPH_BUTTON_ACTION:
                graph = CoverabilityGraph.getCoverabilityGraph(petriNetMatrix);
                viewer = VisualizationViewerGenerator.GRAPH.generateVisualizationViewer(graph);
                setCustomVertexLabelTransformer(viewer, petriNetMatrix);
                setVertexPickListener(viewer, petriNetMatrix);
                viewer.setGraphMouse(graphMouse);
                new SingleComponentFrame(COVERABILITY_GRAPH_TITLE, viewer);
                break;
            case GENERATE_REACHABILITY_GRAPH_BUTTON_ACTION:
                graph = CoverabilityGraph.getReachabilityGraph(petriNetMatrix, (Integer) nodesLimit.getValue());
                viewer = VisualizationViewerGenerator.GRAPH.generateVisualizationViewer(graph);
                viewer.setGraphMouse(graphMouse);
                new SingleComponentFrame(REACHABILITY_GRAPH_TITLE, viewer);
                break;
            case GENERATE_MATRIX_BUTTON_ACTION:
                new SingleComponentFrame(MATRIX_TITLE, new MatrixPanel(petriNetMatrix));
                break;
            case GENERATE_PROPERTIES_BUTTON_ACTION:
                JOptionPane.showMessageDialog(null,
                        generatePropertiesRaport(
                                petriNetGraph,
                                petriNetMatrix,
                                CoverabilityGraph.getCoverabilityGraph(petriNetMatrix)),
                        "Własności sieci petriego", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported action.");
        }
    }

    private void setCustomVertexLabelTransformer(VisualizationViewer<Marking, Transition> viewer,
                                                 PetriNetMatrix matrix) {

        final int[] capacityVector = matrix.getCapacityVector();
        viewer.getRenderContext().setVertexLabelTransformer(new Transformer<Marking, String>() {
            @Override
            public String transform(Marking marking) {
                boolean hasInfiniteMarkingExceedingCapacity =
                        getIndexesOfInfiniteMarkingsExceedingCapacity(marking.getValue(), capacityVector).size() > 0;

                return hasInfiniteMarkingExceedingCapacity
                        ? marking.toString() + "(!)"
                        : marking.toString();
            }
        });
    }

    private void setVertexPickListener(VisualizationViewer<Marking, Transition> viewer, final PetriNetMatrix matrix) {
        viewer.getPickedVertexState().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() instanceof Marking) {
                    Marking marking = (Marking) e.getItem();
                    List<Integer> indexes = getIndexesOfInfiniteMarkingsExceedingCapacity(
                            marking.getValue(), matrix.getCapacityVector());
                    if (indexes.size() > 0) {
                        StringBuilder strBuilder = new StringBuilder();
                        strBuilder.append("Poniższe miejsca ograniczone są pojemnością:\n");
                        for (Integer index : indexes) {
                            strBuilder
                                    .append(matrix.getPlacesNames()[index])
                                    .append(" - pojemność: ")
                                    .append(matrix.getCapacityVector()[index])
                                    .append("\n");
                        }
                        JOptionPane
                                .showMessageDialog(null, strBuilder.toString(), "Uwaga!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    private List<Integer> getIndexesOfInfiniteMarkingsExceedingCapacity(Double[] markingVector, int[] capacityVector) {
        List<Integer> indexes = Lists.newArrayList();
        int i = 0;
        for (Double marking : markingVector) {
            if (marking.isInfinite() && capacityVector[i] != 0) {
                indexes.add(i);
            }
            i++;
        }
        return indexes;
    }

    private String generatePropertiesRaport(PetriNetGraph petriNetGraph,
                                            PetriNetMatrix petriNetMatrix,
                                            DirectedSparseGraph<Marking, Transition> coverabilityGraph) {

        StringBuilder raportBuilder = new StringBuilder();

        raportBuilder.append(Properties.isNetAlive(petriNetMatrix) ?
                "Sieć jest żywa."
                : Properties.isNetPotentiallyAlive(coverabilityGraph, petriNetMatrix)
                        ? "Sieć jest potencjalnie żywa."
                        : "Sieć jest martwa.");
        raportBuilder.append('\n');

        raportBuilder.append(Properties.isNetConservative(coverabilityGraph)
                ? "Sieć jest zachowawcza." : "Sieć nie jest zachowawcza.");
        raportBuilder.append('\n');

        raportBuilder.append(Properties.isNetSafe(coverabilityGraph, petriNetMatrix)
                ? "Sieć jest bezpieczna." : "Sieć nie jest bezpieczna.");
        raportBuilder.append('\n');

        Double netBoundary = Properties.getNetBoundary(coverabilityGraph, petriNetMatrix);
        raportBuilder.append(netBoundary.isInfinite()
                ? "Sieć nie jest ograniczona"
                : "Sieć jest " + netBoundary.intValue() + "-ograniczona.");
        raportBuilder.append('\n');

        raportBuilder.append(Properties.isNetReversible(coverabilityGraph, petriNetMatrix)
                ? "Sieć jest odwracalna." : "Sieć nie jest odwracalna.");
        raportBuilder.append('\n');

        String[] placesNames = petriNetMatrix.getPlacesNames();
        Map<String, Double> placesBoundaries = Maps.newLinkedHashMap();
        List<String> unboundedPlaces = Lists.newArrayList();
        for (int placeIndex = 0; placeIndex < petriNetMatrix.getPlacesNames().length; placeIndex++) {
            Double placeBoundary = Properties.getPlaceBoundary(placeIndex, coverabilityGraph, petriNetMatrix);
            if (placeBoundary.isInfinite()) {
                unboundedPlaces.add(placesNames[placeIndex]);
            } else {
                placesBoundaries.put(placesNames[placeIndex], placeBoundary);
            }
        }
        raportBuilder.append("Miejsca ograniczone: ");
        raportBuilder.append(placesBoundaries.isEmpty()
                ? "brak"
                : FluentIterable
                        .from(placesBoundaries.entrySet())
                        .transform(new Function<Map.Entry<String, Double>, String>() {
                            @Override
                            public String apply(Map.Entry<String, Double> placeBoundary) {
                                return placeBoundary.getKey() + "(" + placeBoundary.getValue().intValue() + ")";
                            }
                        }).join(Joiner.on(", ")));
        raportBuilder.append('\n');

        raportBuilder.append("Miejsca nieograniczone: ");
        raportBuilder.append(unboundedPlaces.isEmpty()
                ? "brak"
                : FluentIterable.from(unboundedPlaces).join(Joiner.on(", ")));
        raportBuilder.append('\n');

        List<Transition> aliveTransitions = Lists.newArrayList();
        List<Transition> potentiallyAliveTransitions = Lists.newArrayList();
        List<Transition> deadTransitions = Lists.newArrayList();
        for (Transition transition : petriNetGraph.getTransitions()) {
            if (Properties.isTransitionAlive(transition, petriNetMatrix)) {
                aliveTransitions.add(transition);
            } else if (Properties.isTransitionPotentiallyAlive(transition, coverabilityGraph)) {
                potentiallyAliveTransitions.add(transition);
            } else {
                deadTransitions.add(transition);
            }
        }
        raportBuilder.append("Przejścia żywe: ");
        raportBuilder.append(aliveTransitions.isEmpty()
                ? "brak"
                : FluentIterable.from(aliveTransitions).join(Joiner.on(", ")));
        raportBuilder.append('\n');

        raportBuilder.append("Przejścia potencjalnie żywe: ");
        raportBuilder.append(potentiallyAliveTransitions.isEmpty()
                ? "brak"
                : FluentIterable.from(potentiallyAliveTransitions).join(Joiner.on(", ")));
        raportBuilder.append('\n');

        raportBuilder.append("Przejścia martwe: ");
        raportBuilder.append(deadTransitions.isEmpty()
                ? "brak"
                : FluentIterable.from(deadTransitions).join(Joiner.on(", ")));
        raportBuilder.append('\n');

        return raportBuilder.toString();
    }
}