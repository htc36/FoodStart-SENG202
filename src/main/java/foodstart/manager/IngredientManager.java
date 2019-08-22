package foodstart.manager;
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
	
	private Set<Ingredient> ingredients;


	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public IngredientManager(){
		
	}


	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}

}

