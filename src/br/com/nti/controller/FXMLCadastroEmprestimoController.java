/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.controller;

import br.com.nti.modelo.Emprestimo;
import br.com.nti.modelo.Equipamento;
import br.com.nti.ultil.TextFieldFormatter;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class FXMLCadastroEmprestimoController implements Initializable {

    @FXML
    private TextField TextFildEmprestimoId;

    @FXML
    private TextField TextFildEmprestimoNome;

    @FXML
    private TextField TextFildEmprestimoSerial;

    @FXML
    private TextField TextFildEmprestimoSetor;

    @FXML
    private TextField TextFildEmprestimoEquipamento;

    @FXML
    private TextField TextFildEmprestimoTelefone;

    @FXML
    private TextField TextFildEmprestimoDestino, TextFildEmprestimoTombo;

    @FXML
    private TextArea TextAreaDescricao;

    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_salvar;

    @FXML
    private DatePicker DatePickerEmprestimoDataEntrega, DatePickerEmprestimoDataSaida;

    private Stage dialogStage;
    private boolean buttonConfimarClicked = false;
    private Equipamento equipamento = new Equipamento();
    private Emprestimo emprestimo = new Emprestimo();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private  void mascaraCelula() {
        TextFieldFormatter tff  = new TextFieldFormatter();
        tff.setMask("###########");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(TextFildEmprestimoTelefone);
        tff.formatter();
    }

    @FXML
    public void salvarEmprestimo() {
        if (validarEntradaDeDados()) {
            if (validaData()) {

                equipamento.setId(Integer.parseInt(TextFildEmprestimoId.getText()));
                emprestimo.setSerialEquipamento(TextFildEmprestimoSerial.getText());
                emprestimo.setSetor(TextFildEmprestimoSetor.getText());
                emprestimo.setEquipamento(TextFildEmprestimoEquipamento.getText());
                emprestimo.setNome(TextFildEmprestimoNome.getText());
                emprestimo.setDestino(TextFildEmprestimoDestino.getText());
                emprestimo.setTombo(TextFildEmprestimoTombo.getText());
                emprestimo.setDescricao(TextAreaDescricao.getText());

                emprestimo.setNumero(TextFildEmprestimoTelefone.getText());

                emprestimo.setData_saida(DatePickerEmprestimoDataSaida.getValue());
                emprestimo.setDataDevolucao(DatePickerEmprestimoDataEntrega.getValue());
                emprestimo.setEquipamentoId(equipamento);
                dialogStage.close();
                buttonConfimarClicked = true;

            }
        }
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (TextFildEmprestimoSetor.getText() == null || TextFildEmprestimoSetor.getText().length() == 0) {
            errorMessage += "Campo Setor é Obrigatório !\n";
        }
        if (TextFildEmprestimoEquipamento.getText() == null || TextFildEmprestimoEquipamento.getText().length() == 0) {
            errorMessage += "Campo Equipamento é Obrigatório!\n";
        }
        if (TextFildEmprestimoNome.getText() == null || TextFildEmprestimoNome.getText().length() == 0) {
            errorMessage += "Campo Nome é Obrigatório!\n";
        }
        if (TextFildEmprestimoDestino.getText() == null || TextFildEmprestimoDestino.getText().length() == 0) {
            errorMessage += "Campo Destino é Obrigatório!\n";
        }
        if (DatePickerEmprestimoDataEntrega.getValue() == null) {
            errorMessage += "Campo Data de Devolução é Obrigatório!\n";
        }
        if (DatePickerEmprestimoDataEntrega.getValue() == null) {
            errorMessage += "Campo Data de Saida é Obrigatório!\n";
        }

        if (TextAreaDescricao.getText() == null || TextAreaDescricao.getText().length() == 0) {
            errorMessage += "Campo Descrição é Obrigatório!\n";
        }

        if (TextAreaDescricao.getText().length() > 255) {
            errorMessage += "Campo Descrição ultrapassou o limite de 255 carácteres !\n";
        }

        if (TextFildEmprestimoTelefone.getText() == null || TextFildEmprestimoTelefone.getText().length() == 0 || TextFildEmprestimoTelefone.getText().length() > 16) {
            errorMessage += "Campo Numero é Invalido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Empréstimo");
            alert.setHeaderText("Campos Invalidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public void desbilitarCampos() {
        TextFildEmprestimoId.setDisable(true);
        TextFildEmprestimoSerial.setDisable(true);
        TextFildEmprestimoSetor.setDisable(true);
        TextFildEmprestimoEquipamento.setDisable(true);
        TextFildEmprestimoTombo.setDisable(true);
    }

    public boolean validaData() {
        String errorMessage = "";
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataSaida = LocalDate.now();
        dataEmprestimo = DatePickerEmprestimoDataEntrega.getValue();
        dataSaida = DatePickerEmprestimoDataSaida.getValue();

        if (dataEmprestimo.compareTo(dataSaida) < 0) {     // Validação entre data de saida e devolução
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.setTitle("Erro na Data");
            alert.setHeaderText("Data de Saida    não pode ser '' Maior'' do que a Data de Devolução!" + " " + dataEmprestimo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }

        return true;
    }

    public boolean validaDataAtual() {
        String errorMessage = "";
        LocalDate dataDeHoje = LocalDate.now();
        LocalDate dataSaida = LocalDate.now();
        dataSaida = DatePickerEmprestimoDataSaida.getValue();

        if (dataSaida.compareTo(dataDeHoje) < 0) { // Valida a adata de Saida em relação ao dia Atual.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Data");
            alert.setHeaderText("Data de Saida   não pode ser ''Menor '' do que o dia atual !" + " " + dataDeHoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            alert.setContentText(errorMessage);
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
            //  return true;

        }

        return true;
    }

    // Gets e Sets.....
    public void botaoCancelarEmprestimo() {
        dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfimarClicked() {
        return buttonConfimarClicked;
    }

    public void setButtonConfimarClicked(boolean buttonConfimarClicked) {
        this.buttonConfimarClicked = buttonConfimarClicked;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        desbilitarCampos();
        this.equipamento = equipamento;
        this.TextFildEmprestimoId.setText(equipamento.getId().toString());
        this.TextFildEmprestimoSerial.setText(equipamento.getSerial());
        this.TextFildEmprestimoSetor.setText(equipamento.getSetor());
        this.TextFildEmprestimoEquipamento.setText(equipamento.getEquipamento());
        this.TextFildEmprestimoTombo.setText(equipamento.getTombo());

    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;

    }

// Metodo usando para carregar as informção nos TextFild para serem alterado
    public void carregarDadosParaAlterar(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
        desbilitarCampos();
        TextFildEmprestimoId.setDisable(true);
        this.TextFildEmprestimoId.setText(emprestimo.getId().toString());
        this.TextFildEmprestimoSerial.setText(emprestimo.getSerialEquipamento());
        this.TextFildEmprestimoSetor.setText(emprestimo.getSetor());
        this.TextFildEmprestimoEquipamento.setText(emprestimo.getEquipamento());
        this.TextFildEmprestimoDestino.setText(emprestimo.getDestino());
        this.TextFildEmprestimoTombo.setText(emprestimo.getTombo());
        this.TextFildEmprestimoNome.setText(emprestimo.getNome());
        this.TextFildEmprestimoTelefone.setText(emprestimo.getNumero());
        this.DatePickerEmprestimoDataEntrega.setValue(emprestimo.getDataDevolucao());
        this.DatePickerEmprestimoDataSaida.setValue(emprestimo.getData_saida());
        this.TextAreaDescricao.setText(emprestimo.getDescricao());

    }

}
