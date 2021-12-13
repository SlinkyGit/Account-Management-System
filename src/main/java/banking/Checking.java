package banking;

public class Checking extends Account {

    public Checking(String type, String id, double apr) {
        super(type, id, apr);
    }

    @Override
    public boolean validDeposit(double value) {
        return ((value >= 0) && (value <= 1000));
    }

    @Override
    public boolean validWithdraw(double value) {
        return ((value >= 0) && (value <= 400));
    }

    @Override
    public void withdraw(double value) {
        if (this.balance < value) {
            this.balance = 0;
        } else {
            this.balance -= value;
        }
    }

}
