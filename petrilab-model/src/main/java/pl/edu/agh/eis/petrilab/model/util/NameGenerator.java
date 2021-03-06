package pl.edu.agh.eis.petrilab.model.util;

/**
 * Name: NameGenerator
 * Description: NameGenerator
 * Date: 2015-04-19
 * Created by BamBalooon
 */
@Deprecated
public enum NameGenerator {
    PLACE("P%d"), TRANSITION("T%d");

    private static final int DEFAULT_COUNTER_START = 1;
    private final String nameFormat;
    private int nameCounter;

    private NameGenerator(String nameFormat) {
        this(nameFormat, DEFAULT_COUNTER_START);
    }

    NameGenerator(String nameFormat, int nameCounter) {
        this.nameFormat = nameFormat;
        this.nameCounter = nameCounter;
    }

    public String getName() {
        return String.format(nameFormat, nameCounter++);
    }

}
