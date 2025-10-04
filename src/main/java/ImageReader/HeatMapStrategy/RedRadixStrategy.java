package src.main.java.ImageReader.HeatMapStrategy;

public class RedRadixStrategy implements DistanceHighlightStrategy {
    @Override
    public int computePixel(double distance) {
        int normDist = (int)(Math.sqrt(distance / MAX_RGB_DISTANCE) * 255 + 0.5);
        normDist = Math.min(normDist, 255);
        return 0xFF000000 | (normDist << 16);
    }


}
