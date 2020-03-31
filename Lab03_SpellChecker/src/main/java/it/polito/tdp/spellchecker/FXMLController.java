package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary dictionary = new Dictionary();

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> cbxScelta;

    @FXML
    private TextArea txtInserimento;

    @FXML
    private Button btnCheck;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Label txtErrore;

    @FXML
    private Button btnClear;

    @FXML
    private Label txtCompletamento;
    
    private ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    void doClearText(ActionEvent event) {
    	txtRisultato.clear();
    	txtInserimento.clear();
    	txtErrore.setText("");
    	txtCompletamento.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	dictionary.loadDictionary(cbxScelta.getValue());
    	String ts = txtInserimento.getText().toLowerCase().replaceAll("[.,\\/#!?$%\\^&\\+;:{}=\\-_'~()\\[\\]\"]", "");
    	List<String> inserimento = new LinkedList<>();
    	String array[] = ts.split(" ");
    	for(String s : array) {
    		inserimento.add(s);
    	}
    	List<RichWord> controllo = new LinkedList<>();
    	double start = System.nanoTime();
    	controllo = this.dictionary.spellCheckText(inserimento);
    	double stop = System.nanoTime();    
    	int numParoleErrate = 0;
    	List<String> paroleErrate = new LinkedList<>();
    	for (RichWord r : controllo) {
    		if(r.isCorretta() == false) {
    			numParoleErrate++;
    			paroleErrate.add(r.getParola());
    		}
    	}
    	String risultato = "";
    	for (String s : paroleErrate)
    		risultato += s + " ";
    	txtRisultato.setText(risultato);
    	txtErrore.setText("The text contains " +numParoleErrate+ " errors");
    	txtCompletamento.setText("Spell check completed in " + (stop - start)/1e9 + " seconds");
    }

    @FXML
    void initialize() {
        assert cbxScelta != null : "fx:id=\"cbxScelta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInserimento != null : "fx:id=\"txtInserimento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrore != null : "fx:id=\"txtErrore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCompletamento != null : "fx:id=\"txtCompletamento\" was not injected: check your FXML file 'Scene.fxml'.";
        list.addAll("English","Italian");
        cbxScelta.setItems(list);
        cbxScelta.setValue("Italian");
    }
    
    public void setDictionary(Dictionary dictionary) {
    	this.dictionary = dictionary;
    }
}
