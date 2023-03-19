package Trabalho01.Questão2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ImpServidor implements Runnable {
  public int porta;
  public List<Clients> lista = new ArrayList<>(3);
  
  public ImpServidor(int porta) {
    this.porta = porta;
    lista.add(new Clients(1, "10.215.10.148",1234));
    lista.add(new Clients(2, "localhost",5678));
    lista.add(new Clients(3, "localhost",9101));
  }

  public void run() {
    try {
      DatagramSocket socketCliente = new DatagramSocket(porta);
      byte[] bufferRecebimento = new byte[2048];
      DatagramPacket datagramaRecebimento = new DatagramPacket(
        bufferRecebimento,
        bufferRecebimento.length
      );
      socketCliente.receive(datagramaRecebimento);
      bufferRecebimento = datagramaRecebimento.getData();
      String msg = new String(bufferRecebimento);

      String[] arrayMsg = msg.split("-");
      String realMsg = arrayMsg[0];
      int type = Integer.parseInt(arrayMsg[1]);
      // type 0 = unicast
      // type 1 = broadcast
      if (type == 0) {
        int id = Integer.parseInt(arrayMsg[2]);
        for (Clients c : lista) {
          if (c.id == id) {
           byte[] bufferEnvio = realMsg.getBytes();
            DatagramPacket datagramaEnvio = new DatagramPacket(
              bufferEnvio,
              bufferEnvio.length,
              InetAddress.getByName(c.host),
              c.port
            );
            System.out.println("Enviando mensagem para o cliente " + c.id);
            socketCliente.send(datagramaEnvio);
          }
        }
      } else {
        for (Clients c : lista) {
          byte[] bufferEnvio = realMsg.getBytes();
          DatagramPacket datagramaEnvio = new DatagramPacket(
            bufferEnvio,
            bufferEnvio.length,
            InetAddress.getByName(c.host),
            c.port
          );

          socketCliente.send(datagramaEnvio);
        }
      }
      socketCliente.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
