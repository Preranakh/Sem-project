package Admin;

import javafx.beans.property.SimpleStringProperty;

public class ProfileData {

    private final SimpleStringProperty fname;
    private final SimpleStringProperty lname;
    private final SimpleStringProperty dmat;
    private final SimpleStringProperty permanentadd;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty citizen;
    private final SimpleStringProperty password;

    public ProfileData(String fname, String lname, String dmat, String permanentadd, String contact, String citizen, String password) {
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.dmat = new SimpleStringProperty(dmat);
        this.permanentadd = new SimpleStringProperty(permanentadd);
        this.contact = new SimpleStringProperty(contact);
        this.citizen = new SimpleStringProperty(citizen);
        this.password = new SimpleStringProperty(password);
    }

    public String getFname() {
        return fname.get();
    }

    public SimpleStringProperty fnameProperty() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public String getLname() {
        return lname.get();
    }

    public SimpleStringProperty lnameProperty() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public String getDmat() {
        return dmat.get();
    }

    public SimpleStringProperty dmatProperty() {
        return dmat;
    }

    public void setDmat(String dmat) {
        this.dmat.set(dmat);
    }

    public String getPermanentadd() {
        return permanentadd.get();
    }

    public SimpleStringProperty permanentaddProperty() {
        return permanentadd;
    }

    public void setPermanentadd(String permanentadd) {
        this.permanentadd.set(permanentadd);
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getCitizen() {
        return citizen.get();
    }

    public SimpleStringProperty citizenProperty() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen.set(citizen);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
