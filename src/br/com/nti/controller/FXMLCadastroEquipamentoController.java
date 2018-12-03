package br.com.nti.controller;

import br.com.nti.dao.EmprestimoDAO;
import br.com.nti.dao.EquipamentoDAO;
import br.com.nti.database.Database;
import br.com.nti.database.DatabaseFactory;
import br.com.nti.modelo.Emprestimo;
import br.com.nti.modelo.Equipamento;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

/**
 * FXML Controller class
 *
 * @author Filipe.Santos
 */
public class FXMLCadastroEquipamentoController implements Initializable {

    @FXML
    private TableView<Equipamento> tableViewEquipamento;

    @FXML
    private TableColumn<Equipamento, Integer> tableColumnEQuipamentoId;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoSetor;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoUsuario;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoEquipamento;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoDescricao;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoSerial;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoTombo;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoFornecedor;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoMarca;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoComputador;
    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoObservacoes;

    @FXML
    private TableColumn<Equipamento, String> tableColumnEQuipamentoLocal;

    @FXML
    private TableColumn<Equipamento, CheckBox> tableColumnEQuipamentoSelecinar;

    @FXML
    private Button btn_incluir, btn_alterar, btn_deletar, btn_teste, btn_relatorio_equipamento, btnAbrirOCS;

    @FXML
    private Label lbl_mostra, lblTotalEquipamentos;

    @FXML
    private TextField txt_pesquisar;

    @FXML
    private Button btn_pesquisar, btnTesteEmprestimo, btn_atualizarTabela, btn_empresta_equipamento, btnteste;
    private List<Equipamento> listaEquipamento;
    private ObservableList<Equipamento> observableListEquipamento = FXCollections.observableArrayList();
    //  private List<String> listaComBox = new ArrayList<>();

    // Atributos para manipulação de banco de dados.
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = (Connection) database.conectar();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        equipamentoDAO.setConnection(connection);
        emprestimoDAO.setConnection(connection);

        carregarTableViewEQuipamento();
        tableViewEquipamento.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEquipamento(newValue));

        // Fazendo pesquiar na tabela 
        txt_pesquisar.setOnKeyReleased((e) -> {
            tableViewEquipamento.setItems(buscaNaTabela());
        });
        totalEquipamento();
    }

    public void carregarTableViewEQuipamento() {

        tableColumnEQuipamentoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnEQuipamentoSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tableColumnEQuipamentoUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tableColumnEQuipamentoEquipamento.setCellValueFactory(new PropertyValueFactory<>("equipamento"));
        tableColumnEQuipamentoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnEQuipamentoSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        tableColumnEQuipamentoTombo.setCellValueFactory(new PropertyValueFactory<>("tombo"));
        tableColumnEQuipamentoFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        tableColumnEQuipamentoMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnEQuipamentoComputador.setCellValueFactory(new PropertyValueFactory<>("computador"));
        tableColumnEQuipamentoObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        tableColumnEQuipamentoLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
        tableColumnEQuipamentoSelecinar.setCellValueFactory(new PropertyValueFactory<>("selecionar"));

        listaEquipamento = equipamentoDAO.listar();
        observableListEquipamento = FXCollections.observableArrayList(listaEquipamento);
        tableViewEquipamento.setItems(observableListEquipamento);
    }

    // Pegar Itens da tabela
    public void selecionarItemTableViewEquipamento(Equipamento equipamento) {
    }
   
    @FXML
    public void totalEquipamento(){
        Integer totalEquipamentos = equipamentoDAO.contarEquipamento();
        lblTotalEquipamentos.setText(totalEquipamentos.toString());
    }
    @FXML
    public void handleButtonInserir() throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Equipamento equipamento = new Equipamento();
            boolean buttonConfirmarClicked = FXMLVBoxMainControllerDialogController(equipamento);
            if (buttonConfirmarClicked) {

                if (equipamentoDAO.inserir(equipamento) == true) {
                    alert.setContentText("Equipamento Cadastrado com Sucesso!");
                    alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
                    alert.getDialogPane().setStyle("-fx-font-size: 14;");
                    alert.show();
                    carregarTableViewEQuipamento();
                } else {
                    Alert alerteERRO = new Alert(Alert.AlertType.ERROR);
                    alerteERRO.setContentText("Os campos Serial ou Tombo não pode se repetir!");
                    alerteERRO.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
                    alerteERRO.getDialogPane().setStyle("-fx-font-size: 14;");
                    alerteERRO.show();
                }
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERRO ao Salvar Equipamento!");
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
        }

    }

    @FXML
    public void handleButtonAlterar() throws IOException, SQLException {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        Equipamento equipamento = tableViewEquipamento.getSelectionModel().getSelectedItem();
        if (equipamento != null) {
            boolean buttonConfirmarClicked = FXMLVBoxMainControllerDialogController(equipamento);
            if (buttonConfirmarClicked) {
                equipamentoDAO.alterar(equipamento);
                alert1.setContentText("Equipamento Alterado com Sucesso!");
                alert1.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
                alert1.getDialogPane().setStyle("-fx-font-size: 14;");

                alert1.show();
                carregarTableViewEQuipamento();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, escolha um equipamento  na Tabela!");
            alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
            alert.getDialogPane().setStyle("-fx-font-size: 14;");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException, SQLException {

        Equipamento equipamento = null;
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setContentText("Deseja realmente Excluir este equipamento ?");
        alert1.getDialogPane().setStyle("-fx-font-size: 14;");
        Optional<ButtonType> action = alert1.showAndWait();

        if (action.get() == ButtonType.OK) {
            for (int i = 0; i < tableViewEquipamento.getItems().size(); i++) {
                equipamento = tableViewEquipamento.getItems().get(i);
                if (tableViewEquipamento.getItems().get(i).getSelecionar().isSelected()) {
                    equipamentoDAO.remover(equipamento);
                    if (equipamentoDAO.remover(equipamento) == false) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Não é possivel excluir equipamentos emprestados!");
                        a.show();
                        a.getDialogPane().setStyle("-fx-font-size: 14;");
                        break;
                    }
                }
            }
        }
    }

    public boolean FXMLVBoxMainControllerDialogController(Equipamento equipamento) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(FXMLVBoxMainControllerDialogController.class
                        .getResource("/br/com/nti/view/FXMLVBoxMainControllerDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Equipamento");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Setando o equipamento no Controller.
        FXMLVBoxMainControllerDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEquipamento(equipamento);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfimarClicked();
    }

    // Metodo para Maninupular o Dialog Stage de Emprestimo
    public boolean FXMLCadastroEmprestimoDIalog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(FXMLCadastroEmprestimoController.class
                        .getResource("/br/com/nti/view/FXMLCadastroEmprestimoDIalog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Emprestimo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Setando o emprestimo no Controller.
        FXMLCadastroEmprestimoController controller = loader.getController();
        Equipamento equipamento = tableViewEquipamento.getSelectionModel().getSelectedItem();
        controller.setDialogStage(dialogStage);

        controller.setEquipamento(equipamento);
        controller.setEmprestimo(emprestimo);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfimarClicked();
    }

    @FXML
    public void handleButtonEmpresta() throws IOException, SQLException, JRException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setStyle("-fx-font-family: \"times new roman\", Gerogia, Serif;");
        alert.getDialogPane().setStyle("-fx-font-size: 14;");
        Emprestimo emprestimo = new Emprestimo();

        Equipamento equipamento = tableViewEquipamento.getSelectionModel().getSelectedItem();

        if (equipamento != null) {
            boolean buttonConfirmarClicked = FXMLCadastroEmprestimoDIalog(emprestimo);

            if (buttonConfirmarClicked) {
                if (emprestimoDAO.inserir(emprestimo) == true) {
                    gerarReciboEquipamentoEmprestado();
                    alert.setContentText("Equipamento Emprestado com Sucesso!");
                    alert.show();

                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setContentText("ERRO!  Ao Realizar Emprestimo! Verrifique se o EQUIPAMENTO está Emprestado!");
                    alert1.show();
                }

            }
        } else {
            Alert alertErroAoSelecionar = new Alert(Alert.AlertType.WARNING);
            alertErroAoSelecionar.setContentText("Por favor, escolha um equipamento  na Tabela!");
            alertErroAoSelecionar.show();
        }

    }

    @FXML
    public ObservableList<Equipamento> buscaNaTabela() {
        ObservableList<Equipamento> equipamentoPesquisa = FXCollections.observableArrayList();
        for (int i = 0; i < observableListEquipamento.size(); i++) {
            if (observableListEquipamento.get(i).getSetor().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getUsuario().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getEquipamento().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getDescricao().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getSerial().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getFornecedor().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getMarca().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getFornecedor().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())
                    || observableListEquipamento.get(i).getLocal().toLowerCase().contains(txt_pesquisar.getText().toLowerCase())) {
                equipamentoPesquisa.add(observableListEquipamento.get(i));
            }
        }
        return equipamentoPesquisa;
    }

    @FXML
    public boolean chamaTelaRelatorio() throws JRException, IOException {
        FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(FXMLVBoxMainControllerDialogController.class
                        .getResource("/br/com/nti/view/GerarRelatorio.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Gerar Relatórios");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        // Setando o equipamento no Controller.
        GerarRelatorioController relatorio = loader.getController();
        relatorio.setDialogStage(dialogStage);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return relatorio.isButtonConfimarClicked();

    }

    public void gerarReciboEquipamentoEmprestado() throws JRException {
        HashMap filtro = new HashMap();
        Integer id = 0;
        filtro.put("id", id);
        URL url = getClass().getResource("/br/com/nti/relatorio/SaidaEquipamentoMaterial.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtros
        JasperPrintManager.printPage(jasperPrint, 0, true);
    }
    
    @FXML
    public boolean chamarTelaOcs() throws IOException{
          FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(FXMLCadastroEmprestimoController.class
                        .getResource("/br/com/nti/view/FXMLAbrirOCS.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Sistema OCS");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(true);

        // Setando o emprestimo no Controller.
        FXMLAbrirOCSController controller = loader.getController();
        controller.setDialogStage(dialogStage);

      
    //    controller.setEmprestimo(emprestimo);
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfimarClicked();
    }

}
