/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package strategy;

import model.Balsa;
import model.Embarcacao;

/**
 *
 * @author Pedro
 */
public class CalculoBalsa implements CalculoPedagioStrategy{
    public float calcular(float precoFixo, Embarcacao embarcacao, Object... extras) {
        if (embarcacao instanceof Balsa) {
            Balsa b = (Balsa) embarcacao;
            // Regra: Tarifa Base + (precoKgCarga * Peso da carga)
            float adicionalPeso = b.getPesoCarga() * (float)extras[0];
            return precoFixo + adicionalPeso;
        }
        return precoFixo;
    }
}
