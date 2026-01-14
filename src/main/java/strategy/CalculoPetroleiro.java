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
import model.Petroleiro;

public class CalculoPetroleiro implements CalculoPedagioStrategy {
    public float calcular(float precoFixo, Embarcacao embarcacao, Object... extras) {
        if (embarcacao instanceof Petroleiro) {
            Petroleiro p = (Petroleiro) embarcacao;
            float adicionalLitros = p.getCapacidadeLitros() * (float) extras[0];
            return precoFixo + adicionalLitros;
        }
        return precoFixo;
    }
}
