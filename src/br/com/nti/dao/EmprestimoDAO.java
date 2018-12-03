package br.com.nti.dao;

import br.com.nti.modelo.Emprestimo;
import br.com.nti.modelo.Equipamento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Filipe.Santos
 */
public class EmprestimoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (serialEquipamento, setor,  equipamento, destino, nome, dataDevolucao, "
                + "tombo, descricao, numero, data_saida, equipamento_Id) VALUES(?,?,?, ?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, emprestimo.getSerialEquipamento());
            stmt.setString(2, emprestimo.getSetor());
            stmt.setString(3, emprestimo.getEquipamento());
            stmt.setString(4, emprestimo.getDestino());
            stmt.setString(5, emprestimo.getNome());
            stmt.setDate(6, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.setString(7, emprestimo.getTombo());
            stmt.setString(8, emprestimo.getDescricao());
            stmt.setString(9, emprestimo.getNumero());
            stmt.setDate(10, Date.valueOf(emprestimo.getData_saida()));
            stmt.setInt(11, emprestimo.getEquipamentoId().getId());

            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Emprestimo emprestimo) {
        String sql = "UPDATE  emprestimo SET     serialEquipamento=?, setor=?, equipamento=?, destino=?, nome=?,  dataDevolucao=?, tombo=?, descricao=? , numero=?,  data_saida=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, emprestimo.getSerialEquipamento());
            stmt.setString(2, emprestimo.getSetor());
            stmt.setString(3, emprestimo.getEquipamento());
            stmt.setString(4, emprestimo.getDestino());
            stmt.setString(5, emprestimo.getNome());
            stmt.setDate(6, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.setString(7, emprestimo.getTombo());
            stmt.setString(8, emprestimo.getDescricao());
            stmt.setString(9, emprestimo.getNumero());
            stmt.setDate(10, Date.valueOf(emprestimo.getData_saida()));
            stmt.setInt(11, emprestimo.getId());

            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("PASSEI NO CATCH...." );
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Emprestimo emprestimo) {
        String sql = "DELETE FROM emprestimo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Emprestimo> listar() {
        String sql = "SELECT * FROM emprestimo order  by id;";
        List<Emprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Emprestimo emprestimo = new Emprestimo();
                Equipamento equipamento = new Equipamento();
                emprestimo.setId(resultado.getInt("id"));
                emprestimo.setSerialEquipamento(resultado.getString("serialEquipamento"));
                emprestimo.setSetor(resultado.getString("setor"));
                emprestimo.setEquipamento(resultado.getString("equipamento"));
                emprestimo.setDestino(resultado.getString("destino"));
                emprestimo.setNome(resultado.getString("nome"));
                emprestimo.setDataDevolucao(resultado.getDate("dataDevolucao").toLocalDate());
                emprestimo.setTombo(resultado.getString("tombo"));
                emprestimo.setDescricao(resultado.getString("descricao"));
                emprestimo.setNumero(resultado.getString("numero"));
                emprestimo.setData_saida(resultado.getDate("data_saida").toLocalDate());
                equipamento.setId(resultado.getInt("id"));
                retorno.add(emprestimo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

      public Integer contarEquipamento() {
        int cont = 0;
        try {
            String sql = "SELECT COUNT(id) AS id FROM emprestimo";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            resultado.next();
            cont = resultado.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return cont;

    }
}
