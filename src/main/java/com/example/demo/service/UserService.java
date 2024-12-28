package com.example.demo.service;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    private final DynamoDbTable<User> userTable;
    public UserService(DynamoDbEnhancedClient enhancedClient) {
        this.userTable = enhancedClient.table("User", TableSchema.fromBean(User.class));
    }
    public void createUser(User user) {
        userTable.putItem(user);
    }
    public User getUser(String userId) {
        return userTable.getItem(r -> r.key(k -> k.partitionValue(userId)));
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userTable.scan().items().forEach(users::add);
        return users;
    }
    public String getRole(String userId) {
        User user = userTable.getItem(r -> r.key(k -> k.partitionValue(userId)));
        if (user != null) {
            return user.getRole();
        }
        return null;
    }
    public void deleteUser(String userId) {
        userTable.deleteItem(r -> r.key(k -> k.partitionValue(userId)));
    }
}