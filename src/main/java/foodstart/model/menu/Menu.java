package foodstart.model.menu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * Models a menu in the system
 */
public class Menu {
	/**
	 * A set to keep all the menu items in
	 */

	private Set<MenuItem> menuItems;

	/**
	 * The name of the menu
	 */

	private int id;

	/**
	 * The title of the menu
	 */
	private String title;

	/**
	 * The description of the menu
	 */
	private String description;

	/**
	 * Constructs an instance of a menu
	 *
	 * @param menuItems   the menu items that are in the menu
	 * @param id          the UID of the menu
	 * @param title       the name of the menu
	 * @param description a description of the menu
	 */
	public Menu(Set<MenuItem> menuItems, int id, String title, String description) {
		this.menuItems = menuItems;
		this.id = id;
		this.title = title;
		this.description = description;
	}

	/**
	 * Constructs an instance of a menu
	 * 
	 * @param menu a menu to make a menu with		
	 */
	public Menu(Menu menu) {
		this.menuItems = menu.getMenuItems();
		this.id = menu.getId();
		this.title = menu.getTitle();
		this.description = menu.getDescription();	
}

	/**
	 * Gets the id of the menu
	 *
	 * @return menu id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the title of the menu
	 *
	 * @return menu title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the menu
	 * @param title the new title for the menu
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the description of the menu
	 *
	 * @return menu description
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns a set of all menu items
	 *
	 * @return menuItems
	 */
	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}

	/**
	 * Sets the menu items that make up the menu
	 *
	 * @param menuItems the menu items that make up the menu
	 */
	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * Adds a menu item to the set of menu items in the menu
	 *
	 * @param item the menu item to add
	 * @return true if the item was added to the menu; false otherwise
	 */
	public boolean addMenuItem(MenuItem item) {
		boolean added;
		added = this.menuItems.add(item);
		return added;
	}

	/**
	 * Removes a menu item from the set of menu items in the menu
	 *
	 * @param item the menu item to remove from the menu
	 * @return true if the item was removed; false otherwise
	 */
	public boolean removeMenuItem(MenuItem item) {
		boolean removed;
		removed = this.menuItems.remove(item);
		return removed;
	}
	
    public Menu clone() {
        Set<MenuItem> itemsCopy = new HashSet<MenuItem>(menuItems);
        return new Menu(itemsCopy, id, title, description);
    }

    //TODO: figure out hashCode bug with model classes
    /*
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + id;
        result = prime * result + ((menuItems == null) ? 0 : menuItems.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Menu other = (Menu) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id != other.id)
            return false;
        if (menuItems == null) {
            if (other.menuItems != null)
                return false;
        } else if (!menuItems.equals(other.menuItems))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
	
	
}

