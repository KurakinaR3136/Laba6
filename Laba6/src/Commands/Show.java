package Commands;


import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.Crutch;
import Controller.OrganizationCollection;
import Organization.Organization;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class Show extends Commands implements CommandWithoutArg, Serializable, Crutch {
	OrganizationCollection collection = new OrganizationCollection();

	@Override
	public void execute(Object o, StringBuffer msg) throws FileNotFoundException {
		if (collection.getSize() == 0){
			msg.insert(0,"\nКоллекция пустая.");
			System.out.println("Коллекция пустая.");
		}
		else for (Organization organization : collection.getCollection()){
			System.out.println(organization.getInfo());
			msg.insert(0,organization.getInfo());
		}
	}

	@Override
	public String getName() {
		return "show";
	}

	@Override
	public void toInventTheWheel() {

	}

	public static String ShowElement(Vector<Organization> collection){
		String string="";
			if(collection.size()!=0){

				Iterator<Organization> iterator=collection.iterator();
				while (iterator.hasNext()){
					string=string+iterator.next().toString()+ '\n';

				}
				return string;

			}else {string="Коллекция пустая ";}
			return string;
	}
}
