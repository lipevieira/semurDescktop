package br.com.nti.dao;

import br.com.nti.modelo.Equipamento;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Filipe.Santos
 */
public class EquipamentoDAO implements Serializable {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Equipamento equipamento) {
        String sql = "INSERT INTO equipamento(setor, usuario, equipamento, "
                + "descricao, serial, tombo, fornecedor, marca, computador, observacoes, local) VALUES(?,?,?, ?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, equipamento.getSetor());
            stmt.setString(2, equipamento.getUsuario());
            stmt.setString(3, equipamento.getEquipamento());
            stmt.setString(4, equipamento.getDescricao());
            stmt.setString(5, equipamento.getSerial());
            stmt.setString(6, equipamento.getTombo());
            stmt.setString(7, equipamento.getFornecedor());
            stmt.setString(8, equipamento.getMarca());
            stmt.setString(9, equipamento.getComputador());
            stmt.setString(10, equipamento.getObservacoes());
            stmt.setString(11, equipamento.getLocal());
            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Equipamento equipamento) {
        String sql = "UPDATE  equipamento SET   setor=?, usuario=?, equipamento=?, descricao=?, serial=?, tombo=?,"
                + " fornecedor=?, marca=?, computador=?,  observacoes = ?,local = ? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, equipamento.getSetor());
            stmt.setString(2, equipamento.getUsuario());
            stmt.setString(3, equipamento.getEquipamento());
            stmt.setString(4, equipamento.getDescricao());
            stmt.setString(5, equipamento.getSerial());
            stmt.setString(6, equipamento.getTombo());
            stmt.setString(7, equipamento.getFornecedor());
            stmt.setString(8, equipamento.getMarca());
            stmt.setString(9, equipamento.getComputador());
            stmt.setString(10, equipamento.getObservacoes());
            stmt.setString(11, equipamento.getLocal());
            stmt.setInt(12, equipamento.getId());

            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Equipamento equipamento) {
        String sql = "DELETE FROM equipamento WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, equipamento.getId());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException ex) {

            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Equipamento> listar() {
        String sql = "SELECT * FROM equipamento ORDER BY local, setor, usuario, equipamento ";
        List<Equipamento> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Equipamento equipamento = new Equipamento();
                CheckBox selecionar = new CheckBox("" + equipamento.getSelecionar());

                equipamento.setSelecionar(selecionar);
                equipamento.setId(resultado.getInt("id"));
                equipamento.setSetor(resultado.getString("setor"));
                equipamento.setUsuario(resultado.getString("usuario"));
                equipamento.setEquipamento(resultado.getString("equipamento"));
                equipamento.setDescricao(resultado.getString("descricao"));
                equipamento.setSerial(resultado.getString("serial"));
                equipamento.setTombo(resultado.getString("tombo"));
                equipamento.setFornecedor(resultado.getString("fornecedor"));
                equipamento.setMarca(resultado.getString("marca"));
                equipamento.setComputador(resultado.getString("computador"));
                equipamento.setObservacoes(resultado.getString("observacoes"));
                equipamento.setLocal(resultado.getString("local"));
                retorno.add(equipamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Integer contarEquipamento() {
        int cont = 0;
        try {
            String sql = "SELECT COUNT(id) AS id FROM equipamento";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            resultado.next();
            cont = resultado.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return cont;

    }

    @Override
    public String toString() {
        return "EquipamentoDAO{" + "connection=" + connection + '}';
    }

}
