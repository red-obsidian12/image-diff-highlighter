package src.main.java.ImageReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageStat {
    public static void main(String[] args) {
        // controllo argomenti
        if (args.length != 2) {
            System.out.println("Sintassi non corretta, uso:\njava ImageStat.java img1.png img2.png\n");
            System.exit(1);
        }

        // leggo i percorsi dei files
        String firstImagePath = args[0];
        String secondImagePath = args[1];

        // controllo che dimensioni delle immagini siano compatibili
        try {
            BufferedImage img1 = ImageIO.read(new File(firstImagePath));
            BufferedImage img2 = ImageIO.read(new File(secondImagePath));

            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                System.out.println("Le immagini devono avere la stessa dimensione!");
                System.exit(1);
            }

            // ora che è tutto corretto procedo con l'esecuzione dell'algoritmo


        } catch (Exception e) {
            System.out.println("Errore nel leggere le immagini: " + e.getMessage());
        }
    }
}
