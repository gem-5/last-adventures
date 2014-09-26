package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.CompanyType;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Jack Mueller
 */
class Company {
    //economic maximum of a company wants to have of any good
    private static final int SOFT_MAX = 30;
    
    private final CompanyType type;
    private Map<String, Integer> currentGoods;

    /**
     * Construct the company from a company type.
     *
     * @param type The CompanyType to copy.
     */
    public Company(CompanyType type) {
        this.type = type;
        currentGoods = new HashMap<>();
        for (String product : type.getProducts()) {
            //when a company is generated, it should have anywhere from
            //5 to SOFT_MAX of each good it sells
            currentGoods.put(product, new Random().nextInt(SOFT_MAX - 4) + 5);
        }
    }
    
    public Map<String, Integer> getCurrentGoods() {
        return currentGoods;
    }
}
