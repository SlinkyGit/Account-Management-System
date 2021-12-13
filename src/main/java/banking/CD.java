package banking;

public class CD extends Account {

    public CD(String type, String id, double apr, double balance) {
        super(type, id, apr);
        this.balance = balance;
    }

    @Override
    public boolean validDeposit(double value) {
        return false;
    }

    @Override
    public boolean validWithdraw(double value) {
        return getBalance() <= value;
    }

    @Override
    public void withdraw(double value) {
        if ((super.getAge() >= 12) && (super.getAge() <= 60)) {
            balance = 0;
        }
    }

    @Override
    public void calculateAPR() {
        for (int i = 0; i < 4; i++) {
            double interestRate = (getBalance() * getCalcApr());
            this.balance += interestRate;
        }
    }
}



