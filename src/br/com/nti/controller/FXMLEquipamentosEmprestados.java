package br.com.nti.controller;

import br.com.nti.dao.EmprestimoDAO;
import br.com.nti.database.Database;
import br.com.nti.database.DatabaseFactory;
import br.com.nti.modelo.Emprestimo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class FXMLEquipamentosEmprestados implements Initializable {

    @FXML
    private TableView<Emprestimo> tableViewEmprestimo;

    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoId;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoSerial;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoSetor;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoEquipamento;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoDestino;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoNome;

    @FXML
    private TableColumn<Emprestimo, LocalDate> tableColumnEmprestimoDataDevolucao;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoTombo;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoDescricao;

    @FXML
    private TableColumn<Emprestimo, LocalDate> tableColumnEmprestimoDataSaida;

    @FXML
    private TableColumn<Emprestimo, String> tableColumnEmprestimoNumero;

    @FXML
    private Button handleButtonAlterarEmprestimo, btnRelatorioEquipamentosEmprestado, handleButtonDevolverEquipamento, btn_atualizar_tabela_emprestimo,
            btnSegundaVia;

    @FXML
    private TextField txtPesquisaEmprestimo;

    @FXML
    private Label lblTotalEquipamentos;

    private List<Emprestimo> listaEmprestimo;
    private ObservableList<Emprestimo> observableListEmprestimo;

    // Atributos para manipulação de banco de dados.
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = (Connection) database.conectar();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    Emprestimo emprestimo = new Emprestimo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emprestimoDAO.setConnection(connection);
        carregarTableViewEmprestimo();
        tableViewEmprestimo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEquipamento(newValue));
        txtPesquisaEmprestimo.setOnKeyReleased((e) -> {
            tableViewEmprestimo.setItems(buscaNaTabelaEmprestimo());
        });

        totalEquipamento();

    }

    // Pegar Itens da tabela
    public void selecionarItemTableViewEquipamento(Emprestimo emprestimo) {

    }

    @FXML
    public void totalEquipamento() {
        Integer totalEquipamentos = emprestimoDAO.contarEquipamento();
        lblTotalEquipamentos.setText(totalEquipamentos.toString());
    }

    public void carregarTableViewEmprestimo() {
        tableColumnEmprestimoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnEmprestimoSerial.setCellValueFactory(new PropertyValueFactory<>("serialEquipamento"));
        tableColumnEmprestimoSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tableColumnEmprestimoEquipamento.setCellValueFactory(new PropertyValueFactory<>("equipamento"));
        tableColumnEmprestimoDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        tableColumnEmprestimoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //Formatando a Data na TableView
        tableColumnEmprestimoDataSaida.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
        tableColumnEmprestimoDataSaida.setCellFactory(column -> {
            DateTimeFormatter myDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new TableCell<Emprestimo, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setText("");
                    } else {
                        setText(myDate.format(item));
                    }
                }
            };
        });
        //Formatando a Data na TableView
        tableColumnEmprestimoDataDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
        tableColumnEmprestimoDataDevolucao.setCellFactory(column -> {
            DateTimeFormatter myDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new TableCell<Emprestimo, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setText("");
                    } else {
                        setText(myDate.format(item));
                    }
                }
            };
        });

        tableColumnEmprestimoTombo.setCellValueFactory(new PropertyValueFactory<>("tombo"));
        tableColumnEmprestimoNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumnEmprestimoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        listaEmprestimo = emprestimoDAO.listar();
        observableListEmprestimo = FXCollections.observableArrayList(listaEmprestimo);
        tableViewEmprestimo.setItems(observableListEmprestimo);
    }

    @FXML
    public void handleButtonAlterarEmprestimo() throws IOException {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        Emprestimo emprestimo = tableViewEmprestimo.getSelectionModel().getSelectedItem();

        if (emprestimo != null) {

            boolean buttonConfirmarClicked = FXMLCadastroEmprestimoDIalog(emprestimo);
            if (buttonConfirmarClicked) {

                try {
                    emprestimoDAO.alterar(emprestimo);
                    carregarTableViewEmprestimo();
                    alert1.setContentText("Emprestimo Alterado com Sucesso!");
                    alert1.getDialogPane().setStyle("-fx-font-size: 14;");
                    alert1.show();
                    carregarTableViewEmprestimo();

                } catch (Exception e) {
                    carregarTableViewEmprestimo();
                    alert1.setContentText("Erro ao Alterar Equipamento!");
                    alert1.getDialogPane().setStyle("-fx-font-size: 14;");
                    alert1.show();

                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, escolha um equipamento  que está emprestado  na Tabela para altera-lo!");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        try {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            HashMap filtro = new HashMap();
            Emprestimo emprestimo = tableViewEmprestimo.getSelectionModel().getSelectedItem();
            if (emprestimo != null) {
                filtro.put("id", emprestimo.getId());

                // ESSE COMANDO IMPRIMIE O RELATORIO DE DEVOLUÇÃO DO EQUIPAMENTO.
                URL url = getClass().getResource("/br/com/nti/relatorio/DevolucaoEquipamento.jasper");
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtros

                alert1.setContentText("Deseja realmente devolver este equipamento ?");
                alert1.getDialogPane().setStyle("-fx-font-size: 14;");

                Optional<ButtonType> action = alert1.showAndWait();
                if (action.get() == ButtonType.OK) {
                    JasperPrintManager.printPage(jasperPrint, 0, true);
                    emprestimoDAO.remover(emprestimo);
                    carregarTableViewEmprestimo();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Por favor, escolha um equipamento que está emprestado na Tabela!");
                alert.getDialogPane().setStyle("-fx-font-size: 14;");
                alert.show();
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERRO ! Não foi Possivel devolver o Equipamento!" + ex);
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
            Logger.getLogger(FXMLEquipamentosEmprestados.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean FXMLCadastroEmprestimoDIalog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(FXMLVBoxMainControllerDialogController.class
                        .getResource("/br/com/nti/view/FXMLCadastroEmprestimoDIalog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Emprestimo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Setando o cliente no Controller.
        FXMLCadastroEmprestimoController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        // controller.setEmprestimo(emprestimo);
        controller.carregarDadosParaAlterar(emprestimo);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfimarClicked();
    }

    public ObservableList<Emprestimo> buscaNaTabelaEmprestimo() {
        ObservableList<Emprestimo> emprestimoPesquisa = FXCollections.observableArrayList();
        for (int i = 0; i < observableListEmprestimo.size(); i++) {
            if (observableListEmprestimo.get(i).getSerialEquipamento().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getSetor().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getEquipamento().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getDestino().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getNome().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getNumero().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())
                    || observableListEmprestimo.get(i).getDescricao().toLowerCase().contains(txtPesquisaEmprestimo.getText().toLowerCase())) {
                emprestimoPesquisa.add(observableListEmprestimo.get(i));
            }
        }
        return emprestimoPesquisa;
    }

    // botão para Gerar o Relatorio geral de Emprestimo
    @FXML
    public void handleButtonGerarRelatorioEQuipametoEmprestado() throws JRException {

        URL url = getClass().getResource("/br/com/nti/relatorio/JAVAFXMLRelatorioEquipamentosEmprestados.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros

        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

    @FXML
    public void gerarSegundaVia() throws JRException {

        HashMap filtro = new HashMap();
        Emprestimo emprestimo = tableViewEmprestimo.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
            filtro.put("id", emprestimo.getId());
            URL url = getClass().getResource("/br/com/nti/relatorio/SaidaEquipamentoMaterial.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtros

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor selecione um equipamento na Tabela!");
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
        }

    }

}
