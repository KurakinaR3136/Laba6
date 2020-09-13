package Client;

import Commands.*;
import Controller.Message;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWork {
    //-------------------------------------------------------------------------------
    // settings for Server
    public static List<String> executed_scripts = new ArrayList<>();
    public static DatagramChannel сlientChannel;
    private SocketAddress serverAddress;
    public static Message sendMessage;
    public static Message receivedMessage;
    private String msg = "";
    ByteBuffer byte_buff;
    byte[] buffer;
    static  public BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    //-------------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------------
    public ClientWork(String hostname,int port)  {
        // start server
        try {
            сlientChannel =  DatagramChannel.open();
            сlientChannel.configureBlocking(false);
            DatagramSocket socketClient = сlientChannel.socket();

            socketClient.bind(null);

            serverAddress = new InetSocketAddress(hostname, port);
        }catch (Exception e){e.printStackTrace();}
    }
    //-------------------------------------------------------------------------------
    // Main
    //-------------------------------------------------------------------------------
    public void StartClient()  {
       try {
           byte_buff =  ByteBuffer.allocateDirect(8 * 1024);


           System.out.println("Пожалуйста, введите команду help, для того что бы получить список команд, используйте \"help\".");

           while (true){
               Thread.sleep(800);
               getData();


               String line = bufferedReader.readLine();
               sendMessage=selectCommand(line);
               sendData(sendMessage);
           }
       }catch (Exception e){e.printStackTrace();}
    }
    //-------------------------------------------------------------------------------
    // Получение данных от сервера
    //-------------------------------------------------------------------------------
    private void getData() {
       try {
           if(сlientChannel.receive(byte_buff) != null){
               byte_buff.flip();
               buffer = new byte[byte_buff.remaining()];
               byte_buff.get(buffer);
               receivedMessage=Message.class.cast(deserialize(buffer));
               //msg = new String(buffer);
               System.out.println(receivedMessage.getDescription());
               byte_buff.clear();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    //-------------------------------------------------------------------------------
    // Отправка данных сервера
    //-------------------------------------------------------------------------------
    private  void sendData(Message message) throws IOException {
            buffer=serialize(message);
            byte_buff.put(buffer);
            byte_buff.flip();
            сlientChannel.send(byte_buff,serverAddress);
            byte_buff.clear();




    }

    public static Message selectCommand(String string){
        try {
            Message extraMessage;
            String[] args=string.split(" ");
            String command;
            switch (args[0]){
                case"add":
                    extraMessage=Add.add(args[0]);
                    return extraMessage;
                case "clear":
                    command="clear";
                    extraMessage=new Message(null,command,null);
                    return extraMessage;
                case "count_less_than_official_address":
                    command="count_less_than_official_address";
                    return null;
                case "execute_script":
                    try {
                        if(args.length==2){
                            extraMessage=Execute_script.execute_script(args[1]);;
                            return extraMessage;
                        }else {
                            System.out.println("Аргументов должно быть ровно 2 !");
                            selectCommand(bufferedReader.readLine());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                case "help":
                    command="help";
                    extraMessage=new Message(new Help(),command,null);
                    return extraMessage;
                case "info":
                    command="info";
                    extraMessage=new Message(new Info(),command,null);
                    return  extraMessage;
                case "remove_by_id":
                    try {
                        if(args.length==2){
                            command="remove_by_id "+args[1];
                            extraMessage=new Message(null,command,null);
                            return extraMessage;
                        }else{
                            System.out.println("Аргументов должно быть ровно 2 !");
                            selectCommand(bufferedReader.readLine());
                        }
                    }catch (Exception e){e.printStackTrace();}

                case "replace_if_greater":
                    try{
                        if(args.length==3){
                           command="replace_if_greater "+args[1]+ " "+args[2];
                           String argument=args[1]+" "+args[2];
                           if(Replace_if_greater.validator(argument)==true){
                               extraMessage= new Message(null,command,null);
                               return extraMessage;
                           }else {
                               System.out.println("Валидация не пройдена.Ключ и значение  несовместимы");
                               selectCommand(bufferedReader.readLine());
                           }


                        }else{
                            System.out.println("Аргументов должно быть ровно 3 !");
                            selectCommand(bufferedReader.readLine());
                        }
                    }catch (Exception e){e.printStackTrace();}

                case "replace_if_lower":
                    try{
                        if(args.length==3){
                            command="replace_if_lower "+args[1]+ " "+args[2];
                            String argument=args[1]+" "+args[2];
                            if(Replace_if_lower.validator(argument)==true){
                                extraMessage= new Message(null,command,null);
                                return extraMessage;
                            }else {
                                System.out.println("Валидация не пройдена.Ключ и значение  несовместимы");
                                selectCommand(bufferedReader.readLine());
                            }


                        }else{
                            System.out.println("Аргументов должно быть ровно 3 !");
                            selectCommand(bufferedReader.readLine());
                        }
                    }catch (Exception e){e.printStackTrace();}
                case "update_id":
                    try{
                        if(args.length==2){
                            command="update_id " + args[1];
                            extraMessage=Update.update_id(command);
                            return  extraMessage;

                        } else{
                            System.out.println("Аргументов должно быть ровно 2!");
                            selectCommand(bufferedReader.readLine());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                case "exit":
                    System.exit(0);
                    return null;
                case "show":
                    command="show";
                    extraMessage=new Message(null,command,null);
                    return  extraMessage;
                case "sort":
                    command="sort";
                    extraMessage=new Message(null,command,null);
                    return extraMessage;

                default:

                    System.out.println("Неизвестная команда");
                    try {
                        selectCommand(bufferedReader.readLine());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
