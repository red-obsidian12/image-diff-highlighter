package src.main.java.ImageReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageStat {
    private long execTime;

    public static void main(String[] args) {
        // controllo argomenti
        if (args.length != 3 && args.length != 5) {
            System.out.println("Sintassi non corretta, uso:\njava ImageStat.java img1 img2 output [-t threads]\n");
            System.exit(1);
        }

        // leggo i percorsi delle immagini
        String firstImagePath = args[0];
        String secondImagePath = args[1];
        String outputPath = args[2];

        int numThreads=Runtime.getRuntime().availableProcessors();
        if (args.length == 5) {
            if (args[4].equals("-t")) {
                try {
                    numThreads = Integer.parseInt(args[3]);
                    if (numThreads <= 0) {
                        System.out.println("Il numero di thread deve essere un intero positivo.");
                        System.exit(1);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Il parametro threads deve essere un numero intero.");
                    System.exit(1);
                }
            } else {
                System.out.println("Utilizzo scorretto del parametro. Uso corretto:\n" +
                        "java ImageStat.java img1 img2 output [-t threads]\n");
                System.exit(1);
            }
        }

        // avvio del programma di confronto
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

            // i threads non possono superare l'altezza dell'immagine
            numThreads = Math.min(numThreads, height);
            List<Thread> threads = new ArrayList<>(numThreads);

            // ora che è tutto corretto procedo con la suddivisione delle immagini in parti uguali
            int defaultDim = height / numThreads;
            int remainder =  height % numThreads;
            // devo lanciare un thread per ogni spezzone
            for (int i = 0; i < numThreads; i++) {
                int startRow = i * defaultDim;
                int endRow = (i+1) *  defaultDim;
                if (i == numThreads - 1) {
                    endRow = endRow + remainder;
                }

                ImageCompareModule worker = new ImageCompareModule(img1, img2, outImg, startRow, endRow, accumulator);
                threads.add(new Thread(worker, "Thread-" + i));
                threads.getLast().start();
            }

            // ora prima di proseguire devo aspettare che tutti i processi teminino
            for (Thread t : threads) {
                t.join();
            }

            long ssr = accumulator.getSum();
            double totalMSE = ssr / ((double) (height * width * 3));
            System.out.printf("MSE = %.6f%n", totalMSE);

            // salvo immagine output
            try {
                ImageIO.write(outImg, "png", new File(outputPath));
            } catch (IOException e) {
                System.err.println("Impossibile salvare immagine di output: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Errore nella lettura delle immagini: " + e.getMessage());
            System.exit(1);
        }
    }
}
