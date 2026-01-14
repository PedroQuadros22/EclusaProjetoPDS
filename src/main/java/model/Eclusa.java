package model;

import java.util.HashMap;
import java.util.ArrayList;

public class Eclusa {
    private Tamanho tamanho;
    private float capacidadeMaxima;
    private float capacidadeMinima;
    private float vazao;
    private HashMap<String,Float> precoPorTipo = new HashMap<>();
    private HashMap<Integer, Embarcacao> embarcacoes = new HashMap<>();
    private ArrayList<Embarcacao> fila = new ArrayList<>();
    private String status = "seca";
    private float totalApurado = 0;
    private boolean ocupada;
    private boolean porta;
    private Embarcacao embarcacaoNaEclusa;
    private CalculadoraPedagio calculadora;
    
    public Eclusa(){}
    
    public Eclusa(float comprimento, float largura, float capacidadeMaxima, float capacidadeMinima, float vazao, Float preco, Float preco2, Float preco3, Float preco4, CalculadoraPedagio calculadora){
        setCapacidadeMaxima(capacidadeMaxima);
        setCapacidadeMinima(capacidadeMinima);
        setVazao(vazao);
        tamanho = new Tamanho(comprimento,largura);
        setPrecoPorTipo("cargueiro",preco);
        setPrecoPorTipo("petroleiro",preco2);
        setPrecoPorTipo("turismo",preco3);
        setPrecoPorTipo("balsa",preco4);
        this.calculadora = calculadora;
    }
    
    public void registrarPagamento(Embarcacao e) {
        if (e == null) return;
        
        // PROTEÇÃO: Se a calculadora não existir, não trava o programa!
        if (this.calculadora == null) {
            System.out.println("AVISO: Calculadora não iniciada. Pagamento ignorado para evitar erro.");
            return; 
        }
        
        String tipo = identificarTipoString(e);
        if (tipo == null) return;
        
        float tarifaBase = precoPorTipo.getOrDefault(tipo, 0f);
        float valor = calculadora.calcularTarifa(e, tarifaBase, tipo);
        
        this.totalApurado += valor;
    }


    public float totalApuradoDia(){
        return this.totalApurado;
    }

    public void iniciarNovoDia() {
        this.totalApurado = 0;
    }



    public boolean setEmbarcacoes(Integer cI, Embarcacao e) {
        if (embarcacoes.containsKey(cI)) {
            if (!embarcacoes.get(cI).equals(e)) {
                embarcacoes.put(cI, e);
                fila.add(e);
                return true;
            } else {
                return false;
            }
        } else {
            embarcacoes.put(cI, e);
            fila.add(e);
            return true;
        }
    }

    public void setPrecoPorTipo(String tipo, Float preco){
        if(preco > 0) precoPorTipo.put(tipo, preco);
    }
    public void removerPreco(String tipo){
        precoPorTipo.remove(tipo);
    }
    
    // Getters e Setters
    public Tamanho getTamanho(){ return tamanho; }
    public float getVazao(){ return vazao; }
    public HashMap<String,Float> getPrecoPorTipo(){ return precoPorTipo; }
    
    public void setCapacidadeMaxima(float capacidadeMaxima){
        if(capacidadeMaxima > 0 && capacidadeMaxima > capacidadeMinima) this.capacidadeMaxima = capacidadeMaxima;
    }
    public float getCapacidadeMaxima(){ return capacidadeMaxima; }
    
    public void setCapacidadeMinima(float capacidadeMinima){
        if(capacidadeMinima > 0 && capacidadeMinima < capacidadeMaxima) this.capacidadeMinima = capacidadeMinima;
    }
    public float getCapacidadeMinima(){ return capacidadeMinima; }
    
    public void setVazao(float vazao){
        if(vazao > 0) this.vazao = vazao;
    }

    public float tempo(){
        return ((capacidadeMaxima - capacidadeMinima) / vazao);
    }
    
    private String identificarTipoString(Embarcacao e) {
        if (e instanceof Cargueiro) return "cargueiro";
        if (e instanceof Petroleiro) return "petroleiro";
        if (e instanceof NavioTuristico) return "turismo";
        if (e instanceof Balsa) return "balsa";
        return null;
    }
    
    public int tamanhoFila(){ return fila.size(); }
    public void filaEmbarcacao(Embarcacao o){ fila.add(o); }
    
    public void removerDaFila(){
        if(!fila.isEmpty()) fila.remove(0);
    }

    public float removerDaFila(int indice) {
        if (indice < 0 || indice >= fila.size()) return totalApurado;
        
        // Lógica de estorno (opcional)
        Embarcacao embarcacao = fila.get(indice);
        if (this.calculadora != null) {
            String tipo = identificarTipoString(embarcacao);
            if (tipo != null) {
                float tarifaBase = precoPorTipo.getOrDefault(tipo, 0f);
                float valorTotal = calculadora.calcularTarifa(embarcacao, tarifaBase, tipo);
                totalApurado -= valorTotal;
                if (totalApurado < 0) totalApurado = 0;
            }
        }
        fila.remove(indice);
        return totalApurado;
    }
    
    public void setStatus(String status){ this.status = status; }
    public void setOcupada(boolean ocupada){ this.ocupada = ocupada; }
    public void setEmbarcacaoNaEclusa(Embarcacao o){ embarcacaoNaEclusa = o; }
    
    public void removerMap(){
        if(embarcacaoNaEclusa != null)
            embarcacoes.remove(embarcacaoNaEclusa.getCodigoDeIdentificacao());
    }
    
    public boolean getOcupada(){ return ocupada; }
    public void setPorta(boolean b){ porta = b; }
    public boolean getPorta(){ return porta; }
    public String getStatus(){ return status; }
    
    public Embarcacao getUltimaNaFila(){
        if (fila.isEmpty()) return null;
        return fila.get(this.tamanhoFila()-1);
    }
    
    public ArrayList<Embarcacao> getFila(){ return fila; }
    public Embarcacao getEmbarcacaoNaEclusa(){ return embarcacaoNaEclusa; }
    
    public String mensagem(){
        if (embarcacaoNaEclusa != null) return embarcacaoNaEclusa.mensagemEmbarcacao();
        return "";
    }
}