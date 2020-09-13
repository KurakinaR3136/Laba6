package Organization;

import java.io.Serializable;

public class Address implements Serializable {
    private String street; //Поле не может быть null
    private String town; //Поле не может быть null

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
