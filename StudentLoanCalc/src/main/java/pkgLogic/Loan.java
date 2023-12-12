package pkgLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private int LoanPaymentCnt;
	private boolean bCompoundingOption;
	private LocalDate StartDate;
	private double AdditionalPayment;
	private double Escrow;

	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();

	public Loan(double loanAmount, double interestRate, int loanPaymentCnt, LocalDate startDate,
			double additionalPayment, double escrow) {
		super();
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		LoanPaymentCnt = loanPaymentCnt * 12;
		StartDate = startDate;
		AdditionalPayment = additionalPayment;
		bCompoundingOption = false;
		LoanBalanceEnd = 0;
		this.Escrow = escrow;

		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		
		if (AdditionalPayment == 0){
			int i = 0;
			
			while(RemainingBalance > GetPMT()) {
				Payment p1 = new Payment(RemainingBalance, PaymentCnt, StartDate.plusMonths(i), this, false);
				loanPayments.add(p1);
				
				//RemainingBalance = RemainingBalance - (GetPMT() + AdditionalPayment);
				RemainingBalance -= p1.getPrinciple();
				i++;
				PaymentCnt++;
			}
			
			Payment finalPayment = new Payment(RemainingBalance, PaymentCnt, StartDate.plusMonths(i), this, true);
			loanPayments.add(finalPayment);
		}
		
		else {
			int i = 0;
			while(RemainingBalance > GetPMT() + AdditionalPayment) {
				Payment p1 = new Payment(RemainingBalance, PaymentCnt, StartDate.plusMonths(i), this, false);
				loanPayments.add(p1);
				
				RemainingBalance -= p1.getPrinciple();
				i++;
				PaymentCnt++;
			}
			
			Payment finalPayment = new Payment(RemainingBalance, PaymentCnt, StartDate.plusMonths(i), this, true);
			loanPayments.add(finalPayment);
			
			RemainingBalance -= finalPayment.getPrinciple();
			PaymentCnt++;
			i++;
			
			Payment finalPayment2 = new Payment(RemainingBalance, PaymentCnt, StartDate.plusMonths(i), this, true);
			loanPayments.add(finalPayment2);
			
			
		}
				
		
		
		
		
	}

	public double GetPMT() {
		double PMT = 0;
		double r = InterestRate / 12;
		double n = LoanPaymentCnt;
		double p = LoanAmount;
		double f = 0;
		boolean t = false;
		PMT = FinanceLib.pmt(r, n, p, f, t);
		return Math.abs(PMT);
	}

	public double getTotalPayments() {
		double tot = 0;
		for (Payment p : loanPayments) {
			tot += p.getPayment();
		}
		return tot;
	}

	public double getTotalInterest() {

		double interest = 0;
		for (Payment p : loanPayments) {
			interest += p.getInterestPayment();
		}
		
		return interest;

	}

	public double getTotalEscrow() {

		double escrow = 0;
		for (Payment p : loanPayments) {
			escrow += p.getEscrowPayment();
		}
		return escrow;

	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public void setLoanBalanceEnd(double loanBalanceEnd) {
		LoanBalanceEnd = loanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	public int getLoanPaymentCnt() {
		return LoanPaymentCnt;
	}

	public void setLoanPaymentCnt(int loanPaymentCnt) {
		LoanPaymentCnt = loanPaymentCnt;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}

	public void setbCompoundingOption(boolean bCompoundingOption) {
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public void setAdditionalPayment(double additionalPayment) {
		AdditionalPayment = additionalPayment;
	}

	public ArrayList<Payment> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(ArrayList<Payment> loanPayments) {
		this.loanPayments = loanPayments;
	}

	public double getEscrow() {
		return Escrow;
	}

}