package nic.foody.model;

import java.util.List;

import nic.foody.model.Supplier;

/**
 * Main class for the business. Keeps track of the model classes (suppliers
 * etc.) that we have as well as performing major functions.
 */
public class Business {
    List<Supplier> suppliers;

    public void setSuppliers(List<Supplier> s) {
        suppliers = s;
    }
}