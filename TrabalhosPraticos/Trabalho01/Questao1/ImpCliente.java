package Trabalho01.Questao1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ImpCliente implements Runnable {
  DatagramSocket socketCliente = null;
  int id;
  int port;
  int proximo;
  String hostNext;
  boolean msg;
  Scanner sc = new Scanner(System.in);

  public ImpCliente(int id, int port, String hostNext, int proximo, boolean msg) {
    this.id = id;
    this.hostNext = hostNext;
    this.port = port;
    this.proximo = proximo;
    this.msg = msg;
  }

  public void run() {
      try {
        socketCliente = new DatagramSocket(port);

        System.out.println("Cliente rodando em: " +
        InetAddress.getLocalHost() + ":" +
        socketCliente.getLocalPort());
        InetAddress endereco = InetAddress.getByName(hostNext);
        int val = 0;
  
        if (msg) {
          try {
            System.out.println("Digite uma mensagem:");
            val = sc.nextInt();  
          } catch (Exception e) {
            System.out.println("Digite um número inteiro");
          }       
  
          String mensagem = "[" + id + " - " + val + "]";

          byte[] bufferEnvio = mensagem.getBytes();
          bufferEnvio = mensagem.getBytes();
          DatagramPacket datagramaEnvio = new DatagramPacket(
            bufferEnvio,
            bufferEnvio.length,
            endereco,
            proximo
          );
  
          socketCliente.send(datagramaEnvio);
  
          byte[] bufferRecebimento = new byte[2048];
          DatagramPacket datagramaRecebimento = new DatagramPacket(
            bufferRecebimento,
            bufferRecebimento.length
          );
          socketCliente.receive(datagramaRecebimento);
          bufferRecebimento = datagramaRecebimento.getData();
          System.out.println("Mensagem recebida por: " + id + " = " + new String(bufferRecebimento));
        } else {
          byte[] bufferRecebimento = new byte[2048];
          DatagramPacket datagramaRecebimento = new DatagramPacket(
            bufferRecebimento,
            bufferRecebimento.length
          );
  
          socketCliente.receive(datagramaRecebimento);
          bufferRecebimento = datagramaRecebimento.getData();
          String mensagem = new String(bufferRecebimento);

          System.out.println("Mensagem recebida por: " + id + " = " + mensagem);
          
          String mensagemResp;

          String[] arrayMsgProcesso = mensagem.split(" - ");
          String num = arrayMsgProcesso[1].replace("]", "");
          
          int attNumProcess = Integer.parseInt(num.trim());
          if (id%2 == 0) {
            attNumProcess = attNumProcess * 2;
          }

          mensagemResp = "[" + id + " - " + attNumProcess + "]";

          System.out.println("Mensagem enviada por: " + id + " = " + mensagemResp);
          byte[] bufferEnvio = mensagemResp.getBytes();
          bufferEnvio = mensagemResp.getBytes();
          DatagramPacket datagramaEnvio = new DatagramPacket(
            bufferEnvio,
            bufferEnvio.length,
            endereco,
            proximo
          );
          socketCliente.send(datagramaEnvio);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
          socketCliente.close();
      }
  }
}
