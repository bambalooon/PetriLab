package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import pl.edu.agh.eis.petrilab.gui.jung.VisualizationViewerGenerator;
import pl.edu.agh.eis.petrilab.gui.jung.factory.ArcFactory;
import pl.edu.agh.eis.petrilab.gui.jung.factory.VertexFactory;
import pl.edu.agh.eis.petrilab.gui.listener.ModeChangeListener;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrixWithCoordinates;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraphInitializer;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;
import pl.edu.agh.eis.petrilab.model2.util.NameGenerator;

import javax.swing.*;

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
    private ApplicationModeManager modeManager = new ApplicationModeManager(this);
    private PetriLabGui petriLabGui;
    private PetriNetGraph petriNetGraph = new PetriNetGraph();
    private PetriNetGraphInitializer petriNetGraphInitializer;
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
        configuration.setProperty(Configuration.NAME_GENERATOR, new NameGenerator(petriNetGraph));

        graphViewer = VisualizationViewerGenerator.PETRI_NET.generateVisualizationViewer(petriNetGraph);

        graphMouse = new EditingModalGraphMouse<>(graphViewer.getRenderContext(),
                new VertexFactory(), new ArcFactory());

        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);

        graphViewer.setGraphMouse(graphMouse);
        graphViewer.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.addItemListener(
                new ModeChangeListener(graphViewer.getPickedVertexState(), graphViewer.getPickedEdgeState()));
    }

    public ApplicationModeManager getModeManager() {
        return modeManager;
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

    public PetriNetMatrixWithCoordinates generatePetriNetMatrixWithCoordinates() {
        petriNetGraphInitializer = PetriNetGraphInitializer
                .loadInitializer(graphViewer.getGraphLayout(), petriNetGraph);
        return PetriNetMatrixWithCoordinates.fromMatrix(
                PetriNetMatrix.generateMatrix(petriNetGraph),
                petriNetGraphInitializer);
    }

    public void loadPetriNetGraph() {
        if (petriNetGraphInitializer != null) {
            graphViewer.setGraphLayout(new StaticLayout<>(petriNetGraph, petriNetGraphInitializer));
        } else {
            graphViewer.setGraphLayout(new CircleLayout<>(petriNetGraph));
        }
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
    }

    public void loadPetriNetGraph(PetriNetGraph graph) {
        loadPetriNetGraph(graph, null);
    }

    public void loadPetriNetGraph(PetriNetGraph graph, PetriNetGraphInitializer initializer) {
        petriNetGraph = graph;
        petriNetGraphInitializer = initializer;
        configuration.setProperty(Configuration.NAME_GENERATOR, new NameGenerator(petriNetGraph));
        loadPetriNetGraph();
    }

    public void loadSimulationGraph() {
        simulationGraph = PetriNetGraph.fromMatrix(PetriNetMatrix.generateMatrix(petriNetGraph));
        petriNetGraphInitializer = PetriNetGraphInitializer
                .loadInitializer(graphViewer.getGraphLayout(), petriNetGraph);
        PetriNetGraphInitializer initializer = PetriNetGraphInitializer
                .loadInitializer(graphViewer.getGraphLayout(), petriNetGraph, simulationGraph);
        graphViewer.setGraphLayout(new StaticLayout<>(simulationGraph, initializer));
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
