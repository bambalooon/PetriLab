package pl.edu.agh.eis.petrilab.gui;

import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.jung.paintable.ActiveTransitionsBoundingRectanglePaintable;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import java.util.Observable;

import static pl.edu.agh.eis.petrilab.gui.Configuration.SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE;
import static pl.edu.agh.eis.petrilab.gui.Configuration.SIMULATION_MODE_ACTIVE;

/**
 * Name: ApplicationModeManager
 * Description: ApplicationModeManager
 * Date: 2015-05-24
 * Created by BamBalooon
 */
public class ApplicationModeManager extends Observable {
    private final PetriLabApplication application;
    private ApplicationMode currentMode = ApplicationMode.NORMAL;

    public ApplicationModeManager(PetriLabApplication application) {
        this.application = application;
    }

    public ApplicationMode getCurrentMode() {
        return currentMode;
    }

    public void setNormalMode() {
        if (currentMode != ApplicationMode.NORMAL) {
            currentMode = ApplicationMode.NORMAL;
            stopSimulationMode();
            setChanged();
            notifyObservers();
        }
    }

    public void setSimulationMode() {
        if (currentMode != ApplicationMode.SIMULATION) {
            currentMode = ApplicationMode.SIMULATION;
            startSimulationMode();
            setChanged();
            notifyObservers();
        }
    }

    private void startSimulationMode() {
        Configuration configuration = application.getConfiguration();
        if (configuration.getProperty(SIMULATION_MODE_ACTIVE) != Boolean.TRUE) {
            application.loadSimulationGraph();
            configuration.setProperty(SIMULATION_MODE_ACTIVE, true);
            VisualizationViewer<PetriNetVertex, Arc> graphViewer = application.getGraphViewer();
            VisualizationServer.Paintable activeTransitionPreRenderPaintable =
                    new ActiveTransitionsBoundingRectanglePaintable(
                            graphViewer.getRenderContext(),
                            graphViewer.getGraphLayout());
            configuration.setProperty(
                    SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE, activeTransitionPreRenderPaintable);
            graphViewer.addPreRenderPaintable(activeTransitionPreRenderPaintable);
            graphViewer.repaint();
        }
    }

    private void stopSimulationMode() {
        Configuration configuration = application.getConfiguration();
        if (configuration.getProperty(SIMULATION_MODE_ACTIVE) == Boolean.TRUE) {
            application.loadPetriNetGraph();
            configuration.setProperty(SIMULATION_MODE_ACTIVE, false);
            VisualizationViewer<PetriNetVertex, Arc> graphViewer = application.getGraphViewer();
            VisualizationServer.Paintable activeTransitionPreRenderPaintable = configuration
                    .removeProperty(SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE);
            graphViewer.removePreRenderPaintable(activeTransitionPreRenderPaintable);
            graphViewer.repaint();
        }
    }
}
