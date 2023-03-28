package it.unibo.caesena.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public final class ResourceUtil {

    private static final String SEP = File.separator;
    private static final String ROOT = "it" + SEP + "unibo" + SEP + "caesena" + SEP;

    private ResourceUtil() {

    }

    public static BufferedImage getBufferedImage(final String filename, final List<String> directories) {
        BufferedImage image = null;
        try {
            final File file = new File(
                    ClassLoader.getSystemResource(ROOT + "images" + SEP + joinDirectories(directories) + SEP + filename)
                            .toURI());
            image = ImageIO.read(file);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Image path not valid");
        }

        return image;
    }

    public static InputStream getInputStreamFromFile(final String filename, final List<String> directories) {
        return ClassLoader.getSystemResourceAsStream(ROOT + joinDirectories(directories) + filename);
    }

    private static String joinDirectories(final List<String> directories) {
        String joinedDirectories = directories.stream().collect(Collectors.joining(SEP));
        if (!(joinedDirectories.isEmpty() || joinedDirectories.isBlank())) {
            joinedDirectories += SEP;
        }
        return joinedDirectories;
    }
}
