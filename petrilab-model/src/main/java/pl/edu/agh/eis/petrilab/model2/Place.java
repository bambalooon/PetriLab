package pl.edu.agh.eis.petrilab.model2;

import pl.edu.agh.eis.petrilab.model.*;

/**
 * Name: Place
 * Description: Place
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class Place implements PetriNetVertex {
    public static final int MARKING_MIN = 0;
    public static final int MARKING_MAX = Integer.MAX_VALUE;
    public static final int MARKING_DEFAULT = MARKING_MIN;
    public static final int CAPACITY_MIN = 1;
    public static final int CAPACITY_MAX = Integer.MAX_VALUE;
    public static final int CAPACITY_DEFAULT = CAPACITY_MAX;
    private String name;
    private int marking;
    private int capacity;

    public Place(String name) {
        this(name, MARKING_DEFAULT, CAPACITY_DEFAULT);
    }

    protected Place(String name, int marking, int capacity) {
        this.name = name;
        this.marking = marking;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarking() {
        return marking;
    }

    public void setMarking(int marking) {
        this.marking = marking;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
