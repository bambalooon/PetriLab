package pl.edu.agh.eis.petrilab.model2;

/**
 * Name: Transition
 * Description: Transition
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class Transition implements PetriNetVertex {
    private String name;

    public Transition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
