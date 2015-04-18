package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;

/**
 * Name: PTState
 * Description: PTState
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PTState extends GeneralState {
    private int capacity = Integer.MAX_VALUE;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        Preconditions.checkArgument(capacity > 0, "Capacity has to be greater than 0.");
        this.capacity = capacity;
    }
}
