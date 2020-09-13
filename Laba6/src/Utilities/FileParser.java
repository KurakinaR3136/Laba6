package Utilities;

import Controller.OrganizationCollection;
import Organization.*;
import Server.ServerLevel;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

public class FileParser {
    OrganizationCollection collection = new OrganizationCollection();

    public static void parse(String data) {
        if (data == null) System.out.println("Указанный файл не найден. Коллекция пустая.");
        else {
            try {
                Type type = new TypeToken<Vector>() {
                }.getType();
                Vector<LinkedTreeMap> organizations = new Gson().fromJson(data, type);
                for (LinkedTreeMap params : organizations) {
                    Organization organization = new Organization();
                    organization.setName((String) params.get("name"));
                    organization.setFullName((String) params.get("fullName"));
                    organization.setAnnualTurnover((Double) params.get("annualTurnover"));
                    organization.setEmployeesCount(((Double) params.get("employeesCount")).intValue());
                    LinkedTreeMap addressParams = (LinkedTreeMap) params.get("officialAddress");
                    Address address = new Address();
                    address.setStreet((String) addressParams.get("street"));
                    address.setTown((String) addressParams.get("town"));
                    organization.setOfficialAddress(address);
//                    if (params.get("id") != null) organization.setId(((Double) params.get("id")).intValue());
//                    else organization.setId(collection.getSize() + 1);
                    organization.setType(OrganizationType.valueOf((String) params.get("type")));
                    organization.setCreationDate();
                    LinkedTreeMap coordParams = (LinkedTreeMap) params.get("coordinates");
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(((Double) coordParams.get("x")).intValue());
                    coordinates.setY(((Double) coordParams.get("y")).floatValue());
                    organization.setCoordinates(coordinates);
                    ServerLevel.collection.add(organization);
                }
            } catch (NullPointerException e) {
                System.out.println("В файле указаны некорретные данные. Коллекция пустая.");
            }
            System.out.println("Коллекция успешно заполнена.");
        }
    }
}