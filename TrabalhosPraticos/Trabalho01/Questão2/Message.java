package Trabalho01.Questão2;

import java.io.Serializable;

public class Message implements Serializable {
  private static final long serialVersionUID = 1L;
  String mensagem;
  int type;
  int id;

  public Message() {
  }

  public Message(String mensagem, int type, int id) {
    this.mensagem = mensagem;
    this.type = type;
    this.id = id;  
  }
}
