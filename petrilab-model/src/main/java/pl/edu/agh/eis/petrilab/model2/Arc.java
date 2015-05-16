package pl.edu.agh.eis.petrilab.model2;

/**
 * Name: Arc
 * Description: Arc
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class Arc {
    public static final int WEIGHT_MIN = 1;
    public static final int WEIGHT_MAX = Integer.MAX_VALUE;
    public static final int WEIGHT_DEFAULT = WEIGHT_MIN;
    private int weight;

    public Arc() {
        this(WEIGHT_DEFAULT);
    }

    public Arc(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
