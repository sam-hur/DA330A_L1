package Preperation;

public class ServerMain {
    public static void main(String[] args) {
        EchoServer echoServer= new EchoServer();
        echoServer.establish();
    }
}