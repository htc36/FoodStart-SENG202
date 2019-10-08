package foodstart.manager.xml;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.model.order.Order;
import foodstart.model.stock.Supplier;

public class XMLSalesLogParserTest {
    
    Persistence persistence;
    File dataFile;
    HashMap<Integer, Order> expectedOrders = new HashMap<Integer, Order>();
    Map<Integer, Order> actualOrders;
    static final String DIRECTORY = "resources/data/sales_log_xml_files/";

    @Before
    public void setUp() throws Exception {
        persistence = new XMLPersistence();
        expectedOrders = null;
        actualOrders = Managers.getOrderManager().getOrders();
    }

    @After
    public void tearDown() throws Exception {
        Managers.getOrderManager().removeAllOrders();
        persistence = null;
        dataFile = null;
    }


}
