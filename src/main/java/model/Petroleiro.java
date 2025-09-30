package model;

public class Petroleiro extends Embarcacao {
    private float capacidadeLitros;

    public Petroleiro(){}
    public Petroleiro(Tamanho tamanho, String portoOrigem, String portoDestino, String pais,
            int codigoDeIdentificacao, Capitao capitao, String sentido, float capacidadeLitros) {
        super(tamanho,portoOrigem, portoDestino, pais, codigoDeIdentificacao, capitao, sentido);
        setCapacidadeLitros(capacidadeLitros);
    }
    public void setCapacidadeLitros(float capacidadeLitros){
        if(capacidadeLitros>0){
            this.capacidadeLitros=capacidadeLitros;
        }
    }

    public float getCapacidadeLitros() {
        return capacidadeLitros;
    }
    
    public String tipoEmbarcacao(){
        return "petroleiro";
    }
    public String mensagemEmbarcacao(){
        String mensagem=("Capitão:"+ this.getNome()+"\n"+"Codigo de Identificação:"+this.getCodigoDeIdentificacao()+"\nPorto de Origem: "+this.getPortoOrigem()+"\nPorto de Destino: "+this.getPortoDestino()+"\nPaís: "+this.getPais()+"\nCapacidade em Litros:"+this.getCapacidadeLitros());
        return mensagem;
    }
}
