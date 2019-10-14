package foodstart.model.menu;

import java.util.HashSet;
import java.util.Set;


/**
 * Models a menu item in the system
 */
public class MenuItem {
	/**
	 * The unique identifier for each menu item
	 */
	private int id;

	/**
	 * The name of the menu item
	 */
	private String name;

	/**
	 * The description of the menu item
	 */
	private String description;

	private PermanentRecipe defaultVariant;
	/**
	 * A set containing all the recipe variants of a menu item
	 */
	private Set<PermanentRecipe> variants;

	/**
	 * Constructs an instance of a menu item
	 *
	 * @param databaseId  the UID of the menu item
	 * @param name        the name of the menu item
	 * @param description a description of the menu item
	 * @param variants    a set of all recipes that make up the menu item
	 * @param defaultVariant the default recipe for the menu item
	 */
	public MenuItem(int databaseId, String name, String description, Set<PermanentRecipe> variants, PermanentRecipe defaultVariant) {
		this.id = databaseId;
		this.name = name;
		this.description = description;
		this.variants = variants;
		this.defaultVariant = defaultVariant;
		if (defaultVariant != null) {
			this.variants.add(defaultVariant);
		}
	}

	/**
	 * Gets the data base ID
	 *
	 * @return databaseId
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the data base ID
	 * @param id the id of the menu item
	 */
	public void setDatabaseId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name of the menu item
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the menu item
	 * @param name the name of the menu item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the menu item
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the menu item
	 * @param description the description of the menu item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the different variants of the menu item
	 *
	 * @return variants
	 */
	public Set<PermanentRecipe> getVariants() {
		return variants;
	}

	/**
	 * Adds recipe variant to menuitem
	 * @param recipe the variant to add
	 */
	public void addVariant(PermanentRecipe recipe) {
		variants.add(recipe);
	}

	/**
	 * Removes recipe variant from menuitem
	 * @param recipe the variant to remove
	 */
	public void removeVariant(PermanentRecipe recipe) {
		variants.remove(recipe);
	}

	/**
     * Gets the different variants of the menu item as a string
     *
     * @return output A string of the variants
     */
    public String getVariantsAsString() {
        String output = "";
        for (PermanentRecipe recipe : variants) {
                output += String.format("%s ", recipe.getDisplayName());
        }
        return output;
    }

	/**
	 * Sets the variants of the menu item
	 * @param variants the possible variants of the menu item
	 */
	public void setVariants(Set<PermanentRecipe> variants) {
		this.variants = variants;
	}

	/**
	 * Clones the menu item
	 *
	 * @return a clone of the menu item
	 */
	public MenuItem clone() {
		Set<PermanentRecipe> variantsCopy = new HashSet<>(variants);
		return new MenuItem(id, name, description, variantsCopy, defaultVariant);
	}

	/**
	 * Returns true if the given object has the same parameters
	 * @param obj the object to check against
	 * @return true if the objects have equal parameters; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MenuItem other = (MenuItem) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (variants == null) {
			if (other.variants != null) {
				return false;
			}
		} else if (!variants.equals(other.variants)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the hash code of the menu item
	 * @return the hash code of the menu item
	 */
	@Override
	public int hashCode() {
		return ((Integer) this.id).hashCode() +
				((this.name == null) ? 0 : this.name.hashCode()) +
				((this.description == null) ? 0 : this.description.hashCode()) +
				((this.variants == null) ? 0 : this.variants.hashCode()) +
				((this.defaultVariant == null) ? 0 : this.defaultVariant.hashCode());
	}

	/**
	 * Removes a recipe from the menu item
	 *
	 * @param removed the recipe to be removed
	 */
	public void remove(PermanentRecipe removed) {
		this.variants.remove(removed);
	}

	/**
	 * Returns the default recipe variant of the menu item
	 *
	 * @return the default recipe variant of the menu item
	 */
	public PermanentRecipe getDefault() {
		return this.defaultVariant;
	}

	/**
	 * Sets the default recipe of a menu item
	 *
	 * @param defaultVariant the default recipe to set
	 */
	public void setDefaultVariant(PermanentRecipe defaultVariant) {
		this.defaultVariant = defaultVariant;
	}
}

