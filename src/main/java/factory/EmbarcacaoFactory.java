/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import exception.TamanhoIncompativelException;
import model.Balsa;
import model.Balsa;
import model.Capitao;
import model.Capitao;
import model.Cargueiro;
import model.Cargueiro;
import model.Embarcacao;
import model.Embarcacao;
import model.NavioTuristico;
import model.NavioTuristico;
import model.Petroleiro;
import model.Petroleiro;
import model.Tamanho;
import model.Tamanho;

public class EmbarcacaoFactory {

    public static Embarcacao criar(String tipo, Tamanho tamanhoEclusa, float comp, float larg, 
                                   String origem, String destino, String pais, int cI, 
                                   Capitao capitaoEmbarcacao, String sentido, Object... extras) 
                                   throws TamanhoIncompativelException {
        
        // Validação comum de tamanho
        Tamanho tamanho = new Tamanho(comp, larg);
        tamanho.cabeNaEclusa(tamanhoEclusa);
        Capitao capitao = capitaoEmbarcacao;

        switch (tipo.toLowerCase()) {
            case "balsa":
                // extras[0] = carga (String), extras[1] = peso (float)
                return new Balsa(tamanho, origem, destino, pais, cI, capitao, sentido, 
                                 (String) extras[0], (float) extras[1]);
            case "cargueiro":
                // extras[0] = conteineres (int)
                return new Cargueiro(tamanho, origem, destino, pais, cI, capitao, sentido, 
                                     (int) extras[0]);
            case "petroleiro":
                // extras[0] = litros (float)
                return new Petroleiro(tamanho, origem, destino, pais, cI, capitao, sentido, 
                                      (float) extras[0]);
            case "turismo":
                // extras[0] = passageiros (int), extras[1] = cabines (int)
                return new NavioTuristico(tamanho, origem, destino, pais, cI, capitao, sentido, 
                                          (int) extras[0], (int) extras[1]);
            default:
                throw new IllegalArgumentException("Tipo de embarcação desconhecido: " + tipo);
        }
    }
}