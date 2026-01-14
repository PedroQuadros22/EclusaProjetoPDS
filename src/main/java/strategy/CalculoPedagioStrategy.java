/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package strategy;

import model.Embarcacao;

/**
 *
 * @author Pedro
 */

public interface CalculoPedagioStrategy {
    // Recebe a tarifa base (configurada na tela) e retorna o valor final
    float calcular(float precoFixo, Embarcacao embarcacao, Object... extras);
}