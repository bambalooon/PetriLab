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
    private final Map<String, Object> properties = Maps.newHashMap(ImmutableMap.<String, Object>builder()
            .put(VERTEX_FACTORY_TYPE, Place.class)
            .build());

    public void setProperty(String propertyKey, Object propertyValue) {
        properties.put(propertyKey, propertyValue);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String propertyKey) {
        return (T) properties.get(propertyKey);
    }
}
