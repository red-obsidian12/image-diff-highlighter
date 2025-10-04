package src.main.java.ImageReader.HeatMapStrategy;

public class RedScaleWhiteStrategy implements DistanceHighlightStrategy {
    @Override
    public int computePixel(double distance) {
        int normDist = (int) ((distance / MAX_RGB_DISTANCE) * 255.0 + 0.5);
        normDist = Math.max(0, Math.min(normDist, 255));

        int alpha = normDist; // livello di trasparenza (0-255)
        int srcR = 255, srcG = 0, srcB = 0; // rosso puro

        // sovrapposizione colori
        float a = alpha / 255f;
        int outR = (int) (srcR * a + 255 * (1 - a));
        int outG = (int) (srcG * a + 255 * (1 - a));
        int outB = (int) (srcB * a + 255 * (1 - a));
        int outA = 255; // mantiene alpha originale

        return (outA << 24) | (outR << 16) | (outG << 8) | outB;
    }
}