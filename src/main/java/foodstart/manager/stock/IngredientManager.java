package foodstart.manager.stock;
import java.util.HashSet;
import java.util.Set;

import foodstart.model.stock.Ingredient;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class IngredientManager
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();


	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public IngredientManager(){
		
	}

	/**
	 * Add an ingredient to the registry
	 * @param ingredient The ingredient to add
	 */
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

	/**
	 * Gets all the ingredients in the registry
	 * @return The set of ingredients in the registry
	 */
	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

}

