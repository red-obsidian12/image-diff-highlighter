package src.main.java.ImageReader.HeatMapStrategy;

public class RedThresholdStrategy implements DistanceHighlightStrategy {
    @Override
    public int computePixel(double distance) {
        int normDist = (int) ((distance / MAX_RGB_DISTANCE) * 225.0 + 0.5);
        if(normDist > 5)
            normDist = normDist+30;
        normDist = Math.max(0, Math.min(normDist, 255));
        // A | R | G | B
        return (255 << 24) | (normDist << 16) | (0 << 8) | 0;
    }
}
