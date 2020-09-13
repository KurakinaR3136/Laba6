package Controller;


import java.io.*;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Commands implements Commandable, Valid, Serializable {

    private static Map<String, Commandable> commands = new TreeMap<>();
    private static ArrayList<String> history = new ArrayList<>();
    public static ArrayList<String> getHistory() {
        return history;
    }
    public static int id = 0;
    private String value;

    private String message = "";

    public Commandable getCommand(String commandname) {
        return commands.get(commandname);
    }

    public static void setCommands(Map<String, Commandable> commands) {
        Commands.commands = commands;
    }

    public void regist(Commandable... commands) {
        for (Commandable command : commands)
            Commands.commands.put(command.getName(), command);
    }
    public void addToHistory(String commandName){
        if (commandName.equals("history") == false)
            history.add(commandName);
    }

    public void executeCommand(String commandName) {
        String[] nameAndArgument = commandName.split(" ");
        StringBuffer msg = null;

        if (!commandName.equals("")) {
            if (commands.get(nameAndArgument[0]) == null) {
                System.out.println("Такой команды не существует, введите \"help\", чтобы ознакомиться со всем перечнем команд.");
            } else {
                try {
                    CommandWithoutArg commandWithoutArg = (CommandWithoutArg) commands.get(nameAndArgument[0]);
                    try {
                        String s = nameAndArgument[1];
                        System.out.println("Неверный формат команды, введите \"help\", чтобы ознакомиться с форматами команд.");
                    } catch (Exception e) {
                        commands.get(nameAndArgument[0]).execute(null, msg);
                        this.addToHistory(nameAndArgument[0]);
                    }
                } catch (Exception e) {
                    try {
                        String s = nameAndArgument[2];
                        System.out.println("Неверный формат команды, введите \"help\", чтобы ознакомиться с форматами команд.");
                    } catch (IndexOutOfBoundsException e1) {
                        try {
                            commands.get(nameAndArgument[0]).execute(nameAndArgument[1], msg);
                            this.addToHistory(nameAndArgument[0]);
                        } catch (IndexOutOfBoundsException | FileNotFoundException e2) {
                            System.out.println("Неверный формат команды, введите \"help\", чтобы ознакомиться с форматами команд.");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        return data;
    }

    @Override
    public void execute(Object o, StringBuffer msg) throws IOException {

    }

    @Override
    public String getName() {
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isValid() {
        return false;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}