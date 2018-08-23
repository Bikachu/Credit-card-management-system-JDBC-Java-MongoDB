package DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;


public class customersRUN {

    public static void main(String[] args) {

        System.out.println("You entered the customer details system");
        System.out.println("Enter 1 to check the existing account details");
        System.out.println("Enter 2 to modify the existing account");
        System.out.println("Enter 3 to generate monthly bill for a given month and year");
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

        int input_customer = Input.nextInt();
        if (input_customer == 1) {

            System.out.println("Please enter your social security number:");
            int ssn_1 = Input.nextInt();

            customersImp c1 = new customersImp();
            c1.checkAccount(ssn_1);
        }

        if(input_customer ==2){

            customersImp c2 = new customersImp();

            System.out.println("Please enter your social security number:");
            int ssn_2 = Input.nextInt();

            if(c2.checkSSN(ssn_2).hasNext()){

                Scanner customer_address = new Scanner(System.in);

                System.out.println("Please enter your new Apartment Number: ");
                String apt_no = customer_address.nextLine();

                System.out.println("Please enter your new Street Name: ");
                String street_name = customer_address.nextLine();

                System.out.println("Please enter your new City: ");
                String city = customer_address.nextLine();

                System.out.println("Please enter your new State: ");
                String state = customer_address.nextLine();

                System.out.println("Please enter your new Zip Code: ");
                String zip_code =customer_address.nextLine();

                c2.modifyAddress(apt_no,street_name,city,state,zip_code,ssn_2);
                }
                else{
                System.out.println("The database don't records for you Social Security Number\nPlease check your SSN");
            }
        }

        if(input_customer ==3){

            customersImp c3 = new customersImp();

            System.out.println("Please enter your social security number:");
            int ssn_3 = Input.nextInt();


            if(c3.checkSSN(ssn_3).hasNext()){

                Scanner Input_3 = new Scanner(System.in);

                System.out.println("Please enter your credit card number: ");
                String credit_card_no = Input_3.nextLine();

                System.out.println("Please enter the month");
                int month =Input.nextInt();

                System.out.println("Please enter the year ");
                int year = Input.nextInt();

                c3.getMonthlyBill(credit_card_no,month,year,ssn_3);
            }
            else{
                System.out.println("The database don't records for you Social Security Number\nPlease check your SSN");
            }


        }


    }

}
