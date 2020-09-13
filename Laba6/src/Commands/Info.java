package Commands;

import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.Crutch;
import Controller.OrganizationCollection;

import java.io.FileNotFoundException;
import java.io.Serializable;


public class Info extends Commands implements CommandWithoutArg, Serializable, Crutch {
    OrganizationCollection collection = new OrganizationCollection();

    @Override
    public void execute(Object o, StringBuffer msg) throws FileNotFoundException {
        msg.insert(0,"\n"+collection.getInfo());
        System.out.println(collection.getInfo());
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void toInventTheWheel() {

    }
}