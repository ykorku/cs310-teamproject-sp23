package edu.jsu.mcis.cs310.tas_sp23;

import java.util.zip.CRC32;

/**
 * <p> The badge class represents an employee's badge for the company using a string 
 * id and string description. </p>
 * @author Dalton Estes
 */
public class Badge {

    /**
     * <p> String variables representing an employee's id and description
     * on their badge. </p>
     */
    private final String id, description;

    /**
     * <p> Constructs a Badge object. </p>
     * @param description represents the description
     */
    public Badge(String description) {
        this.description = description;

        CRC32 crc = new CRC32();
        crc.update(description.getBytes());
        long idLong = crc.getValue();
        String idString = String.format("%08X", idLong);
        this.id = idString;
    }

    /**
     * <p> Finds an existing badge. </p>
     * @param id represents the id
     * @param description represents the description 
     */
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * <p> Gets the id on the badge. </p>
     * @return id representing the id
     */
    public String getId() {
        return id;
    }

    /**
     * <p> Gets the description on the badge. </p>
     * @return description representing the description 
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p> Returns a string representation of the badge object. </p>
     * @return a string representation of the badge object.
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();
    }
}