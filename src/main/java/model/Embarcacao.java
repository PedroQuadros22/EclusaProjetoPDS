
package model;

import java.util.Objects;

public abstract class Embarcacao{
    private Tamanho tamanho;
    private String portoOrigem;
    private String portoDestino;
    private String pais;
    private int codigoDeIdentificacao;
    private Capitao capitao;
    private String sentido;

    public Embarcacao(){}
    public Embarcacao(Tamanho tamanho, String portoOrigem, String portoDestino, String pais, int codigoDeIdentificacao, Capitao capitao, String sentido){
        this.tamanho=tamanho;
        setPortoOrigem(portoOrigem);
        setPortoDestino(portoDestino);
        setPais(pais);
        setCodigoDeIdentificacao(codigoDeIdentificacao);
        this.capitao=capitao;
        setSentido(sentido);
    }

    public String getPortoOrigem() {
        return portoOrigem;
    }

    public void setPortoOrigem(String portoOrigem) {
        if(portoOrigem!=null){
            this.portoOrigem = portoOrigem;
        }
    }
    public String getNome(){
        return capitao.getNome();
    }

    public String getPortoDestino() {
        return portoDestino;
    }

    public void setPortoDestino(String portoDestino) {
        if(portoDestino!=null){
            this.portoDestino = portoDestino;
        }
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        if(pais!=null){
            this.pais = pais;
        }
    }

    public int getCodigoDeIdentificacao() {
        return codigoDeIdentificacao;
    }

    public void setCodigoDeIdentificacao(int codigoDeIdentificacao) {
        if(codigoDeIdentificacao>0){
            this.codigoDeIdentificacao = codigoDeIdentificacao;
        }
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        if(sentido!=null){
            this.sentido = sentido;
        }
    }
    public String tipoEmbarcacao;
    public String mensagemEmbarcacao;
    
    public void entrar(Eclusa eclusa){
        if(eclusa.getOcupada()==false && eclusa.tamanhoFila()>0){
            if((eclusa.getStatus().equals("seca")&&eclusa.getFila().get(0).getSentido().equals("Subir"))||(eclusa.getStatus().equals("cheia")&&eclusa.getFila().get(0).getSentido().equals("Descer"))){
                eclusa.setOcupada(true);
                eclusa.setEmbarcacaoNaEclusa(eclusa.getFila().get(0));
                eclusa.removerDaFila();
                
              }
        }
    }
    public void sair(Eclusa eclusa){
        if(eclusa.getStatus().equals("seca")||eclusa.getStatus().equals("cheia")){
            eclusa.setOcupada(false);
            eclusa.removerMap();
            eclusa.setEmbarcacaoNaEclusa(null);
        }
    }
    
        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Embarcacao embarcacao = (Embarcacao) obj;
        return codigoDeIdentificacao == embarcacao.codigoDeIdentificacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoDeIdentificacao);
    }

}