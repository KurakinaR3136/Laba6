package Commands;

import Controller.CommandWithoutArg;
import Controller.Commands;
import Controller.Crutch;

import java.io.Serializable;

public class Help extends Commands implements CommandWithoutArg, Serializable, Crutch {
    @Override
    public void execute(Object o, StringBuffer msg) {
        String temp = "\nhelp : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "replace_if_greater key {element} : заменить значение по ключу, если новое значение больше старого.  key: x, y , employeesCount, annualTurnover.\n" +
                "replace_if_lower key {element} : заменить значение по ключу, если новое значение меньше старого. key: x, y , employeesCount, annualTurnover\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n";
        msg.insert(0, temp);
        System.out.println(msg);
    }

    public static String getHelp(){
        String help="\nhelp : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update_id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "replace_if_greater key {element} : заменить значение по ключу, если новое значение больше старого\n" +
                "replace_if_lower key {element} : заменить значение по ключу, если новое значение меньше старого\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n";
        return help;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void toInventTheWheel() {

    }
}
