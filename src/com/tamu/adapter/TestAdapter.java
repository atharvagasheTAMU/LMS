package com.tamu.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.tamu.entity.Book;
import com.tamu.entity.User;

public class TestAdapter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
	    Gson gson = new Gson();

		user.setUsername("john_doe");
		user.setPassword("password13");
		RemoteDataAdapter adapter = new RemoteDataAdapter();
		String body = gson.toJson(user);
		User loggedinUser=null ;
		List<Book> sampleBooks= new ArrayList<Book>();
		try {
			sampleBooks = adapter.getBooks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gson.toJson(sampleBooks));
	}
	
}
