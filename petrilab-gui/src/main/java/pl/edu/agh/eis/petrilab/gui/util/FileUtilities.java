package pl.edu.agh.eis.petrilab.gui.util;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Name: FileUtilities
 * Description: FileUtilities
 * Date: 2015-05-17
 * Created by BamBalooon
 */
public class FileUtilities {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Point2D.class, new Point2DSerialization())
            .create();
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public static String getExtension(File file) {
        String fileExtension = null;
        String fileName = file.getName();

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return fileExtension;
    }

    public static void saveFile(File file, Object content) throws IOException {
        Files.write(GSON.toJson(content), file, CHARSET);
    }

    public static <T> T openFile(File file, Class<T> outputClass) throws IOException {
        String textContent = Files.toString(file, CHARSET);
        return GSON.fromJson(textContent, outputClass);
    }
}
