package Utilities;

import java.io.*;

public class ReadFromFile {
   public static String filename = System.getenv("JSON");

//    public static String read() throws FileNotFoundException {
//        try {
//            InputStream stream = new FileInputStream(filename);
//            Reader reader = new InputStreamReader(stream);
//            int data = reader.read();
//            String res = "";
//            while (data != -1) {
//                res += (char) data;
//                data = reader.read();
//            }
//            return res;
//        } catch (IOException e) {
//            return null;
//        } catch (NullPointerException e) {
//            System.out.println("не указан путь до файла");
//            return null;
//        }
//    }

        public static String readFromFile(String filename) throws FileNotFoundException {
            try {
                InputStream stream = new FileInputStream(filename);
                Reader reader = new InputStreamReader(stream);
                int data = reader.read();
                String res = "";
                while (data != -1) {
                    res += (char) data;
                    data = reader.read();
                }
                return res;
            } catch (IOException e) {
                return null;
            } catch (NullPointerException e) {
                System.out.println("не указан путь до файла");
                return null;
            }
        }
}