package classes;

public class BankAccount {

    private int accountBalance;

    public void deposit(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountBalance += amount;
    }
    public void withdraw(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(amount > accountBalance){
            throw new IllegalArgumentException("Insufficient funds");
        }
        accountBalance -= amount;
    }
    public int getBalance(){
        return accountBalance;
    }
}