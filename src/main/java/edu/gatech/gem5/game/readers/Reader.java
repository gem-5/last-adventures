package edu.gatech.gem5.game.readers;

import com.google.gson.Gson;

import edu.gatech.gem5.game.data.*;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * An abstract class for parsing JSON dictionary data files into Java Maps.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public abstract class Reader<K, T> {

    protected Map<K, T> map;

    public Reader(String path, Type collectionType) {
        Map<K, T> map = new HashMap<>();
        InputStream stream = getClass().getResourceAsStream(path);
        String json = readStream(stream);
        this.map = new Gson().fromJson(json, collectionType);
    }

    /**
     * Get the whole map.
     *
     * @return the data map
     */
    public Map<K, T> get() {
        return this.map;
    }

    /**
     * Get a value from the data map.
     *
     * @param key The key to search for.
     * @return the associated value
     */
    public T get(K key) {
        return map.get(key);
    }

    /**
     * Read a stream into a string.
     *
     * @param stream The input stream.
     * @return The contents of the stream as a string.
     */
    private String readStream(InputStream stream) {
        String out = "";
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            out += scanner.nextLine() + "\n";
        }
        return out;
    }

}
