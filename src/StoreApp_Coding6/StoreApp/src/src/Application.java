import java.sql.*;

public class Application {

    private static Application instance;

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    private Connection connection;

    public Connection getDBConnection() {
        return connection;
    }

    private RedisDataAdapter redisDataAdapter;
    private MongoDBDataAdapter mongoDBDataAdapter;

    private User currentUser = null;

    public User getCurrentUser() { return currentUser; }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public LoginScreen loginScreen = new LoginScreen();

    public LoginScreen getLoginScreen() {
        return loginScreen;
    }

    private ProductViewController productViewController;

    public ProductViewController getProductViewController() {
        return productViewController;
    }

    private OrderViewController orderViewController;

    public OrderViewController getOrderViewController() {
        return orderViewController;
    }

    private Application() {

        productViewController = new ProductViewController();

        orderViewController = new OrderViewController();
        redisDataAdapter = new RedisDataAdapter();
        mongoDBDataAdapter = new MongoDBDataAdapter();
    }


    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
    
    public RedisDataAdapter getRedisDataAdapter() {
        return redisDataAdapter;
    }

    public MongoDBDataAdapter getMongoDBDataAdapter() {
        return mongoDBDataAdapter;
    }
}
