package aplication.app;

import aplication.model.tcp.TCPClient;
import aplication.model.udp.UDPClient;

import java.io.IOException;
import java.util.Scanner;
public class MainClient {
    public static byte timeChose () {
        Scanner ler = new Scanner(System.in);
        byte chose;
        System.out.println("Escolha um time: ");
        System.out.println("0 - Par");
        System.out.println("1 - Impar");
        String number = ler.next();

        int numberInt = Integer.parseInt(number);
        while (numberInt != 0 && numberInt != 1) {
            System.out.println("Escolha um time valido!");
            number = ler.next();
            numberInt = Integer.parseInt(number);
        }
        if (numberInt == 0) {
            System.out.println("Voce escolheu o time Par.");
            chose = 0;
        } else {
            System.out.println("Voce escolheu o time Impar.");
            chose = 1;
        }
        return chose;
    }
    public static byte confirmMessages(byte chose, byte[] time) {
        if (chose == time[0]) {
            if (time[0] == 0) {
                System.out.println("Par escolhido com sucesso.");
            } else {
                System.out.println("Impar escolhido com sucesso.");
            }
        } else {
            chose = time[0];
            if (time[0] == 0) {
                System.out.println("Voce e do time Par. O Impar ja tinha sido escolhido.");
            } else {
                System.out.println("Voce e do time Impar. O Par ja tinha sido escolhido.");
            }
        }
        return chose;
    }
    public static byte readNumber() {
        byte numLocal;
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite um numero positivo de sua escolha:");

        // leitura e tratamento do número inserido pelo cliente
        // enquanto o número digitado for invalido ele pede a insersao de um proximo
        String number = ler.next();
        while (Integer.parseInt(number) < 0) {
            System.out.println("Insira um numero valido!");
            number = ler.next();
        }
        numLocal = (byte) Integer.parseInt(number);
        return numLocal;
    }

    public static void verifyWin(byte[] result,byte chose){
        int mod = result[0] % 2;
        System.out.println("A soma dos valores foi " + result[0]);
        if ( (mod == 0 && chose == 0) || (mod != 0 && chose == 1)){
            System.out.println("Parabens voce ganhou!");
        }else{
            System.out.println("Infelizmente voce perdeu!");
        }
    }
        public static void init() throws IOException {
            Scanner ler = new Scanner(System.in);
            System.out.println("Escolha o tipo de conexão: ");
            System.out.println("0 - UDP");
            System.out.println("1 - TCP");

            int type = ler.nextInt();
            while (type != 0 && type != 1) {
                System.out.println("Escolha uma conexão válida.");
                type = ler.nextInt();
            }

            System.out.println("Digite o IP:(incluindo pontos)");
            String ip = (String) ler.next();

            // Chama metodo responsavel por definir os times
            byte chose = timeChose();

            if (type == 0) {
                // inicializacao da conexao e definicao de times
                UDPClient udp = new UDPClient();
                byte[] choseResult = udp.startConection(ip, new byte[]{chose});
                chose = confirmMessages(chose, choseResult);

                // escolha do numero e envio do numero escolhido
                byte number = readNumber();
                byte[] result = udp.result(new byte[]{number});
                verifyWin(result,chose);
            }else {
                // inicializando e conectando o cliente ao servidor
                TCPClient tcp = new TCPClient(ip);
                tcp.startConection();

                // enviando o time escolhido e verificando se o time final vai ser o mesmo
                tcp.sendTeam(chose);
                byte team = tcp.receiveTeam();
                chose = confirmMessages(chose,new byte[]{team});
                byte number = readNumber();
                byte result = tcp.receiveResult(number);
                verifyWin(new byte[]{result},chose);
            }

        }


        public static void main (String[]args) throws IOException {
            init();
        }
    }

