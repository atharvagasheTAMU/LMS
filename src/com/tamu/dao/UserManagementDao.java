package com.tamu.dao;
import com.google.gson.Gson;
import com.tamu.entity.Address;
import com.tamu.entity.Book;
import com.tamu.entity.Card;
import com.tamu.entity.MembershipType;
import com.tamu.entity.User;

import redis.clients.jedis.Jedis;

public class UserManagementDao {
	private Jedis jedis;
	private Gson gson;

	public UserManagementDao() {
		jedis = new Jedis("redis://default:WwyCVfKfto11N1c71IooaBUVLQIAd2yB@redis-14238.c309.us-east-2-1.ec2.cloud.redislabs.com:14238");

        System.out.println("Reddis Connection successfull in UserManagementDao");
		this.gson = new Gson();
	}

	public int saveAddress(Address address) {
        int addressId = jedis.incr("addressID").intValue();
        address.setAddressId(addressId);
        String addressJson = gson.toJson(address);
        jedis.set("address:" + addressId, addressJson);
        return addressId;
	}


	public int saveCard(Card card) {
		int cardID = jedis.incr("cardID").intValue();;
        card.setCardID(cardID);
        String cardJson = gson.toJson(card);
        jedis.set("card:" + cardID, cardJson);
        return cardID;
	}

	public boolean saveUser(User user) {
		int userID = jedis.incr("userID").intValue();;
		user.setUserId(userID);
        String userJson = gson.toJson(user);
        jedis.set("user:" + userID, userJson);
        jedis.set("email:" + user.getEmail(), Double.toString(userID) );
        jedis.set("username:" + user.getUsername(), Double.toString(userID));

        return true;
	}


	public User loadUser(String username, String password) {
        String userID = jedis.get("username:" + username);
        if (userID != null) {
            String userJson = jedis.get("user:" + userID);
            if (userJson != null) {
                User user = gson.fromJson(userJson, User.class);
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
	}
	public User loadUser(int id) {
		String bookJson = jedis.get("user:" + id);
        return bookJson != null ? gson.fromJson(bookJson, User.class) : null;
	}
	public boolean isEmailTaken(String email) {
            String jsonUser = jedis.get("email:" + email);
            return jsonUser != null;
    }
	public boolean isUsernameTaken(String username) {
            String jsonUser = jedis.get("username:" + username);
            return jsonUser != null;
    }
	
	public MembershipType loadMembershipType(int id) {
		String membershipTypeJson = jedis.get("membership_type:" + id);
        return membershipTypeJson != null ? gson.fromJson(membershipTypeJson, MembershipType.class) : null;
	}
}
