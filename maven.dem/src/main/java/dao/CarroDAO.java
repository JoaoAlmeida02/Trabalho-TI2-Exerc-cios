package dao;

import model.Carro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO extends DAO {   
    public CarroDAO() {
        super();
        conectar();
    }
    
    
    public void finalize() {
        close();
    }
    
    
    public boolean insert(Carro carro) {
        boolean status = false;
        try {
            String sql = "INSERT INTO carro (modelo, placa, renavam, chassi, anofabri) "
                       + "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, carro.getModelo());
            st.setString(2, carro.getPlaca());
            st.setString(3, carro.getRenavam());
            st.setString(4, carro.getChassi());
            st.setInt(5, carro.getAnoFabri());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }

    
    public Carro get(int id) {
        Carro carro = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM carro WHERE id="+id;
            ResultSet rs = st.executeQuery(sql);    
            if(rs.next()){            
                 carro = new Carro(rs.getString("modelo"), rs.getString("placa"), rs.getString("renavam"), rs.getString("chassi"), rs.getInt("anofabri"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return carro;
    }
    
    
    public List<Carro> get() {
        return get("");
    }

    public List<Carro> getOrderByModelo() {
        return get("modelo");        
    }
    
    
    public List<Carro> getOrderByAnoFabri() {
        return get("anofabri");        
    }
    public List<Carro> getOrderByPlaca() {
        return get("placa");        
    }
    
    
    public List<Carro> get(String orderBy) {
        List<Carro> carros = new ArrayList<Carro>();
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM carro" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);               
            while(rs.next()) {                  
                Carro c = new Carro(rs.getString("modelo"), rs.getString("placa"), rs.getString("renavam"), rs.getString("chassi"), rs.getInt("anofabri"));
                carros.add(c);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return carros;
    }
    
    
    public boolean update(Carro carro) {
        boolean status = false;
        try {  
            String sql = "UPDATE carro SET modelo = ?, "
                       + "placa = ?, "
                       + "renavam = ?, "
                       + "chassi = ?, "
                       + "anofabri = ? WHERE placa = " + carro.getPlaca();
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, carro.getModelo());
            st.setString(2, carro.getPlaca());
            st.setString(3, carro.getRenavam());
            st.setString(4, carro.getChassi());
            st.setInt(5, carro.getAnoFabri());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    
    public boolean delete(String placa) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM carro WHERE id = " + placa);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
}
