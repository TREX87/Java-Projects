import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MyJavaFX extends Application {
	
	Stage window;
	private TextField tfAnnualInterestRate = new TextField();
	private TextField tfNumberOfYears = new TextField();
	private TextField tfLoanAmount = new TextField();
	private TextField tfMonthlyPayment = new TextField();
	private TextField tfTotalPayment = new TextField();
	private Button calculate = new Button("Calculate");
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		// create proper layout
		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setAlignment(Pos.CENTER);
		
		// add all nodes to the layout pane
		gridPane.add(new Label("Annual Interest Rate:"), 0, 0);
		gridPane.add(tfAnnualInterestRate, 1, 0);
		gridPane.add(new Label("Number of Years:"), 0, 1);
		gridPane.add(tfNumberOfYears, 1, 1);
		gridPane.add(new Label("Loan Amount:"), 0, 2);
		gridPane.add(tfLoanAmount, 1, 2);
		gridPane.add(new Label("Monthly Payment:"), 0, 3);
		gridPane.add(tfMonthlyPayment, 1, 3);
		gridPane.add(new Label("Total Payment:"), 0, 4);
		gridPane.add(tfTotalPayment, 1, 4);
		gridPane.add(calculate, 1, 5);
		
		// adjust all elements for appealing interface
		tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT);
		tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
		tfLoanAmount.setAlignment(Pos.BOTTOM_RIGHT);
		tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
		tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);
		GridPane.setHalignment(calculate, HPos.RIGHT);
		tfMonthlyPayment.setEditable(false); // this node should not be edited by user
		tfTotalPayment.setEditable(false); // this node should not be edited by user
		
		// register handler object to it's source object
		calculate.setOnAction(e -> calculatePayment());
		
		// create Scene and put it to the Stage
		Scene scene = new Scene(gridPane, 300, 300);
		window.setTitle("Loan Calculator");
		window.setScene(scene);
		window.show();
		
		
	}
	
	public void calculatePayment() {
		// extract values that user inputed
		double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
		int numberOfYears = Integer.parseInt(tfNumberOfYears.getText());
		double loanAmount = Double.parseDouble(tfLoanAmount.getText());
		
		// create an object of Loan
		Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
		
		tfMonthlyPayment.setText(String.format("$%.2f", loan.getMonthlyPayment()));
		tfTotalPayment.setText(String.format("$%.2f", loan.getTotalPayment()));
		
	}


}