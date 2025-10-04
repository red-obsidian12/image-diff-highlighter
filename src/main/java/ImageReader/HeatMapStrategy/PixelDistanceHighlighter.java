package src.main.java.ImageReader.HeatMapStrategy;

public class PixelDistanceHighlighter {
    DistanceHighlightStrategy strategy;

    public PixelDistanceHighlighter() {
    }

    public int computePixel(double distance) {
        return strategy.computePixel(distance);
    }

    public void setStrategy(DistanceHighlightStrategy strategy) {
        this.strategy = strategy;
    }
}
