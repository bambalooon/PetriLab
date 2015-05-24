package pl.edu.agh.eis.petrilab.model2.jung;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import edu.uci.ics.jung.algorithms.layout.Layout;
import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.Map;

/**
 * Name: PetriNetGraphInitializer
 * Description: PetriNetGraphInitializer
 * Date: 2015-05-23
 * Created by BamBalooon
 */
public class PetriNetGraphInitializer implements Transformer<PetriNetVertex, Point2D> {
    private final Map<PetriNetVertex, Point2D> coordinates;

    public PetriNetGraphInitializer() {
        this(Collections.<PetriNetVertex, Point2D>emptyMap());
    }

    public PetriNetGraphInitializer(Map<PetriNetVertex, Point2D> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Point2D transform(PetriNetVertex petriNetVertex) {
        Point2D vertexCoordinates = coordinates.get(petriNetVertex);
        return vertexCoordinates == null
                ? new Point2D.Double(0, 0)
                : vertexCoordinates;
    }

    public static PetriNetGraphInitializer loadInitializer(final Map<String, Point2D> placesCoordinates,
                                                           final Map<String, Point2D> transitionsCoordinates,
                                                           PetriNetGraph graph) {
        Map<PetriNetVertex, Point2D> verticesCoordinatesMap = FluentIterable
                .from(graph.getVertices())
                .toMap(new Function<PetriNetVertex, Point2D>() {
                    @Override
                    public Point2D apply(PetriNetVertex vertex) {
                        return vertex instanceof Place
                                ? placesCoordinates.get(vertex.getName())
                                : transitionsCoordinates.get(vertex.getName());
                    }
                });

        return new PetriNetGraphInitializer(verticesCoordinatesMap);
    }

    public static PetriNetGraphInitializer loadInitializer(final Layout<PetriNetVertex, Arc> graphLayout,
                                                           final PetriNetGraph inGraph,
                                                           PetriNetGraph outGraph) {
        Map<PetriNetVertex, Point2D> verticesCoordinatesMap = FluentIterable
                .from(outGraph.getVertices())
                .toMap(new Function<PetriNetVertex, Point2D>() {
                    @Override
                    public Point2D apply(final PetriNetVertex outVertex) {
                        return FluentIterable.from(inGraph.getVertices())
                                .firstMatch(new Predicate<PetriNetVertex>() {
                                    @Override
                                    public boolean apply(PetriNetVertex inVertex) {
                                        return outVertex.getClass().equals(inVertex.getClass())
                                                && outVertex.getName().equals(inVertex.getName());
                                    }
                                }).transform(new Function<PetriNetVertex, Point2D>() {
                                    @Override
                                    public Point2D apply(PetriNetVertex inVertex) {
                                        return graphLayout.transform(inVertex);
                                    }
                                }).get();
                    }
                });

        return new PetriNetGraphInitializer(verticesCoordinatesMap);
    }

    public static PetriNetGraphInitializer loadInitializer(final Layout<PetriNetVertex, Arc> graphLayout,
                                                           final PetriNetGraph graph) {
        Map<PetriNetVertex, Point2D> verticesCoordinatesMap = FluentIterable
                .from(graph.getVertices())
                .toMap(new Function<PetriNetVertex, Point2D>() {
                    @Override
                    public Point2D apply(final PetriNetVertex vertex) {
                        return graphLayout.transform(vertex);
                    }
                });

        return new PetriNetGraphInitializer(verticesCoordinatesMap);
    }
}
