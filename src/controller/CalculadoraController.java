package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import model.Calculadora_GPON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CalculadoraController {

    @FXML
    private ChoiceBox<Double> Atenuacao_ChoiceBox;

    @FXML
    private Button Calcular_Button;

    @FXML
    private Text ConectorPerda_Label;

    @FXML
    private ChoiceBox<Integer> Conector_ChoiceBox;

    @FXML
    private Text DOS1Perda_Label;

    @FXML
    private ChoiceBox<String> DOS1_ChoiceBox;

    @FXML
    private Text DOS2Perda_Label;

    @FXML
    private ChoiceBox<String> DOS2_ChoiceBox;

    @FXML
    private Text DistPerda_Label;

    @FXML
    private TextField Dist_InputText;

    @FXML
    private Text FreqAtenuacao_Label;

    @FXML
    private Text FusaoPerda_Label;

    @FXML
    private ChoiceBox<Integer> Fusao_ChoiceBox;

    @FXML
    private TextField MS_InputText;

    @FXML
    private TextField Pt_InputText;

    @FXML
    private TextField Sen_InputText;
    
    @FXML
    private Text Resultado_Text;
    
    @FXML
    private Text FusaoPerda_Text;
    
    @FXML
    private Text ConectorPerda_Text;
    
    @FXML
    private Text DistAtenuacao_Text;
    
    private int sum;
    private String texto;
    private float perdas;
    
    private final int POTENCIA = 9;
    private final int SENSIBILIDADE = 8;
    private final int DISTANCIA = 7;
    private final int MARGEM_SEGURANCA = 6;
    
    Alert errorAlert;
    private Calculadora_GPON calculadora;
    
    @FXML
    public void initialize() {
    	calculadora = new Calculadora_GPON();
    	Atenuacao_ChoiceBox.getItems().addAll(0.35,0.25,0.2);
    	Atenuacao_ChoiceBox.setValue(0.35);
    	Atenuacao_ChoiceBox.setStyle("-fx-font-size: 16px;");
    	Conector_ChoiceBox.getItems().addAll(4,8);
    	Conector_ChoiceBox.setStyle("-fx-font-size: 16px;");
    	Conector_ChoiceBox.setValue(4);
    	Fusao_ChoiceBox.getItems().addAll(2,4);
    	Fusao_ChoiceBox.setValue(2);
    	Fusao_ChoiceBox.setStyle("-fx-font-size: 16px;");
    	DOS1_ChoiceBox.getItems().addAll("0","1:4 (7dB)","1:8 (10dB)","1:16 (14.3dB)","1:32 (17dB)");
    	DOS1_ChoiceBox.setValue("0");
    	DOS1_ChoiceBox.setStyle("-fx-font-size: 16px;");
    	DOS2_ChoiceBox.getItems().addAll("0","1:4 (7dB)","1:8 (10dB)","1:16 (14.3dB)","1:32 (17dB)");
    	DOS2_ChoiceBox.setValue("0");
    	DOS2_ChoiceBox.setStyle("-fx-font-size: 16px;");
    	sum=0;
    	errorAlert = new Alert(AlertType.ERROR);
    	// aumentar o tamanho do diálogo
    	errorAlert.getDialogPane().setPrefSize(500,300);

    	// customizar a fonte do conteúdo via CSS inline
    	errorAlert.getDialogPane().lookup(".content.label").setStyle("-fx-font-size: 18px;");
    	Calcular_Button.setOnAction(this::calcular);
        
    };
    
    
    
    
    public void calcular(ActionEvent event){
    //	if() {}
    //	else if() {}
    //	else if() {}
    	sum=0;
    	perdas = 0;
    	texto = Pt_InputText.getText();
    	if (texto == null || texto.trim().isEmpty()) {
    	    
    	} else {
    	    sum+=1;
    	}
    	
    	texto = Sen_InputText.getText();
    	if (texto == null || texto.trim().isEmpty()) {
    	    
    	} else {
    	    sum+=2;
    	}
    	
    	texto = Dist_InputText.getText();
    	if (texto == null || texto.trim().isEmpty()) {
    	    
    	} else {
    	    sum+=3;
    	}
    	
    	texto = MS_InputText.getText();
    	if (texto == null || texto.trim().isEmpty()) {
    	    
    	} else {
    	    sum+=4;
    	}
    	
    	switch (sum) {
    	
			case POTENCIA: {
				if(!verificarTextField(Sen_InputText)) break;
				if(!verificarSensibilidade(Float.parseFloat(Sen_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para sensibilidade está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(Dist_InputText)) break;	
				if(!verificarDist(Float.parseFloat(Dist_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para distância está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(MS_InputText)) break;
				if(!verificarMargem(Float.parseFloat(MS_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para margem está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				String resposta = calculadora.calcularPotencia(Sen_InputText, Dist_InputText, MS_InputText, Atenuacao_ChoiceBox, Fusao_ChoiceBox, Conector_ChoiceBox, DOS1_ChoiceBox, DOS2_ChoiceBox);				
				float aux = (float) (Float.parseFloat(Dist_InputText.getText()) * Atenuacao_ChoiceBox.getValue());
				DistAtenuacao_Text.setText(String.format("%.2f", aux) + "dB");
				aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
				FusaoPerda_Text.setText(String.format("%.2f", aux) + "dB");
				aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
				ConectorPerda_Text.setText(String.format("%.2f", aux) + "dB");
				Resultado_Text.setText(resposta);
				break;
			}
			
			
			
			case SENSIBILIDADE:{
				if(!verificarTextField(Pt_InputText)) break;
				if(!verificarPotencia(Float.parseFloat(Pt_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para Px está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(Dist_InputText)) break;
				if(!verificarDist(Float.parseFloat(Dist_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para distância está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(MS_InputText)) break;
				if(!verificarMargem(Float.parseFloat(MS_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para margem de segurança está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				
				float sensibilidade = (float) calculadora.calcularSensibilidade(Pt_InputText, Dist_InputText, MS_InputText, Atenuacao_ChoiceBox, Fusao_ChoiceBox, Conector_ChoiceBox, DOS1_ChoiceBox, DOS2_ChoiceBox);
				float aux = (float) (Float.parseFloat(Dist_InputText.getText()) * Atenuacao_ChoiceBox.getValue());
				DistAtenuacao_Text.setText(String.format("%.2f", aux) + "dB");
				aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
				FusaoPerda_Text.setText(String.format("%.2f", aux) + "dB");
				aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
				ConectorPerda_Text.setText(String.format("%.2f", aux) +"dB");
				Resultado_Text.setText("Sensibilidade necessária: " + String.format("%.2f", sensibilidade) + "dB");
				break;
			}
			
			
			
			case DISTANCIA:{
				if(!verificarTextField(Sen_InputText)) break;
				if(!verificarSensibilidade(Float.parseFloat(Sen_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para sensibilidade está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(Pt_InputText)) break;
				if(!verificarPotencia(Float.parseFloat(Pt_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para Px está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
				if(!verificarTextField(MS_InputText)) break;
				if(!verificarMargem(Float.parseFloat(MS_InputText.getText( ) ) ) ) {
					errorAlert.setTitle("Erro");
					errorAlert.setHeaderText("Ocorreu um erro");
					errorAlert.setContentText("O valor digitado para margem de segurança está fora dos padrões!");
					errorAlert.showAndWait();
					break;
				};
//				System.out.println("Dist");
				String resposta = calculadora.calcularDistancia(Pt_InputText, Sen_InputText, MS_InputText, Atenuacao_ChoiceBox, Fusao_ChoiceBox, Conector_ChoiceBox, DOS1_ChoiceBox, DOS2_ChoiceBox);
				float aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
				FusaoPerda_Text.setText(String.format("%.2f", aux) + "dB");
				aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
				ConectorPerda_Text.setText(String.format("%.2f", aux) + "dB");
				Resultado_Text.setText(resposta);
				break;
			}
		}
    }

    public boolean verificarTextField(TextField textField) {
    	String text = textField.getText();
        return text != null && !text.isEmpty() && !text.trim().isEmpty() && text.matches("-?\\d+(\\.\\d+)?");
    }
    
    public float parseTextFieldToFloat(TextField textField) {
        return Float.parseFloat(textField.getText());
    }
    
    public boolean verificarDist(float dist) {
    	if (dist < 0 || dist > 100) {
    		System.out.println("Dist fora do intervalo");
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public boolean verificarSensibilidade(float sen) {
    	if (sen > 0 || sen < -50) {
    		System.out.println("Sensibilidade fora do intervalo");
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public boolean verificarMargem(float margem) {
    	if (margem < 0 || margem > 20) {
    		System.out.println("Margem fora do intervalo");
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public boolean verificarPotencia(float pot) {
    	if(pot < 0 || pot > 20) {
    		System.out.println("Potencia fora do intervalo");
    		return false;
    	}else {
    		return true;
    	}
    }
    

}
