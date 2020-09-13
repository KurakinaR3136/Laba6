package Commands;

import Controller.Commandable;
import Controller.Commands;
import Controller.Crutch;
import Controller.OrganizationCollection;
import Organization.Organization;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import java.util.stream.Stream;

public class Remove_by_id extends Commands implements Commandable, Serializable, Crutch {
    OrganizationCollection collection = new OrganizationCollection();
    @Override
    public void execute(Object arg, StringBuffer msg) throws IOException {
        if (collection.getSize() == 0) System.out.println("Коллекция пустая.");
        else {
            int id = 0;
            try {
                id = Integer.parseInt((String) arg);
            } catch (NumberFormatException exp) {
                System.out.println("Нет организации с таким ID");
            }

            String ans = "Нет организации с таким id.";
            Iterator<Organization> it = (Iterator<Organization>) collection.getCollection().iterator();
            while (it.hasNext()) {
                Organization organization = (Organization) it.next();
                int humanId = (int) organization.getId();
                if (id == humanId) {
                    it.remove();
                    ans = "Организация успешно удалена.";
                }
            }
        }
    }

    public static void remove_by_id(int id, Vector<Organization> collection){

        try {
            collection.removeIf((i)->{
                return i.getId()==id;
            });

        }catch (Exception e){e.printStackTrace();}



    }



    @Override
    public String getName() {
        return "remove_by_id";
    }


    @Override
    public void toInventTheWheel() {

    }
}
