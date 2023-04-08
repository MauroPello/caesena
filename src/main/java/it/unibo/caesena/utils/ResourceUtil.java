package it.unibo.caesena.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

/**
 * This class contains methods that are meant to help developers
 * access resources like images and files.
 */
public final class ResourceUtil {

    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP;

    /**
     * Class constructor marked as private as its not needed and all the methods are
     * static.
     */
    private ResourceUtil() {

    }

    /**
     *
     * @param filename    name of file to search
     * @param imageDirectories path where search filename
     * @return a BufferedImage from a path
     */
    public static BufferedImage getBufferedImage(final String filename, final List<String> imageDirectories) {
        try {
            final List<String> directories = new ArrayList<>(imageDirectories);
            directories.add(0, "images");
            return ImageIO.read(getInputStreamFromFile(filename, directories));
        } catch (IOException e) {
            throw new IllegalStateException("Image path not valid", e);
        }

    }

    /**
     *
     * @param filename    name of file to search
     * @param directories path where search filename
     * @return InputStream for finding the path for the filename
     */
    public static InputStream getInputStreamFromFile(final String filename, final List<String> directories) {
        return ClassLoader.getSystemResourceAsStream(ROOT + joinDirectories(directories) + filename);
    }

    /**
     *
     * @param directories list of all directories
     * @return joined directories in String
     */
    private static String joinDirectories(final List<String> directories) {
        String joinedDirectories = directories.stream().collect(Collectors.joining(SEP));
        if (!(joinedDirectories.isEmpty() || joinedDirectories.isBlank())) {
            joinedDirectories += SEP;
        }
        return joinedDirectories;
    }
}
