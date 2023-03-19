package Trabalho01.Questão2;

public class Cliente {
  int port;
  boolean msm;

  public Cliente(int port, boolean msm) {
    this.port = port;
    this.msm = msm;
    this.rodar();
  }

  private void rodar() {
    try {
      ImpCliente c = new ImpCliente(port, msm);
      Thread t = new Thread(c);
      t.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
