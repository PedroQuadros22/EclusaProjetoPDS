package model;

public class Capitao {
    private String nome;
    private int numLicenca;
    private String contato;
    
    public Capitao(){}

    // Corrigido: mudei 'String Contato' para 'String contato' para corresponder à chamada interna
    public Capitao(String nome, int numLicenca, String contato){
        setNome(nome);
        setNumLicenca(numLicenca);
        setContato(contato);
    }

    public void setNome(String nome){
        if(nome != null && !nome.isEmpty()){
            this.nome = nome;
        }
    }

    public String getNome(){
        return nome;
    }


    public void setNumLicenca(int numLicenca){

        if(numLicenca > 0){
            this.numLicenca = numLicenca;
        }
    }

    public int getNumLicenca(){
        return numLicenca;
    }

    public void setContato(String contato){
        if(contato != null && !contato.isEmpty()){
            this.contato = contato;
        }
    }

    public String getContato(){
        return contato;
    }

    public String toString(){
        return ("Capitão: " + nome + " | Licença: " + numLicenca);
    }
}
