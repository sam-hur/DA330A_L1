package Preperation.Client;

public class Main {
    public static void main(String[] args) {
        Thread clientThread1 = new Thread(() -> {
            EchoClient echoClient1 = new EchoClient();
            echoClient1.establish();
        });

        Thread clientThread2 = new Thread(() -> {
            EchoClient echoClient2 = new EchoClient();
            echoClient2.establish();
        });

        Thread clientThread3 = new Thread(() -> {
            EchoClient echoClient3 = new EchoClient();
            echoClient3.establish();
        });

        clientThread1.start();
        clientThread2.start();
        clientThread3.start();

        // Wait for all threads to finish
        try {
            clientThread1.join();
            clientThread2.join();
            clientThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All Done!");
        System.exit(1);
    }
}
