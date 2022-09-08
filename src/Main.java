import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Server2 server = new Server2();
                    server.listen();
            }
        }).start();

        Client client = new Client();
        try{
            client.request();
        } catch (IOException e){
            System.out.println("--Connection Shut Down--");
        }
    }
}
