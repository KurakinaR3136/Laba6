package Commands;

import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.OrganizationCollection;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Clear extends Commands implements CommandWithoutArg, Serializable {
	OrganizationCollection collection = new OrganizationCollection();

	@Override
	public void execute(Object o, StringBuffer msg) throws FileNotFoundException {
		collection.clear();
		msg.insert(0,"\nКоллекция успешно очищена.");
		System.out.println("Коллекция успешно очищена.");
	}

	@Override
	public String getName() {
		return "clear";
	}
}