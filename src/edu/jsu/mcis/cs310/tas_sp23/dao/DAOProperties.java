package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.io.*;
import java.util.Properties;

/**
 * <p> Class for handling the DAO properties. </p>
 * @author Dalton Estes
 */
public class DAOProperties {

    
    /**
     * <p> String constant for the properties file for use in static 
     * initialization block. </p>
     */
    private static final String PROPERTIES_FILE = "dao.properties";
    
    /**
     * <p> Constant of type Properties equating to a new Property creation for
     * use in static initialization method and {@link #getProperty getProperty()}. </p>
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * <p> String variable for prefix. </p>
     */
    private final String prefix;

    static {

        try {

            InputStream file = DAOProperties.class.getResourceAsStream(PROPERTIES_FILE);
            PROPERTIES.load(file);

        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * <p> Setter for DAOProperties. </p>
     * @param prefix prefix for property
     */
    public DAOProperties(String prefix) {

        this.prefix = prefix;

    }

    /**
     * <p> Gets the property of the DAO. </p>
     * @param key key for property
     * @return a DAO property
     */
    public String getProperty(String key) {

        String fullKey = prefix + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            property = null;
        }

        return property;

    }

}
