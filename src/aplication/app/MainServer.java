package aplication.app;

import aplication.model.tcp.TCPServer;
import aplication.model.udp.UDPServer;

import java.io.IOException;
import java.util.Scanner;

public class MainServer {
    public static String readNumber(){
        Scanner ler = new Scanner(System.in);
        String number = ler.next();
        while(Integer.parseInt(number) < 0){
            System.out.println("Insira um numero valido!");
            number = ler.next();
        }
        return number;
    }

    public static void init() throws IOException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha o tipo de conexão: ");
        System.out.println("0 - UDP");
        System.out.println("1 - TCP");

        int type = ler.nextInt();
        while( type != 0 && type != 1){
            System.out.println("Escolha uma conexão válida.");
            type = ler.nextInt();
        }




        if (type == 0){
            // fazendo a leitura do ip para a conexao
            System.out.println("Digite o IP:(incluindo pontos)");
            String ip = (String) ler.next();

            // inicializacao da conexao
            UDPServer udpServer = new UDPServer();
            udpServer.startConection();
           // confirmMessages();
            udpServer.resolve();
        }else if(type == 1){
            TCPServer tcpServer = new TCPServer(2,6430);
            tcpServer.startConection();
            tcpServer.verifyTeam();
            tcpServer.resolve();
        }

    }

    public static void main(String[] args) throws IOException {
        init();
    }
}
