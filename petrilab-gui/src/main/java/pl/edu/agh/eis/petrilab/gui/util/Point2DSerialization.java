package pl.edu.agh.eis.petrilab.gui.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.awt.geom.Point2D;
import java.lang.reflect.Type;

/**
 * Name: Point2DSerialization
 * Description: Point2DSerialization
 * Date: 2015-05-24
 * Created by BamBalooon
 */
public class Point2DSerialization implements JsonDeserializer<Point2D>, JsonSerializer<Point2D> {
    private static final String POINT2D_X_FIELD = "x";
    private static final String POINT2D_Y_FIELD = "y";

    @Override
    public Point2D deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        Point2D.Double deserializedPoint = new Point2D.Double();
        JsonObject jsonPoint = json.getAsJsonObject();

        deserializedPoint.x = jsonPoint.get(POINT2D_X_FIELD).getAsDouble();
        deserializedPoint.y = jsonPoint.get(POINT2D_Y_FIELD).getAsDouble();

        return deserializedPoint;
    }

    @Override
    public JsonElement serialize(Point2D srcPoint, Type typeOfSrc, JsonSerializationContext context) {
        return new Gson().toJsonTree(srcPoint);
    }
}
