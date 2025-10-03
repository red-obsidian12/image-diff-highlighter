package src.main.java.ImageReader;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Accumulatore per somma degli errori al quadrato.
 *
 */
public class MSEAccumulator {
    private long sum;

    public MSEAccumulator() {
        this.sum = 0;
    }

    public synchronized void add(long value) {
        sum=sum+value;
    }

    // sincronizzato per non restituire un valore obsoleto
    public synchronized long getSum() {
        return sum;
    }
}

