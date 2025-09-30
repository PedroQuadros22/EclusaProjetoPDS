package model;

import exception.TamanhoIncompativelException;

public class Tamanho{
    private float comprimento;
    private float largura;
    
    public Tamanho(){}
    
    public Tamanho(float comprimento, float largura){
        setComprimento(comprimento);
        setLargura(largura);
    }
    
    
    public void setComprimento(float comprimento){
        if(comprimento > 0){
            this.comprimento = comprimento;
        }
    }
    
    public void setLargura(float largura){
        if(largura > 0){
            this.largura = largura;
        }
    }
    
    public float getComprimento(){
        return comprimento;
    }
    
    public float getLargura(){
        return largura;
    }

    public void cabeNaEclusa(Tamanho tamanho) throws TamanhoIncompativelException {
        if (comprimento > tamanho.getComprimento() || largura > tamanho.getLargura()) {
            throw new TamanhoIncompativelException();
        }
    }

}