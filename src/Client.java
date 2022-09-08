import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    public void request() throws IOException, ClassNotFoundException, InterruptedException {

        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner scanner;

            while(true) {
                List<Integer> numbers = new ArrayList<Integer>();
                socket = new Socket(host, 1234);
                scanner = new Scanner(System.in);
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                //input from scanner added to list (2 Integers)
                for(int i = 1; i<3; i++) {
                    System.out.printf("Please Write Integer %s To Send It To Server: ", i);
                    try {
                        int number = Integer.parseInt(scanner.next());
                        numbers.add(number);
                    }
                    catch (NumberFormatException e){
                        System.out.println("--The Given Object Was No Integer! Calculation Not Possible! Value replaced with '0'--");
                        //input replaced with default if no Integer
                        numbers.add(0);
                    }
                }

                //output to server - List of 2 Integers
                oos.writeObject(numbers);

                //input from server - Integer result of calculation
                int message = (int) ois.readObject();
                System.out.println(String.format("The Result Of The Multiplication Of %s And %s Is %s.", numbers.get(0), numbers.get(1), message));

                System.out.println("Do You Want To Quit The Program - Y? ");
                //input from scanner and output to server - String
                String msg = scanner.next();
                oos.writeObject(msg);

                ois.close();
                oos.close();
                Thread.sleep(100);
            }
    }
}
