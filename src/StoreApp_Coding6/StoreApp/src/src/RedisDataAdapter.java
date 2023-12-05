import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

import redis.clients.jedis.Connection;

public class RedisDataAdapter {
    private Jedis jedis;
	private Gson gson;


    public RedisDataAdapter() {
    	this.jedis = new Jedis("redis://default:rBYWOfDmQKCHv0Ji3TUCTC68wgjHJ89V@redis-12923.c10.us-east-1-2.ec2.cloud.redislabs.com:12923");
		this.gson = new Gson();
    }
    
    public void saveProduct(Product product) {
        String key = "product:" + product.getProductID();
        String value = serializeProduct(product);
        jedis.set(key, value);
    }

    public Product loadProduct(int productID) {
        String key = "product:" + productID;
        String value = jedis.get(key);
        return deserializeProduct(value);
    }
    
    public User loadUser(String username, String password) {
        try {
            String key = "user:" + username + ":" + password;
            String userString = jedis.get(key);

            if (userString != null) {
                return deserializeUser(userString);
            }
        } catch (Exception e) {
            System.out.println("Error loading user from Redis!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveUser(User user) {
        try {
            String key = "user:" + user.getUsername() + ":" + user.getPassword();
            String userString = serializeUser(user);
            jedis.set(key, userString);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving user to Redis!");
            e.printStackTrace();
            return false;
        }
    }

    public CreditCard loadCreditCard(int cardId) {
        try {
            String key = "creditCard:" + cardId;
            String cardString = jedis.get(key);

            if (cardString != null) {
                return deserializeCreditCard(cardString);
            }
        } catch (Exception e) {
            System.out.println("Error loading credit card from Redis!");
            e.printStackTrace();
        }
        return null;
    }

    public int saveCreditCard(CreditCard creditCard) {
    	Long cardID = jedis.incr("cardID");
    	creditCard.setCardId(cardID.intValue());
        String cardString = gson.toJson(creditCard);
        jedis.set("card:" + cardID, cardString);
        return cardID.intValue();
    }

    public Address loadAddress(int addressId) {
        try {
            String key = "address:" + addressId;
            String addressString = jedis.get(key);
            if (addressString != null) {
                return deserializeAddress(addressString);
            }
        } catch (Exception e) {
            System.out.println("Error loading address from Redis!");
            e.printStackTrace();
        }
        return null;
    }

    public int saveAddress(Address address) {
    	Long addressId = jedis.incr("addressID");
        address.setAddressId(addressId.intValue());
        String addressString = gson.toJson(address);
        jedis.set("address:" + addressId, addressString);
        return addressId.intValue();
    }

    private String serializeProduct(Product product) {
        Gson gson = new Gson();
        return gson.toJson(product);
    }

    private Product deserializeProduct(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, Product.class);
    }
    
    private String serializeUser(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    private User deserializeUser(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, User.class);
    }

    private CreditCard deserializeCreditCard(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, CreditCard.class);
    }

    private Address deserializeAddress(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, Address.class);
    }
}
