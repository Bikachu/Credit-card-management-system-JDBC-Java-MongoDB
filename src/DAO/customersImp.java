package DAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.*;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Projections;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;



public class customersImp implements  customersDAO
{
    public customersImp(){
        super();
    }

    private MongoClient mongoclient = null;
    private MongoDatabase db = null;
    private MongoCollection col_branch = null;  //db.getCollection("CDW_SAPP_BRANCH");
    private MongoCollection col_creditcard = null; //db.getCollection("CDW_SAPP_CREDITCARD");
    private MongoCollection col_customer =null;  //db.getCollection("CDW_SAPP_CUSTOMER");

    private String creditcard = "CDW_SAPP_CREDITCARD";
    private String customer = "CDW_SAPP_CUSTOMER";
    private String branch = "CDW_SAPP_BRANCH";


    @Override
    public void checkAccount(int ssn){

        connection c = new connection();
        mongoclient = c.getConnection();
        db = mongoclient.getDatabase("creditcard");

        col_creditcard = db.getCollection(creditcard);


        Document filter = new Document(
                "$match", new Document("SSN", ssn));

        Document lookup = new Document(
                "$lookup", new Document("from", "CDW_SAPP_CUSTOMER").append("localField","SSN")
                                                                        .append("foreignField","SSN")
                                                                        .append("as", "customer"));

        AggregateIterable output = col_creditcard.aggregate(Arrays.asList(filter,lookup));

        Iterator it2 = output.iterator();
        while(it2.hasNext()){

            System.out.println(it2.next());

        }

        c.closeConnection(mongoclient);
    }

    @Override
    public void  modifyAddress(String apt_no,String street_name, String cust_city, String cust_state, String cust_zip, int ssn){


            Document filter = new Document();
            filter.append("SSN", ssn);

            Document set = new Document();
            set.append("APT_NO", apt_no).append("STREET_NAME", street_name).append("CUST_CITY", cust_city).append("CUST_STATE", cust_state).append("CUST_ZIP", cust_zip);

            Document update = new Document();
            update.append("$set", set);

            col_customer.updateOne(filter, update);

            FindIterable myDoc = col_customer.find(eq("SSN", ssn)).projection(Projections.include("SSN", "APT_NO", "STREET_NAME", "CUST_CITY", "CUST_STATE", "CUST_ZIP"));
            if (myDoc.iterator().hasNext()) {
                System.out.println(myDoc.iterator().next());
            }

        }

    @Override
    public void getMonthlyBill(String credit_card_no, int month, int year,int ssn){

        connection c = new connection();
        mongoclient = c.getConnection();
        db = mongoclient.getDatabase("creditcard");

        col_creditcard = db.getCollection(creditcard);

        Document filter = new Document(
                "$match", new Document("SSN", ssn).append("MONTH",month).append("YEAR",year));

        Document group = new Document(
                "$group", new Document("_id", null).append("total bill", new Document( "$sum", "$TRANSACTION_VALUE" ))
        );

        AggregateIterable output = col_creditcard.aggregate(Arrays.asList(filter,group));

        Iterator it2 = output.iterator();
        if(it2.hasNext()){

            System.out.println(it2.next());

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
