package Controller;

import Organization.Organization;

import javax.crypto.Cipher;
import java.io.Serializable;
import java.util.Vector;

public class Message implements Serializable {
    private Crutch crutch;
    private String description;
    private Organization organization;
    private Vector<Organization> organizations;

    public Organization getOrganizationCollection() {
        return organization;
    }

    public String getDescription() {
        return description;
    }public Message(){}
    public Message(Crutch crutch,String description,Organization organization){
        this.crutch=crutch;
        this.description=description;
        this.organization=organization;
    }
    public Message(Crutch crutch,String description,Organization organization,Vector<Organization> organizations){
        this.crutch=crutch;
        this.description=description;
        this.organization=organization;
        this.organizations=organizations;
    }
}
