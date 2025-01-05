package dbconfig;
import com.example.demo.dynamo.DynamoDBTableCreator;
public class DynamoDBTableSetup {
    public static void main(String[] args) {
        DynamoDBTableCreator tableCreator = new DynamoDBTableCreator();
        tableCreator.initializeTables();
    }
}
