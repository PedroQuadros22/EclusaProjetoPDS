/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoFactory {
    
    private static final String URL = "jdbc:h2:./banco_eclusa"; 
    private static final String USER = "sa"; 
    private static final String PASS = ""; 

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        criarTabelas(conn);
        return conn;
    }
    
    private static void criarTabelas(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        
        // 1. Tabela Capitão (já existia)
        stmt.execute("CREATE TABLE IF NOT EXISTS capitao (" +
                     "licenca INT PRIMARY KEY, " +
                     "nome VARCHAR(100), " +
                     "contato VARCHAR(50))");
                     
        // 2. Tabela Embarcação (NOVA)
        // Guarda tudo misturado. O campo 'tipo' diz qual classe usar.
        String sqlEmbarcacao = "CREATE TABLE IF NOT EXISTS embarcacao (" +
                "id INT PRIMARY KEY, " +
                "tipo VARCHAR(20), " +      // balsa, cargueiro, etc.
                "origem VARCHAR(50), " +
                "destino VARCHAR(50), " +
                "pais VARCHAR(50), " +
                "sentido VARCHAR(20), " +
                "capitao_licenca INT, " +   // Quem conduz (Chave Estrangeira)
                "comprimento FLOAT, " +
                "largura FLOAT, " +
                // Campos Específicos
                "peso_carga FLOAT, " +      // Balsa
                "tipo_carga VARCHAR(50), " + // Balsa
                "num_conteineres INT, " +   // Cargueiro
                "litros FLOAT, " +          // Petroleiro
                "passageiros INT, " +       // Turismo
                "cabines INT" +             // Turismo
                ")";
        stmt.execute(sqlEmbarcacao);
        stmt.close();
    }
}