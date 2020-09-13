package Commands;

import Client.Client;
import Client.ClientWork;
import Controller.*;
import Utilities.FileParser;
import Utilities.ReadFromFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import static Client.ClientWork.bufferedReader;

public class Execute_script extends Commands implements Commandable, Serializable,Crutch {
//    String name = "execute_script";
    OrganizationCollection collection = new OrganizationCollection();
    Commands invoker = new Commands();

    private static String scriptName;

    public static String getScriptName() {
        return scriptName;
    }

    @Override
    public void execute(Object arg, StringBuffer msg) {
        try {
            String data = ReadFromFile.readFromFile((String) arg);
            Commands command = new Commands();
            if (data != null) {
                String[] commands = data.split("\n|\r\n");
                for (int i = 0; i < commands.length; i++) {
                    if (!(commands[i].equals(""))) {
                        if (!(commands[i].equals("execute_script " + arg))) {
                            try {
                                String[] commandAndName = commands[i].split(" ");
                                CommandWithObject commandWithObject = (CommandWithObject) invoker.getCommand(commandAndName[0]);
                                commandWithObject.execute(invoker.getCommand(commandAndName[0]),msg);
                            } catch (Exception e) {
                                System.out.println("Команда \"" + commands[i] + "\":");
                                command.executeCommand(commands[i]);
                                System.out.println();
                            }

                        } else System.out.println("Команда \"" + commands[i] + "\": невыполнима.");
                    }
                }
            } else System.out.println("Указанный файл не найден.");
        } catch (NullPointerException | FileNotFoundException e) {

        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public void toInventTheWheel() {

    }

    public  static Message execute_script(String path) {
        try {
            Message extraMessage;
            String command;
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] args = line.split(" ");
                switch (args[0]) {
                    case "add":
                        scriptName="add";
                        extraMessage = Add.add(args[0]);
                        return extraMessage;
                    case "clear":
                        scriptName="clear";
                        command = "clear";
                        extraMessage = new Message(null, command, null);
                        return extraMessage;
                    case "count_less_than_official_address":
                        scriptName="count_less_than_official_address";
                        command = "count_less_than_official_address";
                        return null;
                    case "execute_script":
                        try {
                            if (args.length == 2) {
                                String string = args[1];
                                if (ClientWork.executed_scripts.contains(string)) {
                                    System.out.println("Вы пойманы за руку как дешевка!");
                                    break;
                                }
                                try {
                                    File dir1 = new File(args[1]);
                                    if (dir1.exists()) {
                                        ClientWork.executed_scripts.add(string);
                                        execute_script(string);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                System.out.println("Аргументов должно быть ровно 2 !");
                                ClientWork.selectCommand(bufferedReader.readLine());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    case "help":
                        scriptName="help";
                        command = "help";
                        extraMessage = new Message(new Help(), command, null);
                        return extraMessage;
                    case "info":
                        scriptName="info";
                        command = "info";
                        extraMessage = new Message(new Info(), command, null);
                        return extraMessage;
                    case "remove_by_id":
                        scriptName="remove_by_id";
                        try {
                            if (args.length == 2) {
                                command = "remove_by_id " + args[1];
                                extraMessage = new Message(null, command, null);
                                return extraMessage;
                            } else {
                                System.out.println("Аргументов должно быть ровно 2 !");
                                ClientWork.selectCommand(bufferedReader.readLine());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    case "remove_if_greater":
                        scriptName="remove_if_greater";
                        try {
                            if (args.length == 2) {
                                command = "remove_if_greater " + args[1];
                                return null;
                            } else {
                                System.out.println("Аргументов должно быть ровно 2 !");
                                ClientWork.selectCommand(bufferedReader.readLine());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    case "replace_if_lower":
                        scriptName="replace_if_lower";
                        try {
                            if (args.length == 2) {
                                command = "replace_if_lower " + args[1];
                                return null;
                            } else {
                                System.out.println("Аргументов должно быть ровно 2 !");
                                ClientWork.selectCommand(bufferedReader.readLine());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    case "exit":
                        scriptName="exit";
                        command = "exit";
                        return null;
                    case "show":
                        scriptName="show";
                        command = "show";
                        extraMessage = new Message(null, command, null);
                        return extraMessage;
                    default:

                        System.out.println("Неизвестная команда");
                        try {
                            ClientWork.selectCommand(bufferedReader.readLine());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

