package edu.jsu.mcis.cs310.tas_sp23.dao;

/**
 * <p> Class that provides method for exception handling. </p>
 * @author Dalton Estes
 */
public class DAOException extends RuntimeException {

    /**
     * <p> Method for class to throw an exception when errors are encountered. </p>
     * @param message exception message
     */
    public DAOException(String message) {
        super(message);
    }

}
