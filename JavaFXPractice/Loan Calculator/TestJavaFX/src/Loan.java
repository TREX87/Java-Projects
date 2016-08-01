
public class Loan {
	private double annualInterestRate;
	private int numberOfYears;
	private double loanAmount;
	private java.util.Date loanDate;
	
	/** Default constructor
	 */
	public Loan() {
		this(2.5, 2, 1000);
	}
	
	// construct a loan with specified annual interest rate,
	// number of years, and loan amount
	public Loan(double annualInterestRate, int numberOfYears,
			double loanAmount) {
		this.annualInterestRate = annualInterestRate;
		this.numberOfYears = numberOfYears;
		this.loanAmount = loanAmount;
		loanDate = new java.util.Date();
	}
	
	// return annualInterestRate
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}
	
	// set numberOfYears
	public int getNumberOfYears() {
		return numberOfYears;
	}
	
	// set a new numberOfYears
	public void setNumberOfYears(int numberOfYears) {
		this.numberOfYears = numberOfYears;
	}
	
	// return loanAmount
	public double getLoanAmount() {
		return loanAmount;
	}
	
	// set a new loanAmount
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	// find monthly payment
	public double getMonthlyPayment() {
		double monthlyInterestRate = annualInterestRate / 1200;
		double monthlyPayment = loanAmount * monthlyInterestRate / (1 - 
				(1 / Math.pow(1+monthlyInterestRate, numberOfYears * 12)));
		return monthlyPayment;
	}
	
	// find total payment
	public double getTotalPayment() {
		double totalPayment = getMonthlyPayment() * numberOfYears * 12;
		return totalPayment;
	}
	
	public java.util.Date getLoanDate() {
		return loanDate;
	}
}