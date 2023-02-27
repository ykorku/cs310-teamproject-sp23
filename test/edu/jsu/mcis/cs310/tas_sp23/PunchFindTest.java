package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp23.dao.PunchDAO;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindPunches1() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */
        
        Punch p1 = punchDAO.find(3433);
        Punch p2 = punchDAO.find(3325);
        Punch p3 = punchDAO.find(1963);

        /* Compare to Expected Values */
        
        assertEquals("#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07", p1.printOriginal());
        assertEquals("#DFD9BB5C CLOCK IN: TUE 09/04/2018 08:00:00", p2.printOriginal());
        assertEquals("#99F0C0FA CLOCK IN: SAT 08/18/2018 06:00:00", p3.printOriginal());

    }

    @Test
    public void testFindPunches2() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p4 = punchDAO.find(5702);
        Punch p5 = punchDAO.find(4976);
        Punch p6 = punchDAO.find(2193);

        /* Compare to Expected Values */

        assertEquals("#0FFA272B CLOCK OUT: MON 09/24/2018 17:30:04", p4.printOriginal());
        assertEquals("#FCE87D9F CLOCK OUT: TUE 09/18/2018 17:34:00", p5.printOriginal());
        assertEquals("#FCE87D9F CLOCK OUT: MON 08/20/2018 17:30:00", p6.printOriginal());

    }
    
    @Test
    public void testFindPunches3() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p7 = punchDAO.find(954);
        Punch p8 = punchDAO.find(258);
        Punch p9 = punchDAO.find(717);

        /* Compare to Expected Values */

        assertEquals("#618072EA TIME OUT: FRI 08/10/2018 00:12:35", p7.printOriginal());
        assertEquals("#0886BF12 TIME OUT: THU 08/02/2018 06:06:38", p8.printOriginal());
        assertEquals("#67637925 TIME OUT: TUE 08/07/2018 23:12:34", p9.printOriginal());

    }
    
        @Test
    public void testFindPunches4() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p10 = punchDAO.find(154);
        Punch p11 = punchDAO.find(3348);
        Punch p12 = punchDAO.find(1734);

        /* Compare to Expected Values */

        assertEquals("#F1EE0555 CLOCK IN: WED 08/01/2018 06:48:50", p10.printOriginal());
        assertEquals("#82A8539F CLOCK OUT: TUE 09/04/2018 16:32:39", p11.printOriginal());
        assertEquals("#E06BE060 CLOCK IN: FRI 08/17/2018 11:55:59", p12.printOriginal());

    }   
    
    @Test
    public void testFindPunches5() {

        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Retrieve Punches from Database */

        Punch p13 = punchDAO.find(4032);
        Punch p14 = punchDAO.find(4095);
        Punch p15 = punchDAO.find(593);

        /* Compare to Expected Values */
        //593, 103, 'DFDFE648', 2018-08-06 16:32:41, 0
        assertEquals("#9D527CFB CLOCK IN: TUE 09/11/2018 06:55:56", p13.printOriginal());
        assertEquals("#CEA28723 CLOCK OUT: TUE 09/11/2018 12:03:51", p14.printOriginal());
        assertEquals("#DFDFE648 CLOCK OUT: MON 08/06/2018 16:32:41", p15.printOriginal());

    }
    
}
