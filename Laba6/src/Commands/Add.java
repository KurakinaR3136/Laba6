package Commands;

import Controller.*;
import Organization.Organization;
import Organization.ReadData;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Add extends Commands implements CommandWithoutArg, CommandWithObject, Serializable,Crutch {
    OrganizationCollection collection = new OrganizationCollection();
    public  static ReadData readData = new ReadData();
    @Override
    public boolean check(Object arg) {
        return true;
    }

    @Override
    public void execute(Object o, StringBuffer msg) throws FileNotFoundException {
		collection.add(readData.getNewOrganization());
		msg.insert(0,"\nОрганизация успешно добавлена.");
        System.out.println("Организация успешно добавлена.");
    }



    public static Message add(String string){
        Organization organization=readData.getNewOrganization();
        Message message=new Message(new Add(),string,organization);
        return message;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public void toInventTheWheel() {

    }
}