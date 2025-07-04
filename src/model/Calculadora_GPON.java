package model;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Calculadora_GPON {
	
	private final String QUATRO = "1:4 (7dB)";
	private final String OITO = "1:8 (10dB)";
	private final String DEZESSEIS = "1:16 (14.3dB)";
	private final String TRINTAEDOIS = "1:32 (17dB)";
	private float aux, perdas;
	
	public float calcularPowerBudget(float sensibilidadeReceptor, float potenciaTransmissao){
		float powerBudget = potenciaTransmissao - sensibilidadeReceptor;
		return powerBudget;
		
	}
	
	public float calcularSensibilidadeReceptor(float powerBudget, float potenciaTransmissao){
		float sensibilidadeReceptor = potenciaTransmissao - powerBudget;
		return sensibilidadeReceptor;
		
	}
	
	public float calcularPotenciaTransmissao(float sensibilidadeReceptor, float powerBudget){
		float potenciaTransmissao =  powerBudget + sensibilidadeReceptor;
		return potenciaTransmissao;
		
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
    
	public String calcularPotencia(TextField Sen_InputText, TextField Dist_InputText, TextField MS_InputText, ChoiceBox<Double> Atenuacao_ChoiceBox, ChoiceBox<Integer> Fusao_ChoiceBox, ChoiceBox<Integer> Conector_ChoiceBox, ChoiceBox<String> DOS1_ChoiceBox, ChoiceBox<String> DOS2_ChoiceBox ) {
		perdas =0;
		aux =0;
		
		aux = (float) (Float.parseFloat(Dist_InputText.getText()) * Atenuacao_ChoiceBox.getValue());
		perdas += aux;
		aux = (Float.parseFloat(MS_InputText.getText()));
		perdas += aux;
		aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
		perdas+= aux;
		aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
		perdas += aux;
		String dos1 = DOS1_ChoiceBox.getValue();
		switch (dos1) {
			case QUATRO:{
				perdas += 7;
				break;
			}
			case OITO:{
				perdas += 10;
				break;
			}
			case DEZESSEIS:{
				perdas += 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas += 17;
				break;
			}
		}
		String dos2 = DOS2_ChoiceBox.getValue();
		switch (dos2) {
			case QUATRO:{
				perdas += 7;
				break;
			}
			case OITO:{
				perdas += 10;
				break;
			}
			case DEZESSEIS:{
				perdas += 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas += 17;
				break;
			}
		}
		System.out.println("Perdas: " + perdas);
		float diferenca = perdas + Float.parseFloat(Sen_InputText.getText());
		System.out.println(diferenca);
		if(diferenca < 0) {
			System.out.println("Ja eh suficicente");
			String resposta = "Potência mínima necessária: 1.5dB";
			return resposta;	
		}else {
			float pot_necessaria = (float) (diferenca + 0.1);
			String resposta = "Potência mínima necessária: "  + String.format("%.2f", pot_necessaria) + "dB";
			return resposta;	
		}

	}
	
	public double calcularSensibilidade(TextField Pt_InputText, TextField Dist_InputText, TextField MS_InputText, ChoiceBox<Double> Atenuacao_ChoiceBox, ChoiceBox<Integer> Fusao_ChoiceBox, ChoiceBox<Integer> Conector_ChoiceBox, ChoiceBox<String> DOS1_ChoiceBox, ChoiceBox<String> DOS2_ChoiceBox){
		
		perdas = 0;
		aux = 0;
		aux = Float.parseFloat(Pt_InputText.getText());
		perdas+= aux;
		aux = (float) (Float.parseFloat(Dist_InputText.getText()) * Atenuacao_ChoiceBox.getValue());
		perdas -= aux;
		aux = (Float.parseFloat(MS_InputText.getText()));
		perdas -= aux;
		aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
		perdas -= aux;
		aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
		perdas -= aux;
		String dos1 = DOS1_ChoiceBox.getValue();
		switch (dos1) {
			case QUATRO:{
				perdas -= 7;
				break;
			}
			case OITO:{
				perdas -= 10;
				break;
			}
			case DEZESSEIS:{
				perdas -= 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas -= 17;
				break;
			}
		}
		String dos2 = DOS2_ChoiceBox.getValue();
		switch (dos2) {
			case QUATRO:{
				perdas -= 7;
				break;
			}
			case "OITO":{
				perdas -= 10;
				break;
			}
			case DEZESSEIS:{
				perdas -= 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas -= 17;
				break;
			}
		}
		return perdas;

		
	}
	
	public String calcularDistancia(TextField Pt_InputText, TextField Sen_InputText, TextField MS_InputText, ChoiceBox<Double> Atenuacao_ChoiceBox, ChoiceBox<Integer> Fusao_ChoiceBox, ChoiceBox<Integer> Conector_ChoiceBox, ChoiceBox<String> DOS1_ChoiceBox, ChoiceBox<String> DOS2_ChoiceBox) {
		perdas = 0;
		aux = 0;
		aux = Float.parseFloat(Sen_InputText.getText());
		aux *= -1;
		perdas = aux;
		aux = Float.parseFloat(Pt_InputText.getText());
		perdas += aux;
		aux = (Float.parseFloat(MS_InputText.getText()));
		perdas -= aux;
		aux = (float) (Fusao_ChoiceBox.getValue() * 0.1);
		perdas -= aux;
		aux = (float) (Conector_ChoiceBox.getValue() * 0.5);
		perdas -= aux;
		String dos1 = DOS1_ChoiceBox.getValue();
		switch (dos1) {
			case QUATRO:{
				perdas -= 7;
				break;
			}
			case OITO:{
				perdas -= 10;
				break;
			}
			case DEZESSEIS:{
				perdas -= 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas -= 17;
				break;
			}
		}
		String dos2 = DOS2_ChoiceBox.getValue();
		switch (dos2) {
			case QUATRO:{
				perdas -= 7;
				break;
			}
			case OITO:{
				perdas -= 10;
				break;
			}
			case DEZESSEIS:{
				perdas -= 14.3;
				break;
			}
			case TRINTAEDOIS:{
				perdas -= 17;
				break;
			}
		}
		if(perdas < 0.2) {
			return "Orçamento estourado";
		}else {
			float dist = (float) (perdas/Atenuacao_ChoiceBox.getValue());
			String resposta = "Distância máxima: " + String.format("%.2f", dist) + "km";
			return resposta;
		}
	}

}
