package Trabalho01.Questão2;

import java.io.Serializable;

public class Message implements Serializable {
  private static final long serialVersionUID = 1L;
  String mensagem;
  int type;
  int id;
  int typeCriptography;
  String chave;

  public Message() {
  }
 

  public Message(String mensagem, int type, int id, int typeCriptography, String chave) {
    this.mensagem = mensagem;
    this.type = type;
    this.id = id;  
    this.typeCriptography = typeCriptography;
    this.chave = chave;
  }
}
