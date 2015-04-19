package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import org.apache.commons.collections15.collection.UnmodifiableCollection;
import pl.edu.agh.eis.petrilab.model.util.NameGenerator;

import java.util.Collection;
import java.util.Collections;

/**
 * Name: GeneralPlace
 * Description: GeneralPlace
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralPlace implements Place {
    public static final int MARKING_MIN = 0;
    public static final int MARKING_MAX = Integer.MAX_VALUE;
    public static final int MARKING_DEFAULT = MARKING_MIN;
    private Collection<TransitionToPlaceArc> inArcs = Collections.emptySet();
    private Collection<PlaceToTransitionArc> outArcs = Collections.emptySet();
    private String name;
    private int marking = MARKING_DEFAULT;

    public GeneralPlace(String name) {
        setName(name);
    }

    public GeneralPlace() {
        this(NameGenerator.PLACE.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
    }

    @Override
    public int getMarking() {
        return marking;
    }

    protected void setMarking(int marking) {
        this.marking = marking;
    }

    @Override
    public void decreaseMarking(int difference) {
        Preconditions.checkArgument(marking >= difference, "Cannot decrease marking below 0.");
        marking -= difference;
    }

    @Override
    public void increaseMarking(int difference) {
        marking += difference;
    }

    @Override
    public Collection<TransitionToPlaceArc> getInArcs() {
        return UnmodifiableCollection.decorate(inArcs);
    }

    @Override
    public Collection<PlaceToTransitionArc> getOutArcs() {
        return UnmodifiableCollection.decorate(outArcs);
    }

    protected void setInArcs(Collection<TransitionToPlaceArc> arcs) {
        Preconditions.checkNotNull(arcs);
        inArcs = arcs;
    }

    protected void setOutArcs(Collection<PlaceToTransitionArc> arcs) {
        Preconditions.checkNotNull(arcs);
        outArcs = arcs;
    }
}
