package com.mycompany.eclusaprojeto;

import com.mycompany.view.View;
import model.Eclusa;

public class EclusaProjeto {
    public static void main(String[] args) {
        Eclusa eclusa = new Eclusa(
            150f, 25f, // Comprimento e Largura
            600f, 200f, // Capacidade máxima e mínima
            200f,        // Vazão
            500f, 750f, 600f, 350f // Preços para cada tipo de embarcação
        );
        View telaInicial = new View(eclusa);
        telaInicial.setVisible(true);
    }
}
