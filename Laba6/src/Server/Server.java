package Server;

import java.io.File;

public class Server{
    private static File file;

    public static File getFile() {
        return file;
    }
  //// java18 -jar name.jar 1431 /home/s423241/txes.txt
    public static void main(String[] args) {
        if(args.length!=2){
            System.out.println("Ожидался порт и имя файла");
            return;
        }
        try {
            if (args[1].equals("") || args[1]==null){
                System.out.println("Вы не задали файл");
                System.out.println("Но я это предвидел, схватил за рукав и создал свой файл");
                file = new File("extraFile.txt");
                file.createNewFile();
            }else{
                file = new File(args[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        new ServerLevel(Integer.parseInt(args[0])).MainServer();
    }
}
