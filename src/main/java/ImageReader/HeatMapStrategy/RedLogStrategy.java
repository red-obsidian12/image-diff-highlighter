package src.main.java.ImageReader.HeatMapStrategy;

public class RedLogStrategy implements DistanceHighlightStrategy {
    @Override
    public int computePixel(double distance) {
        double scaled = Math.log1p(distance) / Math.log1p(MAX_RGB_DISTANCE);
        int normDist = (int)(scaled * 255 + 0.5);
        return 0xFF000000 | (normDist << 16);
    }


}
