package Organization;

import Controller.OrganizationCollection;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ReadData implements Serializable {
    Scanner scanner = new Scanner(System.in);
    Organization newOrganization;
    OrganizationCollection collection = new OrganizationCollection();

    public Organization getNewOrganization() {
        newOrganization = new Organization();
        this.setName(newOrganization);
        Coordinates coords = new Coordinates();
        this.setCoordinateX(coords);
        this.setCoordinateY(coords);
        newOrganization.setCoordinates(coords);
        this.setAnnualTurnover(newOrganization);
        this.setFullName(newOrganization);
        this.setEmployeesCount(newOrganization);
        this.setOrganizationType(newOrganization);
        Address address = new Address();
        this.setTown(address);
        this.setStreet(address);
        newOrganization.setOfficialAddress(address);
        newOrganization.setCreationDate();
        newOrganization.setId(collection.getSize() + 1);
        return newOrganization;
    }

    public void setName(Organization organization) {
        System.out.println("Введите название организации:");
        System.out.print("~ ");
        String name = scanner.nextLine();
        if (name.equals("") || name.equals(null)) this.setName(organization);
        organization.setName(name);
    }

    public void setCoordinateX(Coordinates coords) {
        try {
            System.out.println("Введите координату x:");
            System.out.print("~ ");
            String x = scanner.nextLine();
            if (x.equals("") || x.equals(null)) this.setCoordinateX(coords);
            int xn = Integer.parseInt(x);
            coords.setX(xn);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"int\".");
            this.setCoordinateX(coords);
        }
    }

    public void setCoordinateY(Coordinates coords) {
        try {
            System.out.println("Введите координату y:");
            System.out.print("~ ");
            String y = scanner.nextLine();
            if (y.equals("") || y.equals(null)) this.setCoordinateY(coords);
            coords.setY(Float.parseFloat(y));
            float yn = Float.parseFloat(y);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"float\".");
            this.setCoordinateY(coords);
        }
    }

    public void setAnnualTurnover(Organization organization) {
        try {
            System.out.println("Введите годовой оборот:");
            System.out.print("~ ");
            String turnover = scanner.nextLine();
            if (turnover.equals("") || turnover.equals(null)) this.setAnnualTurnover(organization);
            double turnoverd = Double.parseDouble(turnover);
            organization.setAnnualTurnover(turnoverd);
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"double\".");
            this.setAnnualTurnover(organization);
        }
    }

    public void setFullName(Organization organization) {
        System.out.println("Введите полное название организации:");
        System.out.print("~ ");
        String name = scanner.nextLine();
        if (name.equals("") || name.equals(null)) this.setName(organization);
        organization.setFullName(name);
    }

    public void setEmployeesCount(Organization organization) {
        try {
            System.out.println("Введите количество владельцев:");
            System.out.print("~ ");
            String y = scanner.nextLine();
            if (y.equals("") || y.equals(null)) this.setEmployeesCount(organization);
            organization.setEmployeesCount(Integer.parseInt(y));
            ;
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Значение должно быть типа:\"int\".");
            this.setEmployeesCount(organization);
        }
    }

    public void setOrganizationType(Organization organization) {
        System.out.println("Введите тип организации(COMMERCIAL, PUBLIC, GOVERNMENT, TRUST)");
        System.out.print("~ ");
        String type = scanner.nextLine().toUpperCase();
        try {
            organization.setType(OrganizationType.valueOf(type.toUpperCase()));
        } catch (Exception e) {
            System.out.println("Значение поля должно соответствовать перечислинным типам.");
            this.setOrganizationType(organization);
        }
    }

    public void setTown(Address address) {
        System.out.println("Введите название города:");
        System.out.print("~ ");
        String name = scanner.nextLine();
        if (name.equals("") || name.equals(null)) this.setTown(address);
        address.setTown(name);
    }

    public void setStreet(Address address) {
        System.out.println("Введите название улицы:");
        System.out.print("~ ");
        String name = scanner.nextLine();
        if (name.equals("") || name.equals(null)) this.setStreet(address);
        address.setStreet(name);
    }
}