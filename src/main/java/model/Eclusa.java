package model;

import controller.EclusaController;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Eclusa{
    private Tamanho tamanho;
    private float capacidadeMaxima;
    private float capacidadeMinima;
    private float vazao;
    private HashMap<String,Float>precoPorTipo=new HashMap<>();
    private HashMap<Integer, Embarcacao>embarcacoes = new HashMap<>();
    private ArrayList<Embarcacao>fila=new ArrayList<>();
    private String status="seca";
    private float totalApurado=0;
    private boolean ocupada;
    private boolean porta;
    private Embarcacao embarcacaoNaEclusa;
    
    public Eclusa(){}
    public Eclusa(float comprimento, float largura, float capacidadeMaxima, float capacidadeMinima, float vazao,Float preco,Float preco2,Float preco3,Float preco4){
        setCapacidadeMaxima(capacidadeMaxima);
        setCapacidadeMinima(capacidadeMinima);
        setVazao(vazao);
        tamanho = new Tamanho(comprimento,largura);
        setPrecoPorTipo("cargueiro",preco);
        setPrecoPorTipo("petroleiro",preco2);
        setPrecoPorTipo("turismo",preco3);
        setPrecoPorTipo("balsa",preco4);
    }
    
    public boolean setEmbarcacoes(Integer cI, Embarcacao e) {
    if (embarcacoes.containsKey(cI)) {
        if (!embarcacoes.get(cI).equals(e)) {
            embarcacoes.put(cI, e);
            fila.add(e);
            return true;
        }else{
            return false;
        }
    } else {
        embarcacoes.put(cI, e);
        fila.add(e);
        return true;
    }
}

    
    public void setPrecoPorTipo(String tipo,Float preco){
        if(preco>0){
            precoPorTipo.put(tipo, preco);
        }
    }
    public void removerPreco(String tipo){
        precoPorTipo.remove(tipo);
    }
    public Tamanho getTamanho(){
        return tamanho;
    }
    public float getVazao(){
        return vazao;
    }
    public HashMap <String,Float> getPrecoPorTipo(){
        return precoPorTipo;
    }
    public void setCapacidadeMaxima(float capacidadeMaxima){
        if(capacidadeMaxima>0&&capacidadeMaxima>capacidadeMinima){
            this.capacidadeMaxima=capacidadeMaxima;
        }
    }
    public float getCapacidadeMaxima(){
        return capacidadeMaxima;
    }
    public void setCapacidadeMinima(float capacidadeMinima){
        if(capacidadeMinima>0&&capacidadeMinima<capacidadeMaxima){
            this.capacidadeMinima=capacidadeMinima;
        }
    }
    public float getCapacidadeMinima(){
        return capacidadeMinima;
    }
    public void setVazao(float vazao){
        if(vazao>0){
            this.vazao=vazao;
        }
    }

    public float tempo(){
        return ((capacidadeMaxima-capacidadeMinima)/vazao);
    }
    public float totalApuradoDia(){
        Embarcacao ultimaEmbarcacao = fila.get(fila.size() - 1);
        if (ultimaEmbarcacao instanceof Cargueiro){
            Cargueiro navio=(Cargueiro)fila.get(fila.size()-1);
            totalApurado+=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else if (ultimaEmbarcacao instanceof Petroleiro){
            Petroleiro navio=(Petroleiro)fila.get(fila.size()-1);
            totalApurado+=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else if (ultimaEmbarcacao instanceof NavioTuristico){
            NavioTuristico navio=(NavioTuristico)fila.get(fila.size()-1);
            totalApurado+=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else{
            Balsa navio=(Balsa)fila.get(fila.size()-1);
            totalApurado+=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        return totalApurado;
    }
    public int tamanhoFila(){
        return fila.size();
    }
    public void filaEmbarcacao(Embarcacao o){
        fila.add(o);
    }
    public void removerDaFila(){
        fila.remove(0);
        
    }
    public float removerDaFila(int tamanho){
        Embarcacao ultimaEmbarcacao = fila.get(tamanho);
        if (ultimaEmbarcacao instanceof Cargueiro){
            Cargueiro navio=(Cargueiro)fila.get(tamanho);
            totalApurado-=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else if (ultimaEmbarcacao instanceof Petroleiro){
            Petroleiro navio=(Petroleiro)fila.get(tamanho);
            totalApurado-=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else if (ultimaEmbarcacao instanceof NavioTuristico){
            NavioTuristico navio=(NavioTuristico)fila.get(tamanho);
            totalApurado-=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        else{
            Balsa navio=(Balsa)fila.get(fila.size()-1);
            totalApurado-=precoPorTipo.get(navio.tipoEmbarcacao());
        }
        fila.remove(tamanho);
        return totalApurado;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setOcupada(boolean ocupada){
        this.ocupada=ocupada;
    }
    public void setEmbarcacaoNaEclusa(Embarcacao o){
        embarcacaoNaEclusa=o;
    }
    
    public void removerMap(){
        embarcacoes.remove(embarcacaoNaEclusa.getCodigoDeIdentificacao());
    }
    public boolean getOcupada(){
        return ocupada;
    }
    public void setPorta(boolean b){
        porta=b;
    }
    public boolean getPorta(){
        return porta;
    }
    public String getStatus(){
        return status;
    }
    
    public Embarcacao getUltimaNaFila(){
        return fila.get(this.tamanhoFila()-1);
    }
    
    public ArrayList<Embarcacao> getFila(){
        return fila;
    }
    public Embarcacao getEmbarcacaoNaEclusa(){
        return embarcacaoNaEclusa;
    }
    public String mensagem(){
        
        Balsa balsa=(Balsa)embarcacaoNaEclusa;
        return balsa.mensagemEmbarcacao();
    }
}