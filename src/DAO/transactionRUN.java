package DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;
import java.util.logging.Level;

public class transactionRUN {

    public static void main(String[] args) {

        System.out.println("You entered the transaction details system");
        System.out.println("Enter 1 to display the transaction for a month and year");
        System.out.println("Enter 2 to display the number and total value of transactions for a given type");
        System.out.println("Please choose an option");

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        MongoClient mongoclient = null;
        MongoDatabase db = null;
        MongoCollection col_branch = null;  //db.getCollection("CDW_SAPP_BRANCH");
        MongoCollection col_creditcard = null; //db.getCollection("CDW_SAPP_CREDITCARD");
        MongoCollection col_customer =null;  //db.getCollection("CDW_SAPP_CUSTOMER");

        String creditcard = "CDW_SAPP_CREDITCARD";
        String customer = "CDW_SAPP_CUSTOMER";
        String branch = "CDW_SAPP_BRANCH";


        Scanner Input = new Scanner(System.in);

        int input_transaction = Input.nextInt();

        if(input_transaction==1){

            System.out.println("Please enter your social security number:");
            int ssn_1 = Input.nextInt();

            transactionImp t1 = new transactionImp();
            if(t1.checkSSN(ssn_1).hasNext()){

                Scanner transaction_1 = new Scanner(System.in);

                System.out.println("Please enter your zipcode: ");
                String zip_code = transaction_1.nextLine();

                System.out.println("Please enter your month: ");
                int month  = transaction_1.nextInt();

                System.out.println("Please enter your year: ");
                int year = transaction_1.nextInt();

                t1.getTransactionByZipcode(zip_code,month,year,ssn_1);

            }


        }
        if(input_transaction ==2){

            System.out.println("Please enter your social security number:");
            int ssn_2 = Input.nextInt();

            transactionImp t2 = new transactionImp();
            if(t2.checkSSN(ssn_2).hasNext()){

                Scanner transaction_2 = new Scanner(System.in);

                System.out.println("Please enter a transaction type: ");
                String transaction_type = transaction_2.nextLine();
                t2.TransactionNumberAndValue(transaction_type);


            }
        }

        

    }
}
