package br.com.nti.modelo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Filipe.Santos
 */
public class Emprestimo implements Serializable {

    private Integer id;
    private String serialEquipamento;
    private String setor;
    private String equipamento;
    private String destino;
    private String nome;
    private LocalDate dataDevolucao;
    private LocalDate data_saida;
    private String descricao;
    private String tombo;
    private String numero;
    private Equipamento equipamentoId;

    public Emprestimo() {
    }

    public Emprestimo(Integer id, String serialEquipamento, String setor, String equipamento, String destino, String nome, LocalDate dataDevolucao, LocalDate data_saida, String descricao, String tombo, String numero, Equipamento equipamentoId) {
        this.id = id;
        this.serialEquipamento = serialEquipamento;
        this.setor = setor;
        this.equipamento = equipamento;
        this.destino = destino;
        this.nome = nome;
        this.dataDevolucao = dataDevolucao;
        this.data_saida = data_saida;
        this.descricao = descricao;
        this.tombo = tombo;
        this.numero = numero;
        this.equipamentoId = equipamentoId;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getSerialEquipamento() {
        return serialEquipamento;
    }

    public void setSerialEquipamento(String serialEquipamento) {
        this.serialEquipamento = serialEquipamento;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Equipamento getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Equipamento equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getData_saida() {
        return data_saida;
    }

    public void setData_saida(LocalDate data_saida) {
        this.data_saida = data_saida;
    }

}
