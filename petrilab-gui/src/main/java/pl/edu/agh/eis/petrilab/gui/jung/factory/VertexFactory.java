package pl.edu.agh.eis.petrilab.gui.jung.factory;

import org.apache.commons.collections15.Factory;
import pl.edu.agh.eis.petrilab.gui.Configuration;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.util.NameGenerator;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

import static pl.edu.agh.eis.petrilab.gui.Configuration.VERTEX_FACTORY_TYPE;

/**
 * Name: VertexFactory
 * Description: VertexFactory
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class VertexFactory implements Factory<PetriNetVertex> {
    private final Configuration configuration;

    public VertexFactory() {
        this(PetriLabApplication.getInstance().getConfiguration());
    }

    protected VertexFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public PetriNetVertex create() {
        Class<?> vertexClass = configuration.getProperty(VERTEX_FACTORY_TYPE);
        if (Place.class.equals(vertexClass)) {
            return new Place(NameGenerator.PLACE.getName());
        } else if (Transition.class.equals(vertexClass)) {
            return new Transition(NameGenerator.TRANSITION.getName());
        }
        throw new IllegalStateException("Property VERTEX_FACTORY_TYPE contains illegal value.");
    }
}
