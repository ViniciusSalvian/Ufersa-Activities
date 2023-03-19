package Trabalho01.Questão2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ImpCliente implements Runnable {
  DatagramSocket cliente = null;
  int port;
  boolean msm;

  public ImpCliente( int port, boolean msg) {
    this.msm = msg;
    this.port = port;
  }

  public void run() {
    try {
      cliente = new DatagramSocket(port);
      InetAddress endereco = InetAddress.getByName("10.215.10.148");

      System.out.println("Conectado ao servidor");
      Scanner teclado = new Scanner(System.in);
      Message msg = new Message();

      if (msm) {
        System.out.print("Digite uma mensagem: ");
        msg.mensagem = teclado.nextLine();

        System.out.println("Digite o tipo de mensagem: ");
        System.out.println("0 - Unicast");
        System.out.println("1 - Broadcast");
        msg.type = teclado.nextInt();

        if (msg.type == 0) {
          System.out.print("Digite o ID do destinatário obs: 1 até 3");
          msg.id = teclado.nextInt();
        }

        System.out.println("Enviando mensagem para o servidor...");

        String finalMessage = msg.mensagem + "-" + msg.type + "-" + msg.id+ '-';
        byte[] bufferEnvio = finalMessage.getBytes();
        System.out.println("Mensagem enviada: " + new String(bufferEnvio));
        DatagramPacket datagramaEnvio = new DatagramPacket(
          bufferEnvio,
          bufferEnvio.length,
          endereco,
          12345
        );

        cliente.send(datagramaEnvio);

        byte[] bufferRecebimento = new byte[2048];
        DatagramPacket datagramaRecebimento = new DatagramPacket(
          bufferRecebimento,
          bufferRecebimento.length
        );
        cliente.receive(datagramaRecebimento);
        bufferRecebimento = datagramaRecebimento.getData();
        System.out.println("Mensagem recebida do servidor: " + new String(bufferRecebimento));
      } else {
        byte[] bufferRecebimento = new byte[2048];
        DatagramPacket datagramaRecebimento = new DatagramPacket(
          bufferRecebimento,
          bufferRecebimento.length
        );
        cliente.receive(datagramaRecebimento);
        bufferRecebimento = datagramaRecebimento.getData();
        System.out.println("Mensagem recebida: " + new String(bufferRecebimento));
      }
      teclado.close();
      cliente.close();
      System.out.println("Conexão encerrada");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
