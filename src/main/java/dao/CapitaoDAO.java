package dao;

import model.Capitao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapitaoDAO {

    // 1. SALVAR (INSERT)
    public void salvar(Capitao capitao) throws SQLException {
        String sql = "INSERT INTO capitao (licenca, nome, contato) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capitao.getNumLicenca());
            stmt.setString(2, capitao.getNome());
            stmt.setString(3, capitao.getContato());
            
            stmt.execute();
        }
    }

    // 2. LISTAR TODOS (SELECT)
    public List<Capitao> listarTodos() throws SQLException {
        List<Capitao> lista = new ArrayList<>();
        String sql = "SELECT * FROM capitao";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int licenca = rs.getInt("licenca");
                String nome = rs.getString("nome");
                String contato = rs.getString("contato");
                
                lista.add(new Capitao(nome, licenca, contato));
            }
        }
        return lista;
    }

    // 3. ATUALIZAR (UPDATE)
    // Recebe a licença antiga caso o usuário decida mudar o número da licença
    public void atualizar(Capitao capitao, int licencaAntiga) throws SQLException {
        String sql = "UPDATE capitao SET licenca = ?, nome = ?, contato = ? WHERE licenca = ?";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capitao.getNumLicenca()); // Nova Licença
            stmt.setString(2, capitao.getNome());
            stmt.setString(3, capitao.getContato());
            stmt.setInt(4, licencaAntiga);            // Licença Antiga (para achar o registro)
            
            stmt.execute();
        }
    }

    // 4. DELETAR (DELETE)
    public void deletar(int licenca) throws SQLException {
        String sql = "DELETE FROM capitao WHERE licenca = ?";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, licenca);
            stmt.execute();
        }
    }

    // 5. VERIFICAR SE EXISTE (SELECT auxiliar)
    public boolean existe(int licenca) throws SQLException {
        String sql = "SELECT 1 FROM capitao WHERE licenca = ?";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, licenca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se achou alguém
            }
        }
    }
    
    public Capitao buscarPorLicenca(int licenca) throws SQLException {
        String sql = "SELECT * FROM capitao WHERE licenca = ?";
        
        try (Connection conn = ConexaoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, licenca);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String contato = rs.getString("contato");
                    return new Capitao(nome, licenca, contato);
                }
            }
        }
        return null;
    }
}