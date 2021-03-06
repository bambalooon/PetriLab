package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.menu.simulation.SimulationPanel;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import javax.swing.JCheckBox;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Name: SimulationPlayer
 * Description: SimulationPlayer
 * Date: 2015-05-24
 * Created by BamBalooon
 */
public class SimulationPlayer implements Observer {
    private static final Random RANDOM_GENERATOR = new Random();

    private final SimulationPanel simulationPanel;
    private final JCheckBox autoSimulationCheckBox;
    private Simulation simulation;
    private Integer[] initialMarking;

    public SimulationPlayer(SimulationPanel simulationPanel, JCheckBox checkBox) {
        PetriLabApplication.getInstance().getModeManager().addObserver(this);
        this.simulationPanel = simulationPanel;
        this.autoSimulationCheckBox = checkBox;
    }

    public void play() {
        if (simulation == null) {
            simulation = new Simulation(
                    PetriLabApplication.getInstance().getGraphViewer(),
                    PetriLabApplication.getInstance().getSimulationGraph());

            new Thread(simulation).start();
        }
    }

    public void stop() {
        if (simulation != null) {
            simulation.stop();
            simulation = null;
        }
        PetriLabApplication.getInstance().getSimulationGraph().setMarking(initialMarking);
        PetriLabApplication.getInstance().getGraphViewer().repaint();
        simulationPanel.updateMarking();
    }

    public void pause() {
        if (simulation != null) {
            simulation.stop();
            simulation = null;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        ApplicationMode currentMode = PetriLabApplication.getInstance().getModeManager().getCurrentMode();
        switch (currentMode) {
            case NORMAL:
                if (simulation != null) {
                    simulation.stop();
                    simulation = null;
                }
                break;
            case SIMULATION:
                initialMarking = PetriLabApplication.getInstance().getPetriNetGraph().getMarking();
                break;
        }
    }

    private class Simulation implements Runnable {
        private static final long DEFAULT_INTERVAL = 1000;
        private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
        private final PetriNetGraph simulationGraph;
        private boolean isPlaying = true;
        private long interval = DEFAULT_INTERVAL;

        private Simulation(VisualizationViewer<PetriNetVertex, Arc> graphViewer, PetriNetGraph simulationGraph) {
            this.graphViewer = graphViewer;
            this.simulationGraph = simulationGraph;
        }

        @Override
        public void run() {
            while (isPlaying) {
                List<Transition> activeTransitions = simulationGraph.getActiveTransitions();
                int activeTransitionsCount = activeTransitions.size();
                switch (activeTransitionsCount) {
                    case 0:
                        stop();
                        break;
                    case 1:
                        simulationGraph.fireTransition(activeTransitions.get(0));
                        simulationPanel.updateMarking();
                        graphViewer.repaint();
                        delay();
                        break;
                    default:
                        if (autoSimulationCheckBox.isSelected()) {
                            simulationGraph.fireTransition(
                                    activeTransitions.get(RANDOM_GENERATOR.nextInt(activeTransitionsCount)));
                            simulationPanel.updateMarking();
                            graphViewer.repaint();
                        }
                        delay();
                }
            }
        }

        public void stop() {
            isPlaying = false;
        }

        private void delay() {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {}
        }
    }
}
