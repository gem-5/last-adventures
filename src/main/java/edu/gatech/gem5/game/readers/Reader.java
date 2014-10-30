package edu.gatech.gem5.game.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;
import java.lang.reflect.Type;

import edu.gatech.gem5.game.data.*;

/**
 * An abstract class for parsing JSON dictionary data files into Java Maps.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public abstract class Reader<K, T> {

    protected Map<K, T> map;

    public Reader(String path, Type collectionType) {
        InputStream stream = Reader.class.getResourceAsStream(path);
        String json = readStream(stream);
        map = new Gson().fromJson(json, collectionType);
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
        String out = "";
        
        Scanner scanner = new Scanner(stream, Charset.defaultCharset().displayName());
        while (scanner.hasNext()) {
            out += scanner.nextLine() + "\n";
        }
        return out;
    }

}
