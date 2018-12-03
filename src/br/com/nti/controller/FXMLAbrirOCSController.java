/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author FIlipe.Santos
 */
public class FXMLAbrirOCSController implements Initializable {

    @FXML
    private WebView webView;

    private Stage dialogStage;
    private boolean buttonConfimarClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abrirOcs();
    }

    private void abrirOcs() {
        WebEngine endine = webView.getEngine();
        endine.load("http://192.168.187.6/ocsreports/");
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

}
