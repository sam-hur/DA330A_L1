package Try2.Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.listen();  // listens on sep thread for incoming messages
        client.sendMessage();  // sends newlines sent via the command prompt
    }
}