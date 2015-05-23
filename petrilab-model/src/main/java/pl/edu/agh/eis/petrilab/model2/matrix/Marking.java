package pl.edu.agh.eis.petrilab.model2.matrix;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.primitives.Ints;

import java.util.Arrays;

/**
 * Name: Marking
 * Description: Marking
 * Date: 2015-05-23
 * Created by BamBalooon
 */
public class Marking {
    private final Double[] vector;

    public Marking(Double[] markingVector) {
        this.vector = markingVector;
    }

    public Marking(int[] markingVector) {
        this.vector = toDoubleArray(markingVector);
    }

    public Marking(Double[] oldMarking, int[] transition) {
        this.vector = add(oldMarking, toDoubleArray(transition));
    }

    public Double[] getValue() {
        return vector;
    }

    public void updateMarking(Marking sourceMarking) {
        Double[] sourceVector = sourceMarking.getValue();
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > sourceVector[i]) {
                vector[i] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public static boolean leq(Marking marking1, Marking marking2) {
        Double[] vector1 = marking1.getValue();
        Double[] vector2 = marking2.getValue();
        for (int i = 0; i < vector1.length; i++) {
            if (vector1[i] > vector2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean existLesser(Marking marking1, Marking marking2) {
        Double[] vector1 = marking1.getValue();
        Double[] vector2 = marking2.getValue();
        for (int i = 0; i < vector1.length; i++) {
            if (vector1[i] < vector2[i]) {
                return true;
            }
        }
        return false;
    }

    public static Double[] toDoubleArray(int[] intArray) {
        return FluentIterable.from(Ints.asList(intArray))
                .transform(new Function<Integer, Double>() {
                    @Override
                    public Double apply(Integer input) {
                        return input.doubleValue();
                    }
                }).toArray(Double.class);
    }

    public static Double[] add(Double[] vector, Double[] vector2) {
        Double[] sumVector = new Double[vector.length];
        for (int i = 0; i < sumVector.length; i++) {
            sumVector[i] = vector[i] + vector2[i];
        }
        return sumVector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Marking
                && Arrays.equals(vector, ((Marking) o).getValue());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
    }
}
