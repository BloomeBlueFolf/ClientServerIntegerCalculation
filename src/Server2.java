import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

//Replacing statement with logging messages

public class Server2 {

    private static ServerSocket server;
    private static final int port = 1234;

    public void listen(){

        try {
            server = new ServerSocket(port);
            System.out.println("--Server Started--");

            while(true){
                System.out.println("--Waiting for Client Requests--");
                //listening at socket
                Socket socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                try {
                    //List of 2 Integers - input from client
                    List<Integer> message = (List<Integer>) ois.readObject();
                    System.out.println("Message Received: " + message);

                    //calculation method
                    int product = message.get(0) * message.get(1);

                    //calculation msg back to client
                    oos.writeObject(product);

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    System.out.println("--An Error Occured! Message Received That Was No List Of Two Integers--");
                }

                //input from client - shutdown?
                String msg = (String) ois.readObject();

                ois.close();
                oos.close();
                socket.close();
                if (msg.equalsIgnoreCase("y")){
                    System.out.println("--Shutting Down Socket Server--");
                    break;
                    }
            }
        }
        catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e ){
            e.printStackTrace();
            System.out.println("--A Failure Occured! Please restart the program--");
        }
        try {
            //stop listening on port
            server.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
