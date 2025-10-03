package src.main.java.ImageReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageStat {
    public static void main(String[] args) {
        // controllo argomenti
        if (args.length != 3) {
            System.out.println("Sintassi non corretta, uso:\njava ImageStat.java img1 img2 output\n");
            System.exit(1);
        }

        // leggo i percorsi dei files
        String firstImagePath = args[0];
        String secondImagePath = args[1];
        String outputPath = args[2];

        // controllo che dimensioni delle immagini siano compatibili
        try {
            BufferedImage img1 = ImageIO.read(new File(firstImagePath));
            BufferedImage img2 = ImageIO.read(new File(secondImagePath));

            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                System.out.println("Le immagini devono avere la stessa dimensione!");
                System.exit(1);
            }

            int width = img1.getWidth();
            int height = img1.getHeight();

            BufferedImage outImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            MSEAccumulator accumulator = new MSEAccumulator();

            // ora che è tutto corretto procedo con la suddivisione delle immagini in parti uguali
            int threadsAvailable = Runtime.getRuntime().availableProcessors();
            // creo lista di threads perché è estendibile
            List<Thread> threads = new ArrayList<Thread>(threadsAvailable);


        } catch (Exception e) {
            System.out.println("Errore nel leggere le immagini: " + e.getMessage());
        }
    }
}
