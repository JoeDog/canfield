package org.joedog.canfield.model;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;
import org.joedog.util.Debug;
import org.joedog.util.NumberUtils;
import org.joedog.util.FileUtils;
import org.joedog.util.FileLineIterator;
import org.joedog.util.FileLineReader;
import org.joedog.canfield.control.Constants;

public class Config extends AbstractModel {
  private Attributes    conf      = null;
  private static String sep       = java.io.File.separator;
  private static String cfgdir    = System.getProperty("user.home")+sep+".canfield";
  private static String cfgfile   = cfgdir+sep+"game.conf";
  private static String hsfile    = cfgdir+sep+"scores.data";
  private static String cardfile  = cfgdir+sep+"cards.txt";
  private static Config _instance = null;
  private static Object mutex     = new Object();

  private Config() {
    System.getProperties().put("canfield.dir",    cfgdir);
    System.getProperties().put("canfield.conf",   cfgfile);
    System.getProperties().put("canfield.scores", hsfile);
    System.getProperties().put("canfield.cards",  cardfile);

    conf = new Attributes();

    if (! FileUtils.exists(this.cfgdir)) {
      FileUtils.mkdirs(this.cfgdir); 
    }
      
    try {
      FileInputStream fis = new FileInputStream(new File(this.cfgfile));
      conf.load(fis);
      if (conf.getProperty("Debug") != null && conf.getProperty("Debug").equals("true")) {
        System.getProperties().put("joedog.debug", "true");
      } 

      if (conf.getProperty("Log") != null) {
        System.getProperties().put("log.file", conf.getProperty("Log"));
      }
      /**
       * this is sloppy but we like turing this on for debugging 
       */
      Enumeration e = conf.propertyNames();
      while (e.hasMoreElements()) {
        String key = (String) e.nextElement();
        Debug.print(key + ": " + conf.getProperty(key));
      }
    } catch (Exception e) {
      // catch Config Exception right here
    }
  }

  public synchronized static Config getInstance() {
    if (_instance == null) {
      synchronized(mutex) {
        if (_instance == null) {
          _instance = new Config();
        }
      }
    }
    return _instance;
  }

  public Enumeration propertyNames() {
    return conf.propertyNames();
  }

  public void save() {
    try {
      conf.store(new FileOutputStream(this.cfgfile), null);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return;
  }

  // get property value by name
  public String getProperty(String key) {
    String value = null;

    if (conf.containsKey(key)) {
      value = (String) conf.get(key);
    } else {
      // the property is absent
    }
    return value;
  }

  public String getProperty(String key, String def) {
    String val = this.getProperty(key);
    return (val == null) ? def : val;
  }

  public void setProperty(String key, String val) {
    conf.setProperty(key, val);
  }

  /**
   * If you have a config file, we'll assume you're config'd
   */
  private boolean isConfigured() {
    return FileUtils.exists(this.cfgfile);
  }

  /**
   * Getters and setters 
   *
   * Tracked properties in the class:
   * MainDimension=(num,num)       // dimensions of the main frame
   * MainPosition=(num,num)        // screen position of the main frame
   * ConfigPosition=(num,num)      // screen position of the configuration frame
   * PlayerName=string             // name of the player
   * Cheat=string                  // a passphrase to set the game in cheat mode
   * Headless=true|false           // run game without GUI (for building AI)
   * Debug=true|false              // print debugging information to screen
   */

  /**
   * Sets the dimensions for the main frame. The default  
   * minimum size 1024x690. The format here is (1024,690)
   * <p>
   * @param  String   In format (num,num) 
   * @return void
   */
  public void setMainDimension(String value) {
    String dim = this.dimensionCleanse(value, 1024, 690);
    this.conf.setProperty("MainDimension", dim);
  }

  /**
   * Returns the default dimensions of the main frame
   * size in the following format: (num,num).
   * @param  void
   * @return String  default value is (1024,690)
   */
  public String getMainDimension() {
    return this.conf.getProperty("MainDimension", "(1024,690)");
  }

  /** 
   * Sets the x,y coordinates of the main frame. It expects 
   * those coordinates in the following format: (num,num)
   * <p>
   * @param  String  default value is (10,10)
   * @return void
   */
  public void setMainPosition(String value) {
    String dim = this.dimensionCleanse(value, 10, 10);
    this.conf.setProperty("MainPosition", dim);
  }

  /** 
   * Returns the x,y coordinates for the main frame in the
   * following format: (num,num)
   * <p>
   * @param  void
   * @return String  default value: (10,10)
   */
  public String getMainPosition() {
    return this.conf.getProperty("MainPosition", "(10,10)");
  }

  /** 
   * Returns the x,y coordinates for the Config frame in the
   * following format: (num,num)
   * <p>
   * @param  void
   * @return String  default value: (10,10)
   */
  public void setConfigPosition(String value) {
    String dim = this.dimensionCleanse(value, 10, 10);
    this.conf.setProperty("ConfigPosition", dim);
  }

  /**
   * Returns the x,y coordinates for the Config frame in
   * the following format: (num,num)
   * <p>
   * @param  void
   * @return String default value: (10,10)
   */
  public String getConfigPosition() {
    return this.conf.getProperty("ConfigPosition", "(10,10)");
  }

  /** 
   * Returns the x,y coordinates for the Dialog frame in the
   * following format: (num,num)
   * <p>
   * @param  void
   * @return String  default value: (10,10)
   */
  public void setDialogPosition(String value) {
    String dim = this.dimensionCleanse(value, 10, 10);
    this.conf.setProperty("DialogPosition", dim);
  }

  /**
   * Returns the x,y coordinates for the Dialog frame in
   * the following format: (num,num)
   * <p>
   * @param  void
   * @return String default value: (X+690,Y+420)
   */
  public String getDialogPosition() {
    return this.conf.getProperty("DialogPosition", "(700,430)");
  }

  /**
   * Sets the name of the player 
   * <p>
   * @param  String  The player's name
   * @return void     
   */
  public void setPlayerName(String value) {
    if (value != null && value.length() > 0) {
      this.conf.setProperty("PlayerName", value); 
      firePropertyChange(Constants.NAME, "names", value);
      return;
    }
    this.conf.setProperty("PlayerName", "Name");
  }

  /**
   * Returns the name of the player
   * <p>
   * @param  void
   * @return String  The player's name; default 'Name'
   */
  public String getPlayerName() {
    return this.conf.getProperty("PlayerName", "Name");
  }

  /**
   * This will probably never be called. The programmer sets 
   * the cheat in his .canfield/game.conf file
   */
  public void setCheat(String value) {
    this.conf.setProperty("Cheat", value);
  }

  /**
   * Returns the cheat from the .canfield/game.conf file. The
   * string is then hashed and must match a hardcoded cheat.
   * Hint: I used a popular computer game cheat code
   */
  public String getCheat() {
    String key = MD5((String)conf.getProperty("Cheat"));

    if (key==null || key.length() < 2) {
      return "false";
    } 

    if (key.equals("6f40ce1466318bc16e9541c437609de5")) {
      return "true";
    }
    return "false";
  }

  /**
   * Instruct the application as to whether or not it
   * should run headless, i.e., no GUI. true for headless
   * and false for not headless
   * <p>
   * @param  String
   * @return void
   */
  public void setHeadless(String value) {
    String tmp = value.toLowerCase();
    if (tmp == null || ! tmp.equals("true") || ! tmp.equals("false")) {
      this.conf.setProperty("Headless", "false");  
    }
    this.conf.setProperty("Headless", tmp);
  }

  /**
   * Return the headless state for the application -
   * true for headless and false for not headless
   * <p>
   * @param  void
   * @return String  default value is "false"
   */
  public String getHeadless() {
    String tmp = this.conf.getProperty("Headless", "false").toLowerCase();
    if (tmp == null) return "false";

    if (tmp.equals("true") || tmp.equals("false")) {
      return tmp;
    }
    return "false";
  }

  /**
   * Instructs the application as to whether or not it should
   * go into debug mode: true or false
   * <p>
   * @param  String  either "true" or "false"
   * @return void
   */
  public void setDebug(String value) {
    String debug = value.toLowerCase();

    if (! debug.equals("true") || ! debug.equals("false")) {
      this.conf.setProperty("Debug", "false");  
    }
    this.conf.setProperty("Debug", debug);
  }

  /**
   * Returns the debugging state for the application,
   * true turns debugging on, false does not.
   * <p>
   * @param  void
   * @return String default value is false
   */
  public String getDebug() {
    String debug = this.conf.getProperty("Debug", "false").toLowerCase();
    if (debug == null) return "false";

    if (! debug.equals("true") || ! debug.equals("false")) {
      return debug;
    }
    return "false";
  }

  /**
   * A helper method used to hash our cheat.
   */
  private String MD5(String md5) {
    if (md5==null || md5.length() < 2) return "haha";

    try {
      java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i) {
        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) { }
    return null;
  }

  /**
   * Examine the dimension input and ensure it conforms with the
   * appropriate format, i.e., (num,num). If anything is wrong with
   * the input, this will retrun (defX,defY)
   */
  private String dimensionCleanse(String value, int defX, int defY) {
    String def = "("+defX+","+defY+")"; 
    String ret = value; // store it and use this if value checks out

    if (value == null) {
      return def;
    }

    if ((value = value.replaceAll("\\s", "")).length() < 6) {
      return def;
    }
    String[] tokens = (value = value.substring(1, value.length() - 1)).split(",");
    if (tokens.length != 2) {
      return def;
    }

    int[] tmp = new int[tokens.length];

    try {
      for (int i = 0; i < tokens.length; ++i) {
        tmp[i] = Integer.parseInt(tokens[i]);
      }
    } catch (NumberFormatException ex) {
      return def;
    }
    if (tmp[0] > 0 && tmp[1] > 0) {
      return ret;
    }
    return def;
  }  
}
