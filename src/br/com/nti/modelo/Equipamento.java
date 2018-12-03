/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.modelo;

import javafx.scene.control.CheckBox;

/**
 *
 * @author Filipe.Santos
 */
public class Equipamento {

    private Integer id;
    private String setor;
    private String usuario;
    private String equipamento;
    private String descricao;
    private String serial;
    private String tombo;
    private String fornecedor;
    private String marca;
    private String computador;
    private String observacoes;
    private String local;
    private CheckBox selecionar;

    public Equipamento() {
    }

    public Equipamento(Integer id, String setor, String usuario, String equipamento, String descricao, String serial, String tombo, String fornecedor, String marca, String computador, String observacoes, String local, CheckBox selecionar) {
        this.id = id;
        this.setor = setor;
        this.usuario = usuario;
        this.equipamento = equipamento;
        this.descricao = descricao;
        this.serial = serial;
        this.tombo = tombo;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.computador = computador;
        this.observacoes = observacoes;
        this.local = local;
        this.selecionar = selecionar;
    }

    public Integer getId() {
        return id;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getComputador() {
        return computador;
    }

    public void setComputador(String computador) {
        this.computador = computador;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public CheckBox getSelecionar() {
        return selecionar;
    }

    public void setSelecionar(CheckBox selecionar) {
        this.selecionar = selecionar;
    }
    
    

}
