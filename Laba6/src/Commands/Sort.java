package Commands;

import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.Crutch;
import Controller.OrganizationCollection;

import java.io.Serializable;

/**
 * sorts the elements of a collection
 *
 * @author
 */

public class Sort extends Commands implements CommandWithoutArg, Serializable, Crutch {
	OrganizationCollection collection = new OrganizationCollection();
    @Override
    public void execute(Object o, StringBuffer msg) {
        if (collection.getSize() != 0) {
            collection.toSortArray();
            msg.insert(0,"\nКоллекция успешно отсортировна.");
            System.out.println("Коллекция успешно отсортировна.");
        } else {
            msg.insert(0,"Коллекция пустая.");
            System.out.println("Коллекция пустая.");
        }
    }

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public void toInventTheWheel() {

    }
}