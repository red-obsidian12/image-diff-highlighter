package src.main.java.ImageReader;


import java.awt.image.BufferedImage;

/**
 * Confronta un intervallo di righe [startRow, endRow) delle due immagini.
 * Calcola la somma dei quadrati degli errori (da utilizzare poi in MSE) e scrive i pixel di output.
 */
public class ImageCompareModule implements Runnable {
    private final BufferedImage img1;
    private final BufferedImage img2;
    private final BufferedImage output;
    private final int startRow;
    private final int endRow;
    private final MSEAccumulator accumulator;

    public ImageCompareModule(BufferedImage img1, BufferedImage img2,
                              BufferedImage output, int startRow, int endRow,
                              MSEAccumulator accumulator) {
        this.img1 = img1;
        this.img2 = img2;
        this.output = output;
        this.startRow = startRow;
        this.endRow = endRow;
        this.accumulator = accumulator;
    }

    @Override
    public void run() {
        int w = img1.getWidth();
        for (int y = startRow; y < endRow; y++) {
            for (int x = 0; x < w; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

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
                accumulator.add(sq);        // aggiorno accumulatore

                // distanza per pixel come media delle differenze assolute sui tre canali
                int avgAbsDiff = (Math.abs(dr) + Math.abs(dg) + Math.abs(db)) / 3;

                // Ora scrivo sul file di output un pixel rosso sempre più intenso a seconda della distanza
                // Colore rosso crescente con la distanza (R,0,0).
                // Significato: alpha a 255, R che dipende da avgAbsDiff, G=0, B=0
                int outRgb = (255 << 24) | (avgAbsDiff << 16) | (0 << 8) | 0;
                // Scrittura sul file di output
                output.setRGB(x, y, outRgb);
            }
        }
    }
}

