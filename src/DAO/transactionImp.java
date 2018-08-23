package DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class transactionImp implements transactionDAO{

    public transactionImp() {
        super(); }

    private MongoClient mongoclient = null;
    private MongoDatabase db = null;
    private MongoCollection col_branch = null;  //db.getCollection("CDW_SAPP_BRANCH");
    private MongoCollection col_creditcard = null; //db.getCollection("CDW_SAPP_CREDITCARD");
    private MongoCollection col_customer =null;  //db.getCollection("CDW_SAPP_CUSTOMER");

    private String creditcard = "CDW_SAPP_CREDITCARD";
    private String customer = "CDW_SAPP_CUSTOMER";
    private String branch = "CDW_SAPP_BRANCH";

    @Override
    public void getTransactionByZipcode(String Zip_code, int month, int year, int ssn){

        connection c = new connection();
        mongoclient = c.getConnection();
        db = mongoclient.getDatabase("creditcard");

        col_creditcard = db.getCollection(creditcard);


        Document filter = new Document(
                "$match", new Document("SSN", ssn).append("MONTH",month).append("YEAR",year));


        Document lookup = new Document(
                "$lookup", new Document("from", "CDW_SAPP_CUSTOMER").
                append("localField","SSN").
                append("foreignField","SSN").
                append("as", "customer"));

        AggregateIterable output = col_creditcard.aggregate(Arrays.asList(filter,lookup));

        Iterator it2 = output.iterator();
        while(it2.hasNext()){

            System.out.println(it2.next());

        }

        c.closeConnection(mongoclient);

    }

    @Override
    public void TransactionNumberAndValue(String transaction_type){

        connection c = new connection();
        mongoclient = c.getConnection();
        db = mongoclient.getDatabase("creditcard");

        col_creditcard = db.getCollection(creditcard);


        Document filter = new Document(
                "$match", new Document("TRANSACTION_TYPE",transaction_type));

        Document group = new Document(
                "$group", new Document("_id", null).append("total value", new Document( "$sum", "$TRANSACTION_VALUE" ))
        );

        AggregateIterable output = col_creditcard.aggregate(Arrays.asList(filter,group));

        Iterator it2 = output.iterator();
        if(it2.hasNext()){

            System.out.println(it2.next());

        }
        else{
            System.out.println("No result");
        }

    }

    public Iterator checkSSN(int ssn){

        Scanner Input = new Scanner(System.in);

        connection c = new connection();
        mongoclient = c.getConnection();
        db = mongoclient.getDatabase("creditcard");

        col_creditcard = db.getCollection(creditcard);

        Document ssn_check = new Document();
        ssn_check.put("SSN", ssn);

        FindIterable ssn_doc = col_creditcard.find(ssn_check);
        Iterator it = ssn_doc.iterator();

        return it;
    }

}
