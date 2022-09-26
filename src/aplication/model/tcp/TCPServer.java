package aplication.model.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class TCPServer {
    ServerSocket serverSocket;
    List<TCPUser> clients;
    final int numClients;
    final int serverPort;

    public TCPServer(int numClients, int serverPort) {
        this.numClients = numClients;
        this.serverPort = serverPort;
        this.clients = new LinkedList<>();
    }

    private class TCPUser {
        DataOutputStream output;
        DataInputStream input;

        public TCPUser(DataOutputStream output, DataInputStream input) {
            this.output = output;
            this.input = input;
        }
    }


    public void startConection() throws IOException {
        try {
            int clientConnected = 0;
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Aguardando clientes conectarem:");
            while (clientConnected != numClients) {
                Socket socket = serverSocket.accept();
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                TCPUser client = new TCPUser(output, input);
                clients.add(client);
                clientConnected++;
                System.out.println("Cliente "+clientConnected + " conectado com sucesso.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "erro");

        }

    }

    public void verifyTeam() throws IOException {
        byte team_Cliente_1 = clients.get(0).input.readByte();
        byte team_Cliente_2 = clients.get(1).input.readByte();
        if(team_Cliente_1 == team_Cliente_2){
            if(team_Cliente_2 == 0){
                clients.get(0).output.write(new byte[]{0});
                clients.get(1).output.write(new byte[]{1});
            }else{
                clients.get(0).output.write(new byte[]{1});
                clients.get(1).output.write(new byte[]{0});
            }
        }else{
            clients.get(0).output.write(new byte[]{team_Cliente_1});
            clients.get(1).output.write(new byte[]{team_Cliente_2});
        }
    }

    public void resolve() throws IOException {
        byte soma = 0;
        for (TCPUser client: clients) {
            soma += client.input.readByte();
        }
        for (TCPUser client: clients) {
            client.output.write(soma);
        }
    }
}
