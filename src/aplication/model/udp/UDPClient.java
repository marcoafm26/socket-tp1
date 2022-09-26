package aplication.model.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {

    final int serverPort = 6430;
    //  int serverPort = 6789;
    DatagramSocket aSocket = null;
    DatagramPacket request = null;
    DatagramPacket reply = null;
    InetAddress aHost = null;

    public byte[] startConection(String ipConection, byte[] message) {
        byte[] messageBytes=null;
        byte[] buffer = new byte[3];
        try {
            aSocket = new DatagramSocket();
            aHost = InetAddress.getByName(ipConection);

            messageBytes = message;
            request = new DatagramPacket(messageBytes, message.length, aHost, serverPort);
            aSocket.send(request);

            request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
        return buffer;
    }

    public byte[] result(byte[] message){
            byte[] result = null;
            byte[] send = new byte[3];
        try{
            send[0] = message[0];
            DatagramPacket request = new DatagramPacket(send,send.length,aHost,serverPort);
            aSocket.send(request);

            result = new byte[3];
            reply = new DatagramPacket(result, result.length,aHost,serverPort);
            aSocket.receive(reply);
        }catch(IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return result;
    }
}
