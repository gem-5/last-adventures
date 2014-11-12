package edu.gatech.gem5.game.readers;

import com.google.gson.Gson;
import java.io.IOException;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract class for parsing JSON dictionary data files into Java Maps.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 * @param <K> A json String.
 * @param <T> A LastAdventures data type defined by json.
 */

public abstract class AbstractReader<K, T> {

    /**
     * Map that will hold Gson data.
     */
    protected Map<K, T> map;

    /**
     * Construct the Abstract reader given the path and the Type.
     * 
     * @param path the path
     * @param collectionType the Type to be paired with the path
     */
    public AbstractReader(String path, Type collectionType) {
        InputStream stream = AbstractReader.class.getResourceAsStream(path);
        String json = readStream(stream);
        map = new Gson().fromJson(json, collectionType);
        try {
            stream.close();
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    /**
     * Get the whole map.
     *
     * @return the data map
     */
    public Map<K, T> get() {
        return map;
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
        StringBuilder out = new StringBuilder();
        
        Scanner scanner = new Scanner(stream, Charset.defaultCharset().displayName());
        while (scanner.hasNext()) {
            out.append(scanner.nextLine()).append("\n");
        }
        return out.toString();
    }

}
