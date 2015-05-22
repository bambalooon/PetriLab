package pl.edu.agh.eis.petrilab.gui;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import pl.edu.agh.eis.petrilab.model2.Place;

import java.util.Map;

/**
 * Name: Configuration
 * Description: Configuration
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class Configuration {
    public static final String VERTEX_FACTORY_TYPE = "VERTEX_FACTORY_TYPE";
    public static final String SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE = "SIMULATION_ACTIVE_TRANSITIONS_PRE_RENDER_PAINTABLE";
    public static final String SIMULATION_MODE_ACTIVE = "SIMULATION_MODE_ACTIVE";
    public static final String NAME_GENERATOR = "NAME_GENERATOR";
    private final Map<String, Object> properties = Maps.newHashMap(ImmutableMap.<String, Object>builder()
            .put(VERTEX_FACTORY_TYPE, Place.class)
            .put(SIMULATION_MODE_ACTIVE, false)
            .build());

    public void setProperty(String propertyKey, Object propertyValue) {
        properties.put(propertyKey, propertyValue);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String propertyKey) {
        return (T) properties.get(propertyKey);
    }

    @SuppressWarnings("unchecked")
    public <T> T removeProperty(String propertyKey) {
        return (T) properties.remove(propertyKey);
    }
}
