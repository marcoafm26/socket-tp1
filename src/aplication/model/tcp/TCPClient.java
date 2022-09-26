package aplication.model.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public Socket socket;
   public DataInputStream input;
    public DataOutputStream output;
    public String ipAdress;

    final int serverPort = 6430;

    public TCPClient(String ipAdress){
        this.ipAdress = ipAdress;
    }
    public void startConection(){
        try{
            socket = new Socket(ipAdress,serverPort);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());}
    }

    public void sendTeam (byte team) throws IOException {
        output.write(team);
    }

    public byte receiveTeam() throws IOException {
        return input.readByte();
    }

    public byte receiveResult(byte number) throws IOException {
        output.write(number);
        return input.readByte();
    }

//    public byte[] result(byte chose, byte number) throws IOException {
//        output.write(new byte[]{chose,number});
//        byte time = input.readByte();
//        return resultadoGame;
//    }

}
