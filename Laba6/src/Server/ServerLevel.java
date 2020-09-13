package Server;

import Commands.*;
import Controller.Commands;
import Controller.Message;
import Controller.OrganizationCollection;
import Organization.Organization;
import Utilities.FileParser;
import Utilities.ReadFromFile;
import Utilities.WriterToFile;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ServerLevel {
    //-------------------------------------------------------------------------------
    // settings for Server
    //-------------------------------------------------------------------------------
    static Logger LOGGER = Logger.getLogger(ServerLevel.class.getName());
    private static StringBuffer msgs;
    private DatagramChannel  server;
    private SocketAddress clientAddress;
    public static  WriterToFile writer = new WriterToFile();
    public static File file;

public Organization organization = new Organization();
public static String filename="kompanii.txt";
    //-------------------------------------------------------------------------------
    private int session_token;
    private int sizeBuff =  8 * 1024;
    private  static Gson json = new Gson();
    //-------------------------------------------------------------------------------
    private ByteBuffer byte_buff = ByteBuffer.allocateDirect(8 * 1024);
    private String msg;
    private StringBuffer temp;
    //-------------------------------------------------------------------------------
    private static Map<String, Commands> commandMap = new TreeMap<>();
    private OrganizationCollection organizationCollection;
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    public static Message receiveMessage=new Message();
    public static Message sendMessage=new Message();
    public static  Vector<Organization> collection=new Vector<Organization>();
    public ServerLevel(int port)  {
        try {

            organizationCollection = new OrganizationCollection();

            commandMap.put("help", new Help());
            commandMap.put("info", new Info());
            commandMap.put("show", new Show());
            commandMap.put("add", new Add());
            commandMap.put("Count_less_than_official_address", new Count_less_than_official_address());
            commandMap.put("update", new Update());
            commandMap.put("save", new Save());
            commandMap.put("clear", new Clear());
            commandMap.put("execute_script", new Execute_script());
            commandMap.put("remove_by_id", new Remove_by_id());
            commandMap.put("replace_if_greaterr", new Replace_if_greater());
            commandMap.put("replace_if_lower", new Replace_if_lower());
            commandMap.put("Sort", new Sort());

            // start server
            LOGGER.log(Level.INFO, "Открытие магического портала для передачи данных (шутка)");
            server =  DatagramChannel.open();
            // неблокирующий режим канала
            server.configureBlocking(false);
            InetSocketAddress  addressServer = new InetSocketAddress(port);

            LOGGER.log(Level.INFO, "Связываем пространство и время (шутка: присваивоение имени сокету)");
            server.bind(addressServer);
            System.out.println("Server Started: " + addressServer);
        }catch (Exception e){e.printStackTrace();}
    }
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    public void MainServer()  {
       try {

           FileParser.parse(ReadFromFile.readFromFile(String.valueOf(Server.getFile())));

           //System.out.println(collection.size());
           byte_buff = ByteBuffer.allocate(sizeBuff);

           while (true){
               clientAddress = server.receive(byte_buff);

               if(listenConnect()){
                   temp = new StringBuffer();

                   readCommand();
                   sendDataClnt();

                   temp = null;
                   System.gc();
               }
           }
       }catch (Exception e){e.printStackTrace();}
       }

       public static void Save(){
           try {

//            for (Organization organization: collection.getCollection()){
//                writer.write(json.toJson(organization)+"\n");
//            }
               writer.write(json.toJson(collection));
//            System.out.println("Коллекция успешно сохранена.");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

    //-------------------------------------------------------------------------------
    // Модуль прослушивания и соединения с клиентом (модуль приема подключений)
    //-------------------------------------------------------------------------------
    public boolean listenConnect(){
        if(clientAddress != null){

            session_token = (int) getRandomIntegerBetweenRange(1,10000);
            return true;
        }
        else
            return false;
    }
    //-------------------------------------------------------------------------------
    // Модуль чтения команд
    //-------------------------------------------------------------------------------
    private void readCommand() throws IOException, ClassNotFoundException {
        byte_buff.flip();
        byte[] buffer = new byte[byte_buff.remaining()];
        byte_buff.get(buffer);
        receiveMessage=Message.class.cast(deserialize(buffer));
       // System.out.println("[+] #info#\t get data from user : "+receiveMessage.getDescription());

        startCommand(receiveMessage);

        // подготовка данных перед отправкой
        //msg = "[+] #info#\t User --> [ id_token: " + session_token + "]\t command --> " + msg;
        //System.out.println(msg);

        byte_buff.clear();
        Save();
        byte_buff.put(serialize(sendMessage));
    }
    //-------------------------------------------------------------------------------
    // Модуль выполнения команды
    //-------------------------------------------------------------------------------
    private void startCommand(Message receiveMessage)  {
            getFromMessage(receiveMessage.getDescription(),receiveMessage.getOrganizationCollection());



        /*try {
            Commands commands = commandMap.entrySet().stream().filter(k -> k.getKey().toLowerCase().equals(msg.toLowerCase())).findAny().get().getValue();
            if(commands != null)
                commands.execute(organizationCollection, temp);
            System.out.println("[______________________]:" + temp.toString());
            msg += temp.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

         */
    }
    //-------------------------------------------------------------------------------
    // Модуль отправки данных клиенту (модуль отправки ответов клиенту)
    //-------------------------------------------------------------------------------
    private boolean sendDataClnt() throws IOException{
        byte_buff.flip();
        server.send(byte_buff, clientAddress);
        byte_buff.clear();

        System.out.println("[+] #info#\tsuccess sending data to client");
        return true;
    }
    //-------------------------------------------------------------------------------
    // Функция для рандомной генерации уникального номера сессии
    //-------------------------------------------------------------------------------
    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = ((int)(Math.random()*((max-min)+1))+min);
        return x;
    }


    public static void getFromMessage(String description, Organization organization){
        try {
            String[] strings=description.split(" ");
            switch (strings[0]){
                case "add":
                    collection.add(organization);
                    sendMessage=new Message(null,"На сервер добавден следующий элемент \n"+organization.toString(),null);
                    break;
              //  case "sort":
               //     sendMessage= new Message(null, organization.compareTo(OrganizationCollection),null);
                case "info":
                    sendMessage=new Message(null,OrganizationCollection.Info(collection),null);
                    break;
                case "update_id":
                    //Update.update(Integer.parseInt(strings[1]),msgs);
                    sendMessage=new Message(null,Update.parseUpdateId(Integer.parseInt(strings[1]),collection,organization), null);
                    break;
                case "help":
                    sendMessage=new Message(null,Help.getHelp(),null);
                    break;
                case "show":
                    sendMessage=new Message(null,Show.ShowElement(collection),null);
                    break;
                case "remove_by_id":
                    Remove_by_id.remove_by_id(Integer.parseInt(strings[1]),collection);
                    sendMessage=new Message(null,"Элемент с id "+strings[1] +" аннигилирован",null);
                    break;

                case "clear":
                    collection.clear();
                    sendMessage=new Message(null,"Теперь тут пусто",null);
                    break;
                case "replace_if_greater":
                    Replace_if_greater.replace(strings[1],strings[2],collection);
                    sendMessage = new Message(null, "Элемент успешно обновлен", null);
                    break;
                case "replace_if_lower":
                    Replace_if_lower.replace(strings[1],strings[2],collection);
                    sendMessage = new Message(null, "Элемент успешно обновлен", null);
                    break;



                default:
                    System.out.println("не знаю что это");


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        in.close();
        is.close();
        return is.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
}
