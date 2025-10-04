package src.main.java.ImageReader.HeatMapStrategy;

public interface DistanceHighlightStrategy {
    double MAX_RGB_DISTANCE = Math.sqrt(3 * 255 * 255);

    int computePixel(double distance);
}
