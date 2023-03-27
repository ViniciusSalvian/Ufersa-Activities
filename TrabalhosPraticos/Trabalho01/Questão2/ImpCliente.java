package Trabalho01.Questão2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ImpCliente implements Runnable {
  DatagramSocket cliente = null;
  int port;
  boolean msm;

  public ImpCliente(int port, boolean msg) {
    this.msm = msg;
    this.port = port;
  }

  public void run() {
    try {
      cliente = new DatagramSocket(port);
      InetAddress endereco = InetAddress.getByName("localhost");

      System.out.println("Conectado ao servidor");
      Scanner teclado = new Scanner(System.in);
      Message msg = new Message();
      CifraVigenere cifraVigenere;
      String cypherText = "";
      String decryptedText = "";
      String finalMessage = "";

      System.out.println("Cifra de Vernam: 0; Cifra de Vigenere: 1; Cifra AES: 2");
      System.out.println("Selecione o tipo de criptografia a ser aplicada: ");
      msg.typeCriptography = teclado.nextInt();
      System.out.println("Digite a chave: ");
      msg.chave = teclado.next();

      if (msm) {
        System.out.print("Digite uma mensagem: ");
        msg.mensagem = teclado.nextLine();

        System.out.println("Digite o tipo de mensagem: ");
        System.out.println("0 - Unicast");
        System.out.println("1 - Broadcast");
        msg.type = teclado.nextInt();

        if (msg.type == 0) {
          System.out.print("Digite o ID do destinatário obs: 1 até 3 -> ");
          msg.id = teclado.nextInt();         
        }

        finalMessage = msg.mensagem + "-" + msg.type + "-" + msg.id + '-' + msg.typeCriptography + '-' + msg.chave;

        if (msg.typeCriptography == 1) {         
          cifraVigenere = new CifraVigenere(msg.chave);
          cypherText = cifraVigenere.encrypt(finalMessage);
          System.out.println("Mensagem cifrada: " + cypherText);
          decryptedText = cifraVigenere.decrypt(cypherText);
          System.out.println("Mensagem decifrada: " + decryptedText);
        }

        System.out.println("Enviando mensagem para o servidor...");

        byte[] bufferEnvio = cypherText.getBytes();
        System.out.println("Mensagem enviada: " + new String(bufferEnvio));
        DatagramPacket datagramaEnvio = new DatagramPacket(
            bufferEnvio,
            bufferEnvio.length,
            endereco,
            12345);

        cliente.send(datagramaEnvio);

        byte[] bufferRecebimento = new byte[2048];
        DatagramPacket datagramaRecebimento = new DatagramPacket(
            bufferRecebimento,
            bufferRecebimento.length);
        cliente.receive(datagramaRecebimento);
        bufferRecebimento = datagramaRecebimento.getData();

        System.out.println("Mensagem recebida do servidor: " + new String(bufferRecebimento) +
            "\n" + "Mensagem decriptografada: " + decryptedText);
      } else {

        byte[] bufferRecebimento = new byte[2048];
        DatagramPacket datagramaRecebimento = new DatagramPacket(
            bufferRecebimento,
            bufferRecebimento.length);
        cliente.receive(datagramaRecebimento);
        bufferRecebimento = datagramaRecebimento.getData();
        String msgRecebida = new String(bufferRecebimento);
        String [] msgSplit = msgRecebida.split("-"); 
        String newMessage = msgSplit[3];
        System.out.println( "Nova mensagem: " + newMessage);

        int tipoCriptografia = Integer.parseInt(newMessage);

        if (tipoCriptografia == 1) {
          cifraVigenere = new CifraVigenere(msg.chave);
          System.out.println("Chave: " + msg.chave);
          System.out.println("testando " + decryptedText);
          decryptedText = cifraVigenere.decrypt(msgRecebida);
          System.out.println("Mensagem decriptografada do cao: " + decryptedText);
        }

        System.out.println("Mensagem recebida do servidor: " + new String(bufferRecebimento) +
            "\n" + "Mensagem decriptografada: " + decryptedText);
      }
      teclado.close();
      cliente.close();
      System.out.println("Conexão encerrada");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
