package pl.edu.agh.eis.petrilab.api;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model.PetriNet;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.Iterator;

/**
 * Created by PW on 26-04-2015.
 */
public class CoverabilityGraph {

    public DirectedSparseGraph Generate(PetriNetMatrix net) {
        DirectedSparseGraph<int[], String> graph = new DirectedSparseGraph<>();
        int[][] incidenceMatrix = net.add(false,net.getInMatrix(),net.getOutMatrix());
        int transitionNumber = incidenceMatrix[0].length;
        int placesNumber = incidenceMatrix.length;

        Iterator graphItr = graph.getVertices().iterator();

        int[] m0 = net.getMarkingVector();
        int[] newVertex;
        graph.addVertex(m0); //dodaj wezel poczatkowy o znakowaniu M0
        newVertex = m0; //oznacz go jako nowy
        boolean newVertexExists = true;

        if(net.getActiveTransitions(newVertex).isEmpty()) {
            return graph;
        }

        //int[] newVertex = net.getMarkingVector();
        int[] marking1 = new int[placesNumber];

        while(newVertexExists) { //dopoki istnieje wezel oznaczony jako nowy
            if (net.getActiveTransitions(newVertex).isEmpty()) { //jezeli dla znakowania M nie istnieje aktywne przejscie sialalala
                newVertexExists = false;
            }
            else { //w przeciwnym wypadku
                for (int i = 0; i < transitionNumber; i++) {
                    if (net.isTransitionActive(i, newVertex)) { //dla kazdego przejscia aktywnego wykonaj
                        marking1 = net.add(true, newVertex, net.col(incidenceMatrix, i)); //oblicz znakowanie M' gdzie M-t-M'
                        while(graphItr.hasNext()) { //jezeli w grafie istnieje znakowanie
                            int[] marking11 = (int[]) graphItr.next(); //M''
                            for(int j = 0; i < transitionNumber; i++) {
                                boolean condition1 = net.add(true, marking11, net.col(incidenceMatrix, i)) == marking1; //takie ze M' jest osiagalne z M''
                                boolean condition2 = true; //oraz dla kazdego p M''(p) <= M'(p)
                                boolean condition3 = false; //oraz istnieje p' takie ze M''(p') < M'(p')
                                for(int k = 0; k < placesNumber; k++) {
                                    if (marking11[k] > marking1[k]) {
                                        condition2 = false;
                                        break;
                                    }
                                    if(marking11[k] < marking1[k])
                                        condition3 = true;
                                }
                                if(condition1 && condition2 && condition3) {
                                    int[] inf = net.multiply(net.add(false, marking1,marking11),-1); //to M' = M' + ((M'-M'')*nieskonczonosc)
                                    marking1 = net.add(true,marking1,inf);
                                    break;
                                }
                            }
                        }
                        if(!graph.containsVertex(marking1)) { //jezeli wezel M' nie nalezy do grafu
                            graph.addVertex(marking1); //to dodaj go
                            graph.addEdge(net.getTransitionsNames()[i], newVertex, marking1); //dodaj luk z wezla M do wezla M'
                            newVertex = marking1; //oznacz wezel M' jako nowy
                        }
                    }

                }
            }
        }
        return graph;
    }
//wyswietlanie nieskonczonosci - to jest na razie problem, ale poki co jest wartosc ujemna

}
