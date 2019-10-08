package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.manager.exceptions.ImportFailureException;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class XMLIngredientParserTest {

	Persistence persistence;

	@Before
	public void setUp() throws Exception {
		persistence = new XMLPersistence();
	}

	@Test
	public void testImportCheckIngredientName() {
		persistence.importFile(new File("resources/data/TestIngredients1.xml"), DataType.INGREDIENT);
		Managers.writeBuffer();
		assertEquals("Parse ingredient ID 0", 0, Managers.getIngredientManager().getIngredient(0).getId());
	}

	@Test
	public void testImportCheckIngredientId() {
		persistence.importFile(new File("resources/data/TestIngredients2.xml"), DataType.INGREDIENT);
		Managers.writeBuffer();
		assertEquals("Parses name correctly", 1,
				Managers.getIngredientManager().getIngredientByName("TestItem1").getId());
	}

	@Test
	public void testImportCheckUnit() {
		persistence.importFile(new File("resources/data/TestIngredients3.xml"), DataType.INGREDIENT);
		Managers.writeBuffer();
		assertEquals("ID 2 measured in grams", Unit.GRAMS, Managers.getIngredientManager().getIngredient(2).getUnit());
		assertEquals("ID 3 measured in ml", Unit.MILLILITRES,
				Managers.getIngredientManager().getIngredient(3).getUnit());
		assertEquals("ID 4 measured in units", Unit.UNITS, Managers.getIngredientManager().getIngredient(4).getUnit());
	}

	@Test
	public void testImportCheckStock() {
		persistence.importFile(new File("resources/data/TestIngredients4.xml"), DataType.INGREDIENT);
		Managers.writeBuffer();
		assertEquals("ID 5 truck stock 10", 10, Managers.getIngredientManager().getIngredient(5).getTruckStock());
		assertEquals("ID 5 kitchen stock 20", 20, Managers.getIngredientManager().getIngredient(5).getKitchenStock());
		assertEquals("ID 6, no truck stock", 0, Managers.getIngredientManager().getIngredient(6).getTruckStock());
		assertEquals("ID 6, no kitchen stock", 0, Managers.getIngredientManager().getIngredient(6).getKitchenStock());
		assertEquals("ID 7, 10000 truck stock", 10000,
				Managers.getIngredientManager().getIngredient(7).getTruckStock());
		assertEquals("ID 7, 1 kitchen stock", 1, Managers.getIngredientManager().getIngredient(7).getKitchenStock());
	}

	@Test
	public void testImportCheckDietary() {
		persistence.importFile(new File("resources/data/TestIngredients5.xml"), DataType.INGREDIENT);
		Managers.writeBuffer();
		assertTrue("All true, vegan",
				Managers.getIngredientManager().getIngredient(8).isSafeFor(DietaryRequirement.VEGAN));
		assertTrue("All true, vegetarian",
				Managers.getIngredientManager().getIngredient(8).isSafeFor(DietaryRequirement.VEGETARIAN));
		assertTrue("All true, nut free",
				Managers.getIngredientManager().getIngredient(8).isSafeFor(DietaryRequirement.NUT_ALLERGY));
		assertTrue("All true, gluten free",
				Managers.getIngredientManager().getIngredient(8).isSafeFor(DietaryRequirement.GLUTEN_FREE));
		assertTrue("All true, dairy free",
				Managers.getIngredientManager().getIngredient(8).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));

		assertFalse("All false, vegan",
				Managers.getIngredientManager().getIngredient(9).isSafeFor(DietaryRequirement.VEGAN));
		assertFalse("All false, vegetarian",
				Managers.getIngredientManager().getIngredient(9).isSafeFor(DietaryRequirement.VEGETARIAN));
		assertFalse("All false, nut free",
				Managers.getIngredientManager().getIngredient(9).isSafeFor(DietaryRequirement.NUT_ALLERGY));
		assertFalse("All false, gluten free",
				Managers.getIngredientManager().getIngredient(9).isSafeFor(DietaryRequirement.GLUTEN_FREE));
		assertFalse("All false, dairy free",
				Managers.getIngredientManager().getIngredient(9).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));

		assertFalse("All missing, vegan",
				Managers.getIngredientManager().getIngredient(10).isSafeFor(DietaryRequirement.VEGAN));
		assertFalse("All missing, vegetarian",
				Managers.getIngredientManager().getIngredient(10).isSafeFor(DietaryRequirement.VEGETARIAN));
		assertFalse("All missing, nut free",
				Managers.getIngredientManager().getIngredient(10).isSafeFor(DietaryRequirement.NUT_ALLERGY));
		assertFalse("All missing, gluten free",
				Managers.getIngredientManager().getIngredient(10).isSafeFor(DietaryRequirement.GLUTEN_FREE));
		assertFalse("All missing, dairy free",
				Managers.getIngredientManager().getIngredient(10).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));

		assertFalse("Combination, vegan is false",
				Managers.getIngredientManager().getIngredient(11).isSafeFor(DietaryRequirement.VEGAN));
		assertTrue("Combination, vegetarian is true",
				Managers.getIngredientManager().getIngredient(11).isSafeFor(DietaryRequirement.VEGETARIAN));
		assertFalse("Combination, nut allergy is missing",
				Managers.getIngredientManager().getIngredient(11).isSafeFor(DietaryRequirement.NUT_ALLERGY));
		assertFalse("Combination, gluten free is false",
				Managers.getIngredientManager().getIngredient(11).isSafeFor(DietaryRequirement.GLUTEN_FREE));
		assertTrue("Combination, lactose intolerent is true",
				Managers.getIngredientManager().getIngredient(11).isSafeFor(DietaryRequirement.LACTOSE_INTOLERANT));

	}

	@Test
	public void testImportInvalidIDs() {
		boolean threwException = false;
		try {
			persistence.importFile(new File("resources/data/TestBadIngredients1.xml"), DataType.INGREDIENT);
			Managers.writeBuffer();
		} catch (ImportFailureException e) {
			threwException = true;
		}
		assertTrue("Throw exception when importing ID -1", threwException);
		assertTrue("Should not have ID -1 in memory", Managers.getIngredientManager().getIngredient(-1) == null);
	}

	@Test
	public void testImportInvalidTruckStock() {
		boolean threwException = false;
		try {
			persistence.importFile(new File("resources/data/TestBadIngredients2.xml"), DataType.INGREDIENT);
			Managers.writeBuffer();
		} catch (ImportFailureException e) {
			threwException = true;
		}
		assertTrue("Throw exception when importing truck stock -1000", threwException);
		assertTrue("Should not have ID 1000 in memory", Managers.getIngredientManager().getIngredient(1000) == null);
	}
	
	@Test
	public void testImportInvalidKitchenStock() {
		boolean threwException = false;
		try {
			persistence.importFile(new File("resources/data/TestBadIngredients3.xml"), DataType.INGREDIENT);
			Managers.writeBuffer();
		} catch (ImportFailureException e) {
			threwException = true;
		}
		assertTrue("Throw exception when importing kitchen stock -1", threwException);
		assertTrue("Should not have ID 1001 in memory", Managers.getIngredientManager().getIngredient(1001) == null);
	}
	
	@Test
	public void testImportBlankNameStock() {
		boolean threwException = false;
		try {
			persistence.importFile(new File("resources/data/TestBadIngredients4.xml"), DataType.INGREDIENT);
			Managers.writeBuffer();
		} catch (ImportFailureException e) {
			threwException = true;
		}
		assertTrue("Throw exception when importing item with blank name", threwException);
		assertTrue("Should not have ID 1002 in memory", Managers.getIngredientManager().getIngredient(1002) == null);
	}
}
