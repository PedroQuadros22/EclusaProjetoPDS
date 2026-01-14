/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmbarcacaoDAO {

    public void salvar(Embarcacao e) throws SQLException {
        String sql = "INSERT INTO embarcacao (id, tipo, origem, destino, pais, sentido, capitao_licenca, comprimento, largura, " +
                     "peso_carga, tipo_carga, num_conteineres, litros, passageiros, cabines) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getCodigoDeIdentificacao());
            stmt.setString(2, e.tipoEmbarcacao());
            stmt.setString(3, e.getPortoOrigem());
            stmt.setString(4, e.getPortoDestino());
            stmt.setString(5, e.getPais());
            stmt.setString(6, e.getSentido());
            stmt.setInt(7, e.getCapitao().getNumLicenca());

             stmt.setFloat(8, 0); 
             stmt.setFloat(9, 0); 

            if (e instanceof Balsa) {
                stmt.setFloat(10, ((Balsa) e).getPesoCarga());
                stmt.setString(11, ((Balsa) e).getTipoCarga());
            } else { stmt.setObject(10, null); stmt.setObject(11, null); }

            if (e instanceof Cargueiro) {
                stmt.setInt(12, ((Cargueiro) e).getNumConteineres());
            } else { stmt.setObject(12, null); }

            if (e instanceof Petroleiro) {
                stmt.setFloat(13, ((Petroleiro) e).getCapacidadeLitros());
            } else { stmt.setObject(13, null); }

            if (e instanceof NavioTuristico) {
                stmt.setInt(14, ((NavioTuristico) e).getNumPassageiros());
                stmt.setInt(15, ((NavioTuristico) e).getNumCabines());
            } else { stmt.setObject(14, null); stmt.setObject(15, null); }

            stmt.execute();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM embarcacao WHERE id = ?";
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        }
    }
    public List<Embarcacao> listarTodas(Tamanho tamanhoPadrao, CapitaoDAO capDao) throws SQLException {
        List<Embarcacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM embarcacao";

        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("id");
                String origem = rs.getString("origem");
                String destino = rs.getString("destino");
                String pais = rs.getString("pais");
                String sentido = rs.getString("sentido");
                int licencaCap = rs.getInt("capitao_licenca");

                Capitao cap = capDao.buscarPorLicenca(licencaCap);
                if (cap == null) {
                    cap = new Capitao("Capitão Desconhecido", licencaCap, "---");
                }
                Embarcacao emb = null;

                if ("balsa".equalsIgnoreCase(tipo)) {
                    float peso = rs.getFloat("peso_carga");
                    String tpCarga = rs.getString("tipo_carga");
                    emb = new Balsa(tamanhoPadrao, origem, destino, pais, id, cap, sentido, tpCarga, peso);
                } else if ("cargueiro".equalsIgnoreCase(tipo)) {
                    int cont = rs.getInt("num_conteineres");
                    emb = new Cargueiro(tamanhoPadrao, origem, destino, pais, id, cap, sentido, cont);
                } else if ("petroleiro".equalsIgnoreCase(tipo)) {
                    float lit = rs.getFloat("litros");
                    emb = new Petroleiro(tamanhoPadrao, origem, destino, pais, id, cap, sentido, lit);
                } else if ("turismo".equalsIgnoreCase(tipo)) {
                    int pass = rs.getInt("passageiros");
                    int cab = rs.getInt("cabines");
                    emb = new NavioTuristico(tamanhoPadrao, origem, destino, pais, id, cap, sentido, pass, cab);
                }

                if (emb != null) lista.add(emb);
            }
        }
        return lista;
    }
}
