package Trabalho01.Questao1;

public class Cliente {
  String hostNext;
  int id;
  int port;
  int proximo;
  boolean msg;


  public Cliente(int id ,int port, String hostNext, int proximo, boolean msg) {
    this.id = id;
    this.hostNext = hostNext;
    this.port = port;
    this.proximo = proximo;
    this.msg = msg;
    this.rodar();
  }

  private void rodar() {
    try {
      ImpCliente cliente1 = new ImpCliente(id, port, hostNext, proximo, msg);
      Thread t1 = new Thread(cliente1);
      t1.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}