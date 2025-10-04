package src.main.java.ImageReader;

import src.main.java.ImageReader.HeatMapStrategy.*;

import java.awt.image.BufferedImage;

/**
 * Confronta un intervallo di righe [startRow, endRow) delle due immagini.
 * Eslcude endRow.
 * Calcola la somma dei quadrati degli errori (da utilizzare poi in MSE) e
 * scrive i pixel di output.
 */
public class ImageCompareModule implements Runnable {
    private final BufferedImage img1;
    private final BufferedImage img2;
    private final BufferedImage output;
    private final int startRow;
    private final int endRow;
    private final MSEAccumulator accumulator;
    private final int id;
    private final boolean verbose;

    public ImageCompareModule(BufferedImage img1, BufferedImage img2,
            BufferedImage output, int startRow, int endRow,
            MSEAccumulator accumulator, int id, boolean verbose) {
        this.img1 = img1;
        this.img2 = img2;
        this.output = output;
        this.startRow = startRow;
        this.endRow = endRow;
        this.accumulator = accumulator;
        this.id = id;
        this.verbose = verbose;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long partialSum = 0;
        int w = img1.getWidth();
        int h = endRow - startRow;
        PixelDistanceHighlighter distanceHighlighter = new PixelDistanceHighlighter();
        distanceHighlighter.setStrategy(new RedThresholdStrategy());

        // precarico settori di pixel su cui lavorare
        int[] pixels1 = img1.getRGB(0, startRow, w, h, null, 0, w);
        int[] pixels2 = img2.getRGB(0, startRow, w, h, null, 0, w);
        int[] outputPixels = new int[w * h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int index = y * w + x;
                int rgb1 = pixels1[index];
                int rgb2 = pixels2[index];

                // mascheratura per ottenere il valore dei canali
                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = (rgb1) & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = (rgb2) & 0xFF;

                // differenze tra i pixel
                int dr = r1 - r2;
                int dg = g1 - g2;
                int db = b1 - b2;

                // somma degli errori al quadrato (per MSE si sommerà su tutti i pixel e canali)
                long sq = (long) dr * dr + (long) dg * dg + (long) db * db;
                partialSum = partialSum + sq;

                // Ora scrivo sul file di output un pixel scelto dallo strategy
                double distance = Math.sqrt(sq);


                outputPixels[index] = distanceHighlighter.computePixel(distance);

            }
        }

        // ora scrivo l'output sull'immagine effettiva
        output.setRGB(0, startRow, w, h, outputPixels, 0, w);
        accumulator.add(partialSum); // aggiorno accumulatore
        long endTime = System.currentTimeMillis(); // Fine
        long execTime = endTime - startTime;
        if (verbose) {
            System.out.printf(this + ", time: %d ms%n", execTime);
        }
    }

    @Override
    public String toString() {
        return "ImageCompareModule-" + id + ", startRow=" + startRow + ", endRow=" + (endRow - 1);
    }
}
