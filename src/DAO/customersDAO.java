package DAO;
public interface customersDAO {


    public void checkAccount(int ssn);
    public void modifyAddress(String apt_no,String street_name, String cust_city, String cust_state, String cust_zip, int ssn );
    public void getMonthlyBill(String credit_card_no, int month, int year,int ssn);

}

