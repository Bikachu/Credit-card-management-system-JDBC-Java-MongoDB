package DAO;

public interface transactionDAO {

    public void getTransactionByZipcode(String Zip_code, int month, int year, int ssn);
    public void TransactionNumberAndValue(String transaction_type);

}

