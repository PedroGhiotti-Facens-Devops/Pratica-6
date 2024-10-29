package br.com.valueprojects.mock_spring.models;

public class Alerta {

    private int id;
    private String mensagem;
    private Participante destinatario;

    public Alerta() {}
    public Alerta(String mensagem, Participante destinatario) { this.mensagem = mensagem; }

    public Participante getDestinatario() { return destinatario; }
    public void setDestinatario(Participante destinatario) { this.destinatario = destinatario; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

}
