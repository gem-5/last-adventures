package edu.gatech.gem5.game;

import edu.gatech.gem5.game.readers.CompanyReader;
import edu.gatech.gem5.game.readers.ConditionReader;
import edu.gatech.gem5.game.readers.EnvironmentReader;
import edu.gatech.gem5.game.readers.EventReader;
import edu.gatech.gem5.game.readers.GadgetReader;
import edu.gatech.gem5.game.readers.GoodReader;
import edu.gatech.gem5.game.readers.GovernmentReader;
import edu.gatech.gem5.game.readers.ShieldReader;
import edu.gatech.gem5.game.readers.ShipReader;
import edu.gatech.gem5.game.readers.StarReader;
import edu.gatech.gem5.game.readers.StoryReader;
import edu.gatech.gem5.game.readers.TechReader;
import edu.gatech.gem5.game.readers.WeaponReader;



/**
 * A class for static access to data files for the game.
 * TODO: make it not static
 *
 * @author Creston Bunch
 */
public class Data {

    // player data
    /**
     * A ShipReader that contains a map to all Ships stored in game data.
     */
    public static final ShipReader   SHIPS   = new ShipReader("/data/Ships.json");
    /**
     * A WeaponReader that contains a map to all Weapons stored in game data.
     */
    public static final WeaponReader WEAPONS = new WeaponReader("/data/Weapons.json");
    /**
     * A ShieldReader that contains a map to all Shields stored in game data.
     */
    public static final ShieldReader SHIELDS = new ShieldReader("/data/Shields.json");
    /**
     * A GadgetReader that contains a map to all Gadgets stored in game data.
     */
    public static final GadgetReader GADGETS = new GadgetReader("/data/Gadgets.json");


    // Readerplanet data
    /**
     * An EnvironmentReader that contains a map to all environments stored in game data.
     */
    public static final EnvironmentReader ENVIRONMENTS = new EnvironmentReader("/data/Environments.json");
    /**
     * A ConditionReader that contains a map to all conditions stored in game data.
     */
    public static final ConditionReader   CONDITIONS   = new ConditionReader("/data/Conditions.json");
    /**
     * A GovernmentsReader that contains a map to all governments stored in game data.
     */
    public static final GovernmentReader  GOVERNMENTS  = new GovernmentReader("/data/Governments.json");
    /**
     * A GoodReader that  contains a map to all Goods stored in game data.
     */
    public static final GoodReader        GOODS        = new GoodReader("/data/Goods.json");
    /**
     * A TechReader that contains a map to all Techs stored in game data.
     */
    public static final TechReader        TECHS        = new TechReader("/data/TechLevels.json");
    /**
     * A CompanyReader that contains a map to all Companys stored in game data.
     */
    public static final CompanyReader     COMPANIES    = new CompanyReader("/data/Companies.json");
    /**
     * A EventReader that contains a map to all Events stored in game data.
     */
    public static final EventReader       EVENT        = new EventReader("/data/Events.json");


    // misc data
    /**
     * A StarReader that contains a map to all Stars stored in game data.
     */
    public static final StarReader  STARS = new StarReader("/data/Stars.json");
    /**
     * A StoryReader that contains a map to all Stories stored in game data.
     */
    public static final StoryReader STORY = new StoryReader("/data/Story.json");
}
