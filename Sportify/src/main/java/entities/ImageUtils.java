package entities;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage toBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return SwingFXUtils.fromFXImage(image, bufferedImage);
    }

    public static Image toFXImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream); // Write the BufferedImage to the outputStream as PNG
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    public static byte[] toByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, outputStream);
        return outputStream.toByteArray();
    }

    public static BufferedImage fromByteArray(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }
}
