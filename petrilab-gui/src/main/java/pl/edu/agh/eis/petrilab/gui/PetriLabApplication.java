package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.jung.factory.ArcFactory;
import pl.edu.agh.eis.petrilab.gui.jung.factory.VertexFactory;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.gui.util.NameGenerator;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.SwingUtilities;

/**
 * Name: PetriLabApplication
 * Description: PetriLabApplication
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabApplication {
    public static final String TITLE = "PetriLab";
    private static PetriLabApplication INSTANCE;
    public static PetriLabApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PetriLabApplication();
        }
        return INSTANCE;
    }
    private PetriLabGui petriLabGui;
    private PetriNetGraph petriNetGraph = new PetriNetGraph();
    private PetriNetGraph simulationGraph;
    private VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private EditingModalGraphMouse<PetriNetVertex, Arc> graphMouse;
    private Configuration configuration = new Configuration();

    private void startGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                petriLabGui = new PetriLabGui();
                petriLabGui.setVisible(true);
            }
        });
    }

    private void initGraphViewer() {
        graphViewer = VisualizationViewerGenerator.PETRI_NET.generateVisualizationViewer(petriNetGraph);

        graphMouse = new EditingModalGraphMouse<>(graphViewer.getRenderContext(),
                new VertexFactory(), new ArcFactory());

        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

        graphViewer.setGraphMouse(graphMouse);
        graphViewer.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.addItemListener(
                new ModeChangeListener(graphViewer.getPickedVertexState(), graphViewer.getPickedEdgeState()));
    }

    public PetriNetGraph getPetriNetGraph() {
        return petriNetGraph;
    }

    public PetriNetGraph getSimulationGraph() {
        return simulationGraph;
    }

    public VisualizationViewer<PetriNetVertex, Arc> getGraphViewer() {
        return graphViewer;
    }

    public EditingModalGraphMouse<PetriNetVertex, Arc> getGraphMouse() {
        return graphMouse;
    }

    public void loadPetriNetGraph() {
        graphViewer.setGraphLayout(new CircleLayout<>(petriNetGraph));
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        NameGenerator.PLACE.setNameCounter(petriNetGraph.getPlaces().size() + 1);
        NameGenerator.TRANSITION.setNameCounter(petriNetGraph.getTransitions().size() + 1);
    }

    public void loadPetriNetGraph(PetriNetGraph graph) {
        petriNetGraph = graph;
        loadPetriNetGraph();
    }

    public void loadSimulationGraph() {
        simulationGraph = PetriNetGraph.fromMatrix(PetriNetMatrix.generateMatrix(petriNetGraph));
        graphViewer.setGraphLayout(new CircleLayout<>(simulationGraph));
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String... args) {
        PetriLabApplication.getInstance().initGraphViewer();
        PetriLabApplication.getInstance().startGui();
    }
}
