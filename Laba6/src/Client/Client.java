package Client;

public class Client {

    // java18 -jar name.jar localhost 1431


    public static void main(String[] args) throws Exception{
        if(args.length!=2){
            System.out.println("Ожидася хост и порт");
            return;
        }
        new ClientWork(args[0],Integer.parseInt(args[1])).StartClient();
    }
}
