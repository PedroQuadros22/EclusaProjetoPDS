/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

/**
 *
 * @author Pedro
 */

import model.Embarcacao;
import model.NavioTuristico;

public class CalculoTurismo implements CalculoPedagioStrategy {

    public float calcular(float precoFixo, Embarcacao embarcacao, Object... extras) {
        if (embarcacao instanceof NavioTuristico) {
            NavioTuristico t = (NavioTuristico) embarcacao;
            float adicionalPessoas = t.getNumPassageiros() * (float) extras[0] + t.getNumCabines()* (float) extras[1];
            return precoFixo + adicionalPessoas;
        }
        return precoFixo;
    }
}
