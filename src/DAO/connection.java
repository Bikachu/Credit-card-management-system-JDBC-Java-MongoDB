package DAO;
import com.mongodb.MongoClient;
import java.util.logging.Level;

public class connection {

    public static void main(String[] args){

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

    }

    private String localhost = "localhost";
    private int port = 27017;


    public MongoClient getConnection(){

        MongoClient mongo = new MongoClient(localhost, port);
        System.out.println("Connect the MongoDB is successful!");
        return mongo;

        }

    public void closeConnection(MongoClient mongo){
        mongo.close();
        System.out.println("Disconnected to the MongoDB!");
    }


}
