package Commands;

import Controller.*;
import Organization.Organization;
import Organization.ReadData;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class Update extends Commands implements Commandable, Serializable, Crutch {
    OrganizationCollection collection = new OrganizationCollection();
    public static ReadData readData = new ReadData();

    @Override
    public void execute(Object arg, StringBuffer msg) throws IOException {
        try {
            if (collection.isIndexBusy(Integer.parseInt((String) arg))) {
                Vector<Organization> organizations = collection.getCollection();
                Iterator<Organization> it = organizations.iterator();
                while (it.hasNext()) {
                    Organization organization1 = it.next();
                    if (organization1.getId() == Integer.parseInt((String) arg)) {
                        it.remove();
                        Organization organization = readData.getNewOrganization();
                        organization.setId(Integer.parseInt((String) arg));
                        collection.add(organization);
                        msg.insert(0,"\nОрганизация [id:" + arg + "] успешно обновлена.");
                        System.out.println("Организация [id:" + arg + "] успешно обновлена.");
                    }
                }
            } else{
                msg.insert(0,"\nОрганизации с таким id нет.");
                System.out.println("Организации с таким id нет.");
            }
        } catch (Exception e) {
            msg.insert(0,"\nТакого элемента нет в коллекции");
            System.out.println("Такого элемента нет в коллекции");
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public void toInventTheWheel() {

    }
    public static void update(Object arg, StringBuffer msg){
        OrganizationCollection collection = new OrganizationCollection();
        ReadData readData = new ReadData();
            if (collection.isIndexBusy(Integer.parseInt((String) arg))) {
                Vector<Organization> organizations = collection.getCollection();
                Iterator<Organization> it = organizations.iterator();
                while (it.hasNext()) {
                    Organization organization1 = it.next();
                    if (organization1.getId() == Integer.parseInt((String) arg)) {
                        it.remove();
                        Organization organization = readData.getNewOrganization();
                        organization.setId(Integer.parseInt((String) arg));
                        collection.add(organization);
                    }
                }
            }

    }


    public static Message update_id(String string){
        Organization organization=readData.getNewOrganization();
        Message message=new Message(new Add(),string,organization);
        return message;
    }

    public static String parseUpdateId(int id, Vector<Organization> collection,Organization org){
        try {
            String string;
            Iterator<Organization> iterator=collection.iterator();
            Organization organizationToDelete=new Organization();
            if(collection.size()!=0){
                while (iterator.hasNext()){
                    Organization organization=iterator.next();
                    if(organization.getId()==id){
                        organizationToDelete=organization;
                    }
                }
                collection.remove(organizationToDelete);
                collection.add(org);
                string="Опереация обновления проведена успешно";
                return string;
            }else {
                string="Коллекция пустая.Попробуйте добавить элемент";
                return string;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

