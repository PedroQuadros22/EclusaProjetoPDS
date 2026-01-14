/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

/**
 *
 * @author Pedro
 */

import model.Cargueiro;
import model.Embarcacao;

public class CalculoCargueiro implements CalculoPedagioStrategy {
    public float calcular(float precoFixo, Embarcacao embarcacao, Object... extras) {
        if (embarcacao instanceof Cargueiro) {
            Cargueiro c = (Cargueiro) embarcacao;

            float adicionalConteiner = c.getNumConteineres() * (float) extras[0]; 
            return precoFixo + adicionalConteiner;
        }
        return precoFixo;
    }
}
