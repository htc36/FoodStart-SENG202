package nic.foody.model;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import nic.foody.util.PhoneType;

/**
 * Basic info about out suppliers.
 */

public class Supplier {
    public static final String UNKNOWN_EMAIL = "Unknown email";
    public static final String UNKNOWN_URL = "Unknown URL";

    private String sid;
    private String name;
    private String address;
    private String phone;
    private PhoneType phType;
    private String email = UNKNOWN_EMAIL;
    private String url = UNKNOWN_URL;

    public Supplier(String s) {
        sid = s;
    }

    public Supplier(String s, String name) {
        sid = s;
        this.name = name;
    }

    /**
     * Constructor for class. All the required elements
     */
    public Supplier(String s, String name, String addr, String ph) {
        this(s, name, addr, ph, PhoneType.UNKNOWN, UNKNOWN_EMAIL, UNKNOWN_URL);
    }

    /**
     * Constructor for class. All elements.
     * 
     * @param s
     * @param n
     * @param addr
     * @param ph
     * @param email
     * @param url
     */
    public Supplier(String s, String n, String addr, String ph, PhoneType pt, String email, String url) {
        sid = s;
        name = n;
        address = addr;
        phone = ph;
        phType = pt;
        this.email = email;
        this.url = url;
    }

    public void setPhoneType(PhoneType pt) {
        phType = pt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String toString() {
        return "[Supplier: " + sid + ", " + name + ", " + address + ", " + phone + ", " + email + ", " + url + "]";
    }

    // Bunch of getters
    public String id() {
        return this.sid;
    }

    public String name() {
        return this.name;
    }

    public String address() {
        return this.address;
    }

    public String phone() {
        return this.phone;
    }

    public PhoneType phoneType() {
        return this.phType;
    }

    public String email() {
        return this.email;
    }

    public String url() {
        return this.url;
    }
}