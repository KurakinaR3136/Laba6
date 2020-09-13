package Commands;

import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.OrganizationCollection;
import Organization.Organization;
import Utilities.WriterToFile;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

public class Save extends Commands implements CommandWithoutArg , Serializable {
    OrganizationCollection collection = new OrganizationCollection();
    WriterToFile writer = new WriterToFile();

    @Override
    public void execute(Object o, StringBuffer msg) throws IOException {
        try {
            Gson json = new Gson();
//            for (Organization organization: collection.getCollection()){
//                writer.write(json.toJson(organization)+"\n");
//            }
            writer.write(json.toJson(collection.getCollection()));
//            System.out.println("Коллекция успешно сохранена.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.insert(0,"\n[+] info: Success save\n");
    }

        @Override
        public String getName () {
            return "save";
        }
    }
