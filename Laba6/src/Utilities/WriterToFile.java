package Utilities;

import Server.Server;
import Server.ServerLevel;

import java.io.*;
import java.util.Scanner;

public class WriterToFile {
    Scanner scanner = new Scanner(System.in);

    public void write(String data) throws IOException {
        try {
            OutputStream outputStream = new FileOutputStream(new File(String.valueOf(Server.getFile())));
            byte[] dataToBytes = data.getBytes();
            outputStream.write(dataToBytes);
            System.out.println("Коллекция успешно сохранена.");
        } catch (FileNotFoundException e) {
            System.out.println("У вас нет прав доступа к этому файлу");
        }
    }

    public String getFilename() {
        Object filename = ReadFromFile.filename;
        while (filename == null) {
            System.out.print("Введите имя файла:\n~ ");
            filename = scanner.nextLine();
            if (filename.equals(""))
                return this.getFilename();
        }
        return (String) filename;
    }
}
