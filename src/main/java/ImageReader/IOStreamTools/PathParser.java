package src.main.java.ImageReader.IOStreamTools;

import java.nio.file.Path;

/**
 * Utility per normalizzare e aprire percorsi in modo indipendente dalla
 * macchina:
 * - accetta separatori '\' o '/', anche misti
 * - risolve '.'
 * 
 * Esempi d'uso:
 * Path p = PathParser.normalizePath("data/files\file.txt");
 * viene tradotto in "./data/files/file.txt" (unix-style)
 */
public final class PathParser {

    private PathParser() {
    }

    /**
     * Normalizza un percorso testuale:
     * - Rende uniformi i separatori '\' e '/'
     * - Aggiunge '.' per indicare la working dir.
     */
    public static String normalizePath(String inputPath) {
        if (inputPath == null || inputPath.isEmpty()) {
            throw new IllegalArgumentException("Percorso non valido");
        }

        // 1. Converte '\' in '/'
        String path = inputPath.replace("\\", "/");

        if (!Path.of(path).isAbsolute()) {
            // 2. Se inizia con "/" aggiungo "."
            if (path.startsWith("/")) {
                path = "." + path;
            }

            // 3. Se non inizia con "./" aggiungo "./"
            if (!path.startsWith("./")) {
                path = "./" + path;
            }
        }

        return path;
    }

    public static String toAbsolute(String inputPath) {
        if (inputPath == null || inputPath.isEmpty()) {
            throw new IllegalArgumentException("Percorso non valido");
        }

        String normalized = normalizePath(inputPath);
        Path absPath = Path.of(normalized).toAbsolutePath().normalize();

        return absPath.toString();
    }
    
}
