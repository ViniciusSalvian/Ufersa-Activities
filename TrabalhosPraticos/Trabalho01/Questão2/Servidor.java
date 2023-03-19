package Trabalho01.Questão2;

import java.net.InetAddress;
import java.net.Socket;

public class Servidor {
  Socket cliente;
  int porta;
  public Servidor(int porta) {
    this.porta = porta;
    this.rodar();
  }
  private void rodar() {
    try {
      System.out.println("HostAddress: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("HostName: " + InetAddress.getLocalHost().getHostName());

      ImpServidor s = new ImpServidor(porta);
      Thread t = new Thread(s);
      t.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new Servidor(12345);
  }
}
