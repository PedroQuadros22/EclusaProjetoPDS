package model;

public class Capitao {
    private String nome;
    

    public Capitao(){}
    public Capitao(String nome){
        setNome(nome);
    }
    public void setNome(String nome){
        if(nome!=null){
            this.nome=nome;
        }
    }
    public String getNome(){
        return nome;
    }

    public String toString(){
        return ("Capitão: "+nome);
    }
}
