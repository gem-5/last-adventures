package edu.gatech.gem5.game;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.gem5.game.readers.*;

/**
 * A class for static access to data files for the game.
 * TODO: make it not static
 *
 * @author Creston Bunch
 */
public class Data {

    // player data
    public static final ShipReader   SHIPS   = new ShipReader("/data/Ships.json");
    public static final WeaponReader WEAPONS = new WeaponReader("/data/Weapons.json");
    public static final ShieldReader SHIELDS = new ShieldReader("/data/Shields.json");
    public static final GadgetReader GADGETS = new GadgetReader("/data/Gadgets.json");

    // planet data
    public static final EnvironmentReader ENVIRONMENTS = new EnvironmentReader("/data/Environments.json");
    public static final ConditionReader   CONDITIONS   = new ConditionReader("/data/Conditions.json");
    public static final GovernmentReader  GOVERNMENTS  = new GovernmentReader("/data/Governments.json");
    public static final GoodReader        GOODS        = new GoodReader("/data/Goods.json");
    public static final TechReader        TECHS        = new TechReader("/data/TechLevels.json");
    public static final CompanyReader     COMPANIES    = new CompanyReader("/data/Companies.json");

    // misc data
    public static final StarReader  STARS = new StarReader("/data/Stars.json");
    public static final StoryReader STORY = new StoryReader("/data/Story.json");
}
