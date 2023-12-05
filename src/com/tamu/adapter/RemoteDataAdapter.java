package com.tamu.adapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamu.entity.Book;
import com.tamu.entity.User;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteDataAdapter{
    Gson gson = new Gson();
	final int PORT = 8080;

	final String microserviceHost = "localhost";
	
	PrintWriter microserviceWriter;
	Socket microserviceSocket;
	BufferedReader microserviceReader;
	
    public void connect() {
        try {
        	microserviceSocket = new Socket("localhost", PORT);
        	microserviceWriter = new PrintWriter(microserviceSocket.getOutputStream(), true);
    		microserviceReader = new BufferedReader(
    				new InputStreamReader(microserviceSocket.getInputStream()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void disconnect() {
        try {
        	microserviceSocket.close();
        	microserviceWriter.close();
        	microserviceReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public User createMembership(String requestBody) throws UnknownHostException, IOException {
    	String httpMethod = "POST";
    	String path = "/user/membership";
		connect();
		String responseBody = doRESTCall(httpMethod, path, requestBody);
		
        User user = gson.fromJson(responseBody, User.class);
        disconnect();
    	return user;
    }
    public User login(String requestBody) throws UnknownHostException, IOException {
    	String httpMethod = "POST";
    	String path = "/user/login";
		connect();
		String responseBody = doRESTCall(httpMethod, path, requestBody);
		
        User user = gson.fromJson(responseBody, User.class);
        disconnect();
    	return user;
    }
    public String register(String requestBody) throws UnknownHostException, IOException {
    	String httpMethod = "POST";
    	String path = "/user/register";
		connect();
		String responseBody = doRESTCall(httpMethod, path, requestBody);
		
        String response = gson.fromJson(responseBody, String.class);
        disconnect();
    	return response;
    }
    public String addBook(String requestBody) throws UnknownHostException, IOException {
    	String httpMethod = "POST";
    	String path = "/book/addBook";
		connect();
		String responseBody = doRESTCall(httpMethod, path, requestBody);
		
        String response = gson.fromJson(responseBody, String.class);
        disconnect();
    	return response;
    }
    
    public List<Book> getBooks() throws UnknownHostException, IOException {
    	String httpMethod = "GET";
    	String path = "/book/search/all";
		connect();
		String responseBody = doRESTCall(httpMethod, path, "");
		Type bookListType = new TypeToken<List<Book>>(){}.getType();
        List<Book> response = gson.fromJson(responseBody,bookListType);
        disconnect();
    	return response;
    }
    public List<Book> getBooks(String type, String searchString) throws UnknownHostException, IOException {
    	String httpMethod = "GET";
    	String path = "/book/search" + type+ "/" + searchString;
		connect();
		String responseBody = doRESTCall(httpMethod, path, "");
		Type bookListType = new TypeToken<List<Book>>(){}.getType();
        List<Book> response = gson.fromJson(responseBody,bookListType);
        disconnect();
    	return response;
    }
        
	private  String doRESTCall(String httpMethod, String path, String requestBody) {
		try {


//			// Read the entire request body to calculate Content-Length
//			StringBuilder requestBody = new StringBuilder();
//			if (httpMethod.equals("POST")) {
//
//				Map<String, String> headers = new HashMap<>();
//				String headerLine;
//				while ((headerLine = inFromClient.readLine()) != null && !headerLine.isEmpty()) {
//					String[] header = headerLine.split(": ", 2);
//					if (header.length > 1) {
//						headers.put(header[0], header[1]);
//					}
//				}
//				String contentType = headers.get("Content-Type");
//				String contentLengthLine = headers.get("Content-Length");
//
//				if (contentLengthLine != null) {
//					int contentLength = Integer.parseInt(contentLengthLine.trim());
//					char[] bodyChars = new char[contentLength];
//					int bytesRead = inFromClient.read(bodyChars, 0, contentLength);
//
//					if (bytesRead == contentLength) {
//						requestBody = new StringBuilder(new String(bodyChars));
//					} else {
//						// Handle the case where not all expected bytes are read
//						// This could be due to a timeout or incomplete data
//						System.err.println("Error reading request body");
//					}
//				}
//
//				String line;
//				requestBody.append("\r\n");
//
//			}
			
			
			microserviceWriter.println(httpMethod + " " + path + " HTTP/1.1");
			microserviceWriter.println("Host: " + microserviceHost);
			microserviceWriter.println("Content-Type: application/json"); // Change as needed
			microserviceWriter.println("Content-Length: " + requestBody.length());
			microserviceWriter.println();

			if (httpMethod.equals("POST")) {
				microserviceWriter.print(requestBody.toString());
				microserviceWriter.println(); // Ensure a blank line after the request body
				microserviceWriter.flush();
			}

			StringBuilder response = new StringBuilder();
			String line;
			while ((line = microserviceReader.readLine()) != null) {
			    response.append(line).append("\r\n");
			}

			// Convert StringBuilder to String for easy manipulation
			String fullResponse = response.toString();
			String responseBody = null;
			// Find the blank line index to separate headers and body
			int headerBodySeparatorIndex = fullResponse.indexOf("\r\n\r\n");
			if (headerBodySeparatorIndex != -1) {
			    // Extract body from the response
			    responseBody = fullResponse.substring(headerBodySeparatorIndex + 4); // +4 to move past the "\r\n\r\n"
			    
			    // Now you can use the responseBody as needed
			    System.out.println("Response Body: " + responseBody);
			} else {
			    System.err.println("No body found in response");
			}
			
			
			microserviceWriter.close();
			microserviceReader.close();
			microserviceSocket.close();
			
			return responseBody;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
