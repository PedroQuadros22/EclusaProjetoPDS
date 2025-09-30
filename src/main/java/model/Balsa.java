package model;

public class Balsa extends Embarcacao {
    private String tipoCarga;
    private float pesoCarga;

    public Balsa(){}
    public Balsa(Tamanho tamanho, String portoOrigem, String portoDestino, String pais, int codigoDeIdentificacao, Capitao capitao, String sentido, String tipoCarga, float pesoCarga) {
        super(tamanho, portoOrigem, portoDestino, pais, codigoDeIdentificacao, capitao, sentido);
        setTipoCarga(tipoCarga);
        setPesoCarga(pesoCarga);
    }

    public void setTipoCarga(String tipoCarga) {
        if(tipoCarga!=null) {
            this.tipoCarga = tipoCarga;
        }
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setPesoCarga(float pesoCarga){
        if(pesoCarga>0){
            this.pesoCarga=pesoCarga;
        }
    }

    public float getPesoCarga() {
        return pesoCarga;
    }
    
    public String tipoEmbarcacao(){
        return "balsa";
    }
    public String mensagemEmbarcacao(){
        String mensagem=("Capitão:"+ this.getNome()+"\n"+"Codigo de Identificação:"+this.getCodigoDeIdentificacao()+"\nPorto de Origem: "+this.getPortoOrigem()+"\nPorto de Destino: "+this.getPortoDestino()+"\nPaís: "+this.getPais()+"\nTipo de Carga: "+ this.getTipoCarga()+"\nPeso da carga: "+this.getPesoCarga());
        return mensagem;
    }
}
