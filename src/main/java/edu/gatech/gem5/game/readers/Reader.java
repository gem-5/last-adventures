package edu.gatech.gem5.game.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * An abstract class for parsing JSON dictionary data files into Java Maps.
 *
 * @author  Creston Bunch
 * @version 1.0.0
 */

abstract public class Reader<T> {

    /**
     * Read a file into a string.
     *
     * @param filename The path of the file to read.
     * @return The contents of the file as a UTF-8 string.
     */
    protected String readFile(String filename) {
        // default to UTF-8
        return readFile(filename, StandardCharsets.UTF_8);
    }

    /**
     * Read a file into a string.
     *
     * @param path The path of the file to read.
     * @param enc The encoding of the file.
     * @return The contents of the file in the specified encoding.
     */
    protected String readFile(String path, Charset enc) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, enc);
        } catch (IOException e) {
            System.out.println(e);
        }
        return "";
    }

    /**
     * Load the JSON dictionary into a Map object.
     *
     * @param path The path of the data file to load.
     * @return A map with the ids and corresponding objects.
     */
    abstract public Map<String, T> load(String path);

}
