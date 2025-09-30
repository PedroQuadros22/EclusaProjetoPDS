package model;

public class NavioTuristico extends Embarcacao {
    private int numPassageiros;
    private int numCabines;

    public NavioTuristico(){}
    public NavioTuristico(Tamanho tamanho, String portoOrigem, String portoDestino, String pais, int codigoDeIdentificacao, Capitao capitao, String sentido, int numPassageiros, int numCabines) {
        super(tamanho, portoOrigem, portoDestino, pais, codigoDeIdentificacao, capitao, sentido);
        setNumPassageiros(numPassageiros);
        setNumCabines(numCabines);
    }

    public void setNumPassageiros(int numPassageiros) {
        if(numPassageiros>0){
            this.numPassageiros = numPassageiros;
        }
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    public void setNumCabines(int numCabines) {
        if(numCabines>0){
            this.numCabines = numCabines;
        }
    }

    public int getNumCabines() {
        return numCabines;
    }
    public String tipoEmbarcacao(){
        return "turismo";
    }
    public String mensagemEmbarcacao(){
        String mensagem=("Capitão:"+ this.getNome()+"\n"+"Codigo de Identificação:"+this.getCodigoDeIdentificacao()+"\nPorto de Origem: "+this.getPortoOrigem()+"\nPorto de Destino: "+this.getPortoDestino()+"\nPaís: "+this.getPais()+"\nNúmero de Passageiros: "+this.getNumPassageiros()+"\nNúmero de Cabines"+this.getNumCabines());
        return mensagem;
    }
}
