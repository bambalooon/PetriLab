package pl.edu.agh.eis.petrilab.model;

import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * Name: PetriNet
 * Description: PetriNet
 * Date: 2015-04-19
 * Created by BamBalooon
 */
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

}
