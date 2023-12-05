package com.tamu.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tamu.dao.SubscriptionDao;
import com.tamu.dao.UserManagementDao;
import com.tamu.dto.MyBookDto;
import com.tamu.entity.Book;
import com.tamu.entity.Subscription;
import com.tamu.entity.User;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionService {
	private static final Gson gson = new Gson();
	private static SubscriptionDao dataAdapter = null;

	public static void main(String args[]) throws Exception {
		int myPort = 8083; // Change the port as needed

		ServerSocket listenSocket = new ServerSocket(myPort);
		System.out.println("User Management service waiting for request on port " + myPort);
		dataAdapter = new SubscriptionDao();

		while (true) {
			Socket connectionSocket = listenSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			String requestMessageLine = inFromClient.readLine();
			System.out.println("Request: " + requestMessageLine);

			if (requestMessageLine == null) {
				connectionSocket.close();
				continue;
			}

			String[] requestTokens = requestMessageLine.split(" ");
			String httpMethod = requestTokens[0];
			String requestPath = requestTokens[1];
			Map<String, String> headers = new HashMap<>();
			String headerLine;
			while ((headerLine = inFromClient.readLine()) != null && !headerLine.isEmpty()) {
				String[] header = headerLine.split(": ", 2);
				if (header.length > 1) {
					headers.put(header[0], header[1]);
				}
			}
			if (httpMethod.equals("POST") && requestPath.equals("/subscription/subscribe")) {
				String contentType = headers.get("Content-Type");
				String contentLengthLine = headers.get("Content-Length");
//	                if (contentLengthLine != null) {
				int contentLength = Integer.parseInt(contentLengthLine.trim());
				char[] bodyChars = new char[contentLength];
				inFromClient.read(bodyChars);
				String requestBody = new String(bodyChars);
//	                    if ("application/json".equalsIgnoreCase(contentType)) {
				subscribe(requestBody, outToClient);
//	                    } else {
//	                        sendError(outToClient, "Unsupported Media Type", 415);
//	                    }
//	                } else {
//	                    sendError(outToClient, "Bad Request: Missing content length.", 400);
//	                }
			} else if (httpMethod.equals("GET") && requestPath.equals("/subscription/books/username/")) {
				String username = requestPath.substring("/subscription/books/username/".length());
				List<MyBookDto> allBooks = dataAdapter.getSubscription(username);
				if (allBooks != null) {
					String jsonResponse = gson.toJson(allBooks);
					sendJsonResponse(outToClient, jsonResponse);
				} else {
					sendError(outToClient, "Book Not Found", 404);
				}
			}
			else if (httpMethod.equals("GET") && requestPath.equals("/subscription/userId/")) {
				String userId = requestPath.substring("/subscription/userId/".length());
				int allBooks = dataAdapter.getSubscriptionsByUserId(Integer.parseInt(userId));
				sendJsonResponse(outToClient, userId);
			} 
			else {
				sendError(outToClient, "Invalid request.", 400);
			}

			connectionSocket.close();
		}
	}
    private static void subscribe(String requestBody, DataOutputStream outToClient) throws IOException {
        try {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
        	Subscription subscription = gson.fromJson(requestBody, Subscription.class);

            dataAdapter.insertSubscription(subscription);
            sendJsonResponse(outToClient, "Subscribed Successfully");

        }catch(Exception e) {
            sendError(outToClient, "Error Subscribing", 500);

        }
    }

	private static boolean acceptsJson(String requestMessageLine) throws IOException {
		while (requestMessageLine != null && !requestMessageLine.isEmpty()) {
			if (requestMessageLine.toLowerCase().startsWith("accept:")) {
				return requestMessageLine.contains("application/json");
			}
			return false;
		}
		return false;
	}

	private static void sendJsonResponse(DataOutputStream outToClient, String jsonResponse) throws IOException {
		outToClient.writeBytes("HTTP/1.1 200 OK\r\n");
		outToClient.writeBytes("Content-Type: application/json\r\n");
		outToClient.writeBytes("Content-Length: " + jsonResponse.length() + "\r\n");
		outToClient.writeBytes("\r\n");
		outToClient.writeBytes(jsonResponse);
	}

	private static void sendError(DataOutputStream outToClient, String message, int statusCode) throws IOException {
		String jsonRes = "{\"error\": \"" + message + "\"}";
		String res = "HTTP/1.1 " + statusCode + " Error\r\n" + "Content-Type: application/json\r\n" + "Content-Length: "
				+ jsonRes.length() + "\r\n" + "\r\n" + jsonRes;
		outToClient.writeBytes(res);
		outToClient.flush();
	}

}
