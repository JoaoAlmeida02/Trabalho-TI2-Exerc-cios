package com.ti2cc;

import java.sql.*;
import java.security.*;
import java.math.*;

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
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
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
	
	public boolean inserirAluno(alunos aluno) {
        boolean status = false;

        try {
            String sql = "INSERT INTO alunos (matricula, nota, materiafav, sexo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, aluno.getMatricula());
                ps.setInt(2, aluno.getNota());
                ps.setString(3, aluno.getMateriafav());
                ps.setString(4, String.valueOf(aluno.getSexo()));

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
        }

        return status;
    }

	 public alunos[] getAlunos() {
	        String sql = "SELECT * FROM alunos";
	        try {
	            Statement stmt = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            ResultSet rs = stmt.executeQuery(sql);
	          
	            rs.last();
	            int rowCount = rs.getRow();
	            rs.beforeFirst();
	            
	            alunos[] alunos = new alunos[rowCount];
	            int index = 0;
	            while (rs.next()) {
	                int matricula = rs.getInt("matricula");
	                int nota = rs.getInt("nota");
	                String materiafav = rs.getString("materiafav");
	                char sexo = rs.getString("sexo").charAt(0);

	                alunos[index] = new alunos(matricula, nota, materiafav, sexo);
	                index++;
	            }
	            
	            rs.close();
	            stmt.close();
	            return alunos;
	        } catch (SQLException e) {
	            System.err.println("Erro ao obter alunos: " + e.getMessage());
	            return null;
	        }
	    }
	

    public boolean atualizarAluno(alunos aluno) {
        boolean status = false;

        try {
            String sql = "UPDATE alunos SET nota = ?, materiafav = ? WHERE matricula = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, aluno.getNota());
                ps.setString(2, aluno.getMateriafav());
                ps.setInt(3, aluno.getMatricula());

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }

        return status;
    }

    public boolean excluirAluno(int matricula) {
        boolean status = false;

        try {
            String sql = "DELETE FROM alunos WHERE matricula = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, matricula);

                int rowsAffected = ps.executeUpdate();
                status = (rowsAffected > 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        }

        return status;
    }
	
	public static String toMD5(String senha) throws Exception {
		MessageDigest m=MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(),0, senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
}
