package Preperation;

public class ClientMain {
    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        echoClient.establish();
        System.out.println("All Done!");
        System.exit(1);
    }
}