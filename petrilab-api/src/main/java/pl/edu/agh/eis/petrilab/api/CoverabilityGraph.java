package pl.edu.agh.eis.petrilab.api;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.LinkedList;

import static pl.edu.agh.eis.petrilab.model2.matrix.Matrix.row;
import static pl.edu.agh.eis.petrilab.model2.matrix.Matrix.substract;

/**
 * Created by PW on 26-04-2015.
 */
public class CoverabilityGraph {
    public static DirectedSparseGraph<Marking, Transition> getCoverabilityGraph(PetriNetMatrix matrix)
            throws InterruptedException {
        DirectedSparseGraph<Marking, Transition> graph = new DirectedSparseGraph<>();
        LinkedList<Marking> markingQueue = Lists.newLinkedList();
        Marking m0 = new Marking(matrix.getMarkingVector());
        graph.addVertex(m0);
        markingQueue.add(m0);

        int[][] incidenceMatrix = substract(matrix.getInMatrix(), matrix.getOutMatrix());
        int transitionNumber = incidenceMatrix.length;

        while (!markingQueue.isEmpty()) {
            Marking marking = markingQueue.pop();

            for (int i = 0; i < transitionNumber; i++) {
                if (matrix.isTransitionActive(false, i, marking.getValue())) {
                    Marking newMarking = new Marking(marking.getValue(), row(incidenceMatrix, i));

                    if (graph.addVertex(newMarking)) {
                        markingQueue.add(newMarking);
                    } else {
                        final Marking duplicatedMarking = newMarking;
                        newMarking = FluentIterable.from(graph.getVertices())
                                .firstMatch(new Predicate<Marking>() {
                                    @Override
                                    public boolean apply(Marking marking) {
                                        return duplicatedMarking.equals(marking);
                                    }
                                }).get();
                    }

                    if (graph.findEdge(marking, newMarking) == null) {
                        Transition transition = new Transition(matrix.getTransitionsNames()[i]);
                        graph.addEdge(transition, marking, newMarking);
                    }

                    UnweightedShortestPath<Marking, Transition> shortestPath = new UnweightedShortestPath<>(graph);
                    for (Marking srcMarking : graph.getVertices()) {
                        if (srcMarking != newMarking
                                && shortestPath.getDistance(srcMarking, newMarking) != null
                                && Marking.leq(srcMarking, newMarking)
                                && Marking.existLesser(srcMarking, newMarking)) {

                            newMarking.updateMarking(srcMarking);
                            break;
                        }
                    }
                }
            }
            Thread.sleep(100);
        }
        return graph;
    }

    public static DirectedSparseGraph<Marking, Transition> getReachabilityGraph(PetriNetMatrix matrix, int nodesLimit) {
        DirectedSparseGraph<Marking, Transition> graph = new DirectedSparseGraph<>();
        LinkedList<Marking> markingQueue = Lists.newLinkedList();
        Marking m0 = new Marking(matrix.getMarkingVector());
        graph.addVertex(m0);
        markingQueue.add(m0);

        int[][] incidenceMatrix = substract(matrix.getInMatrix(), matrix.getOutMatrix());
        int transitionNumber = incidenceMatrix.length;

        while (!markingQueue.isEmpty() && graph.getVertexCount() < nodesLimit) {
            Marking marking = markingQueue.pop();

            for (int i = 0; i < transitionNumber; i++) {
                if (matrix.isTransitionActive(true, i, marking.getValue())) {
                    Marking newMarking = new Marking(marking.getValue(), row(incidenceMatrix, i));

                    if (graph.addVertex(newMarking)) {
                        markingQueue.add(newMarking);
                        final Marking duplicatedMarking = newMarking;
                        newMarking = FluentIterable.from(graph.getVertices())
                                .firstMatch(new Predicate<Marking>() {
                                    @Override
                                    public boolean apply(Marking marking) {
                                        return duplicatedMarking.equals(marking);
                                    }
                                }).get();
                    }
                    if (graph.findEdge(marking, newMarking) == null) {
                        Transition transition = new Transition(matrix.getTransitionsNames()[i]);
                        graph.addEdge(transition, marking, newMarking);
                    }
                }
            }
        }

        return graph;
    }

}
