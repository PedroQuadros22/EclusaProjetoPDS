package model;

public class Cargueiro extends Embarcacao {
    private int numConteineres;

    public Cargueiro(){}
    public Cargueiro(Tamanho tamanho, String portoOrigem, String portoDestino, String pais, int codigoDeIdentificacao, Capitao capitao, String sentido, int numConteineres) {
        super(tamanho, portoOrigem, portoDestino, pais, codigoDeIdentificacao, capitao, sentido);
        setNumConteineres(numConteineres);
    }
    public void setNumConteineres(int numConteineres){
        if(numConteineres>0){
            this.numConteineres=numConteineres;
        }
    }

    public int getNumConteineres() {
        return numConteineres;
    }
    
    public String tipoEmbarcacao(){
        return "cargueiro";
    }
    public String mensagemEmbarcacao(){
        String mensagem=("Capitão:"+ this.getNome()+"\n"+"Codigo de Identificação:"+this.getCodigoDeIdentificacao()+"\nPorto de Origem: "+this.getPortoOrigem()+"\nPorto de Destino: "+this.getPortoDestino()+"\nPaís: "+this.getPais()+"\nNúmero de conteineres: "+this.getNumConteineres());
        return mensagem;
    }
}
