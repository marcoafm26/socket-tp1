package aplication.model.udp;

import java.net.*;
import java.io.*;

public class UDPServer{

    private class UDPClient{
        InetAddress inetAddress; // 127.0.0.1
        int port;
        byte time;
        private UDPClient(InetAddress inetAddress,int port){
            this.inetAddress = inetAddress;
            this.port = port;
        }

    }
    final int serverPort = 6430;
    final int USERS = 2;
    DatagramSocket aSocket = null;
    DatagramPacket request = null;
    DatagramPacket reply = null;
    InetAddress aHost = null;
    UDPClient[] clients = new UDPClient[2];



    public void verifyTeam(byte[] buffer, UDPClient client){
        byte time = buffer[0];
        if (clients[time] == null){
            client.time = time;
            clients[time] = client;
        }else{
            if(time == 0){
                client.time = 1;
                clients[1] = client;
            }else{
                client.time = 0;
                clients[0] = client;
            }
        }
        System.out.println("Cliente "+(client.time+1) + " adicionado com sucesso.");
    }
    public void startConection(){
        try{
            System.out.println("Esperando usuarios:");
            aSocket = new DatagramSocket(serverPort);
            byte[] buffer = new byte[3];

            while(clients[0] == null || clients[1] == null){
                request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                if(buffer != null){
                    UDPClient client = new UDPClient(request.getAddress(),request.getPort());
                    verifyTeam(buffer,client);

                    buffer[0] = client.time;
                    reply = new DatagramPacket(buffer, 3, client.inetAddress, client.port);
                    aSocket.send(reply);
                }
            }
            confirmMessages();
            // retorno de confirmação de conexao clientes

        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }
    }

    public void firstReply(byte time){

    }

    public void resolve() throws IOException {
        // por padrao o primeiro client vai ser par e o segundo impar
        byte soma = 0;
        byte[] buffer = new byte[3];
        // percorre a lista de clientes e soma o valor de cada inserido
        for (UDPClient client : clients) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            soma += (buffer[0] & 0xFF);
        }
        buffer[0]=soma;

        for (UDPClient client : clients) {
            reply = new DatagramPacket(buffer, buffer.length,client.inetAddress,client.port);
            aSocket.send(reply);
        }
//        if ((soma % 2) == 0){
//            // envia para o primeiro cliente que ele venceu
//            buffer[0] = 1;
//            reply = new DatagramPacket(buffer, 3,
//                    clients[0].inetAddress, clients[0].port);
//            aSocket.send(reply);
//
//            // envia para o segundo cliente que ele perdeu
//            buffer[0] = 0;
//            reply = new DatagramPacket(buffer, 3,
//                    clients[1].inetAddress, clients[1].port);
//            aSocket.send(reply);
//            System.out.println("O time par venceu.");
//        }else{
//            // envia para o primeiro cliente que ele perdeu
//            buffer[0] = 0;
//            reply = new DatagramPacket(buffer, 3,
//                    clients[0].inetAddress, clients[0].port);
//            aSocket.send(reply);
//
//            // envia para o segundo cliente que ele venceu
//            buffer[0] = 1;
//            reply = new DatagramPacket(buffer, 3,
//                    clients[1].inetAddress, clients[1].port);
//            aSocket.send(reply);
//            System.out.println("O time impar venceu.");
//        }

    }
    public static void confirmMessages(){
        System.out.println("Conexao estabelecida com sucesso. Todos os clientes estao conectados");
    }

}