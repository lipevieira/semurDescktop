/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.controller;

import br.com.nti.modelo.Equipamento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class FXMLVBoxMainControllerDialogController implements Initializable {

    @FXML
    private Label labelEquipamentoSetor;

    @FXML
    private Label labelEquipamentoUsuario;

    @FXML
    private Label labelEquipamentoEquipamento;

    @FXML
    private Label labelEquipamentoDescricao;

    @FXML
    private Label labelEquipamentoSerial;

    @FXML
    private Label labelEquipamentoTombo;

    @FXML
    private Label labelEquipamentoFornecedor;

    @FXML
    private Label labelEquipamentoMarca;

    @FXML
    private Label labelEquipamentoCompuatador;

    @FXML
    private TextField TextFildEquipamentoSetor;

    @FXML
    private TextField TextFildEquipamentoUsuario;

    @FXML
    private TextField TextFildEquipamentoEquipamento;

    @FXML
    private TextField TextFildEquipamentoDescricao;

    @FXML
    private TextField TextFildEquipamentoSerial;

    @FXML
    private TextField TextFildEquipamentoTombo;

    @FXML
    private TextField TextFildEquipamentoFornecedor;

    @FXML
    private TextField TextFildEquipamentoMarca, TextFildEquipamentoLocal;

    @FXML
    private TextField TextFildEquipamentoCompuatador, TextFildEquipamentoObervacoes;

    @FXML
    private Button buttomSalvar;

    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfimarClicked = false;
    private Equipamento equipamento;

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
        this.equipamento = equipamento;
        this.TextFildEquipamentoLocal.setText(equipamento.getLocal());
        this.TextFildEquipamentoSetor.setText(equipamento.getSetor());
        this.TextFildEquipamentoUsuario.setText(equipamento.getUsuario());
        this.TextFildEquipamentoUsuario.setText(equipamento.getUsuario());
        this.TextFildEquipamentoEquipamento.setText(equipamento.getEquipamento());
        this.TextFildEquipamentoDescricao.setText(equipamento.getDescricao());
        this.TextFildEquipamentoSerial.setText(equipamento.getSerial());
        this.TextFildEquipamentoTombo.setText(equipamento.getTombo());
        this.TextFildEquipamentoFornecedor.setText(equipamento.getFornecedor());
        this.TextFildEquipamentoMarca.setText(equipamento.getMarca());
        this.TextFildEquipamentoCompuatador.setText(equipamento.getComputador());
        this.TextFildEquipamentoObervacoes.setText(equipamento.getObservacoes());

    }

    @FXML
    public void botaoSalvar() {

        if (validarEntradaDeDados()) {
            equipamento.setSetor(TextFildEquipamentoSetor.getText());
            equipamento.setUsuario(TextFildEquipamentoUsuario.getText());
            equipamento.setEquipamento(TextFildEquipamentoEquipamento.getText());
            equipamento.setDescricao(TextFildEquipamentoDescricao.getText());
            equipamento.setSerial(TextFildEquipamentoSerial.getText());
            equipamento.setTombo(TextFildEquipamentoTombo.getText());
            equipamento.setFornecedor(TextFildEquipamentoFornecedor.getText());
            equipamento.setMarca(TextFildEquipamentoMarca.getText());
            equipamento.setComputador(TextFildEquipamentoCompuatador.getText());
            equipamento.setObservacoes(TextFildEquipamentoObervacoes.getText());
            equipamento.setLocal(TextFildEquipamentoLocal.getText());

            buttonConfimarClicked = true;
            dialogStage.close();
        }

    }

    public void botaoCancelar() {
        dialogStage.close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (TextFildEquipamentoSetor.getText() == null || TextFildEquipamentoSetor.getText().length() == 0) {
            errorMessage += "Campo Setor é Obrigatório!\n";
        }
        if (TextFildEquipamentoUsuario.getText() == null || TextFildEquipamentoUsuario.getText().length() == 0) {
            errorMessage += "Campo Usuario é Obrigatório !\n";
        }
        if (TextFildEquipamentoEquipamento.getText() == null || TextFildEquipamentoEquipamento.getText().length() == 0) {
            errorMessage += "Campo Equipamento é Obrigatório!\n";
        }
        if (TextFildEquipamentoDescricao.getText() == null || TextFildEquipamentoDescricao.getText().length() == 0) {
            errorMessage += "Campo Descrição é Obrigatório!\n";
        }
        if (TextFildEquipamentoSerial.getText() == null || TextFildEquipamentoSerial.getText().length() == 0) {
            errorMessage += "Campo Serial é Obrigatório!\n";
        }
        if (TextFildEquipamentoFornecedor.getText() == null || TextFildEquipamentoFornecedor.getText().length() == 0) {
            errorMessage += "Campo Fornecedor é Obrigatório!\n";
        }
        if (TextFildEquipamentoMarca.getText() == null || TextFildEquipamentoMarca.getText().length() == 0) {
            errorMessage += "Campo Marca é Obrigatório!\n";
        }
/*
        if (TextFildEquipamentoLocal.getText() == null || TextFildEquipamentoLocal.getText().length() == 0) {
            errorMessage += "Campo Local é Obrigatório!\n";
        }
*/
        if (TextFildEquipamentoObervacoes.getText() != null && TextFildEquipamentoObervacoes.getText().length() > 40) {
            errorMessage += "Campo Obervação não pode ultrapassar 39 caracteres!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
