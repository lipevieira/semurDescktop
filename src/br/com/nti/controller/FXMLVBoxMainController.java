/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastroEquipamento;

    @FXML
    private MenuItem menuItemDevolucao;

    @FXML
    private MenuItem menuItemConsultar;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void chamarMenuItemCadastroEquipamento() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/nti/view/FXMLCadastroEquipamento.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void chamarMenuItemCadastroEmprestimo() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/nti/view/FXMLEquipamentosEmprestados.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    public void chamarMenuItemOCS() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/br/com/nti/view/FXMLAbrirOCS.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
