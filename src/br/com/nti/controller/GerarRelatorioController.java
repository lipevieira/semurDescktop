/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nti.controller;

import br.com.nti.database.Database;
import br.com.nti.database.DatabaseFactory;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class GerarRelatorioController implements Initializable {

    @FXML
    private TextField txtRelatorio;

    @FXML
    private ComboBox<String> cbGerarRelatorio;

    @FXML
    private Button btnConfirmaRelatorio, btnGerarRelatorioGeral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBox();

    }

    public void carregarComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList("Local", "Setor", "Usuário", "Equipamento", "Fornecedor", "Marca", "Geral");
        cbGerarRelatorio.setItems(list);

    }

    // Atributos para manipulação de banco de dados.
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = (Connection) database.conectar();

    private Stage dialogStage;
    private boolean buttonConfimarClicked = false;

    @FXML
    public void gerarRelatorio() throws JRException {

        if (cbGerarRelatorio.getValue() != null && txtRelatorio.getText().length() > 0) {

            String coluna = cbGerarRelatorio.getValue();
            HashMap filtro = new HashMap();
            String consulta = txtRelatorio.getText();

            filtro.put("consulta", consulta);
            filtro.put("coluna", coluna);
            URL url = getClass().getResource("/br/com/nti/relatorio/JAVAFXMLRelatorioEquipamento.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtro
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setVisible(true);
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor informe uma colnua da tabela!");
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();

        }
    }

    @FXML
    public void gerarRelatorioCompleto() throws JRException {
        HashMap filtro = new HashMap();
        String consulta = "geral";
        String coluna = "geral";

        filtro.put("consulta", consulta);
        filtro.put("coluna", coluna);
        URL url = getClass().getResource("/br/com/nti/relatorio/JAVAFXMLRelatorioEquipamento.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtro
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
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

}
