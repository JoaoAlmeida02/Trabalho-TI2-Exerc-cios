package dao;

import model.Carro;

import java.sql.*;

public class DAO {
    protected Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
    	 String driverName = "org.postgresql.Driver";
         String serverName = "localhost";
         String mydatabase = "postgres";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirCarro(Carro carro) {
        boolean status = false;

        try {
            String sql = "INSERT INTO carros (placa, renavam, chassi, modelo, anofabri) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, carro.getPlaca());
                ps.setString(2, carro.getRenavam());
                ps.setString(3, carro.getChassi());
                ps.setString(4, carro.getModelo());
                ps.setInt(5, carro.getAnoFabri());

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir carro: " + e.getMessage());
        }

        return status;
    }

    public Carro[] getCarros() {
        String sql = "SELECT * FROM carros";
        try {
            Statement stmt = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
          
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            
            Carro[] carros = new Carro[rowCount];
            int index = 0;
            while (rs.next()) {
                String placa = rs.getString("placa");
                String renavam = rs.getString("renavam");
                String chassi = rs.getString("chassi");
                String modelo = rs.getString("modelo");
                int anoFabri = rs.getInt("anofabri");

                carros[index] = new Carro(placa, renavam, chassi, modelo, anoFabri);
                index++;
            }
            
            rs.close();
            stmt.close();
            return carros;
        } catch (SQLException e) {
            System.err.println("Erro ao obter carros: " + e.getMessage());
            return null;
        }
    }

    public boolean atualizarCarro(Carro carro) {
        boolean status = false;

        try {
            String sql = "UPDATE carros SET renavam = ?, chassi = ?, modelo = ?, anofabri = ? WHERE placa = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, carro.getRenavam());
                ps.setString(2, carro.getChassi());
                ps.setString(3, carro.getModelo());
                ps.setInt(4, carro.getAnoFabri());
                ps.setString(5, carro.getPlaca());

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carro: " + e.getMessage());
        }

        return status;
    }

    public boolean excluirCarro(String placa) {
        boolean status = false;

        try {
            String sql = "DELETE FROM carros WHERE placa = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, placa);

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir carro: " + e.getMessage());
        }

        return status;
    }
}
