package pl.edu.agh.eis.petrilab.model;

import com.google.common.collect.Sets;
import org.apache.commons.collections15.collection.UnmodifiableCollection;

import java.util.Collection;

/**
 * Name: PetriNet
 * Description: PetriNet
 * Date: 2015-04-19
 * Created by BamBalooon
 */
@Deprecated
public class PetriNet {
    private final Collection<Place> places = Sets.newHashSet();
    private final Collection<Transition> transitions = Sets.newHashSet();
    private final Collection<Arc> arcs = Sets.newHashSet();

    public void addPlace(Place place) {
        places.add(place);
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public void addArc(Arc arc) {
        Place place = arc.getPlace();
        Transition transition = arc.getTransition();
        if (place instanceof PTPlace && transition instanceof GeneralTransition) {
            new PTPlace.Builder().fromPlace((PTPlace) place).withArc(arc).modify();
            new GeneralTransition.Builder().fromTransition((GeneralTransition) transition).withArc(arc).modify();
        } else {
            throw new IllegalArgumentException("Arc with this type of place: " + place
                    + " and transition: " + transition + " aren't supported: ");
        }
        arcs.add(arc);
    }

    //TODO: add methods to remove objects

    public Collection<Place> getPlaces() {
        return UnmodifiableCollection.decorate(places);
    }

    public Collection<Transition> getTransitions() {
        return UnmodifiableCollection.decorate(transitions);
    }

    public Collection<Arc> getArcs() {
        return UnmodifiableCollection.decorate(arcs);
    }
}
