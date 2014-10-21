package edu.gatech.gem5.game.readers;

import com.google.gson.reflect.TypeToken;

import edu.gatech.gem5.game.readers.Reader;
import edu.gatech.gem5.game.data.CompanyType;

import java.util.Map;

/**
 * A class for parsing company data files into a Java map.
 *
 * @author  Creston Bunch
 * @version 1.1.0
 */

public class CompanyReader extends Reader<String, CompanyType> {

    public CompanyReader(String path) {
        super(path, new TypeToken<
            Map<String, CompanyType>
        >(){}.getType());
    }
}
