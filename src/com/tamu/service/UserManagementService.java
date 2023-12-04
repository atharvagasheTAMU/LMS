package com.tamu.service;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tamu.dao.UserManagementDao;
import com.tamu.entity.User;

public class UserManagementService {

    private static final Gson gson = new Gson();
    private static UserManagementDao dataAdapter = null;

    public static void main(String args[]) throws Exception {
        int myPort = 8081; // Change the port as needed

        ServerSocket listenSocket = new ServerSocket(myPort);
        System.out.println("User Management service waiting for request on port " + myPort);
        dataAdapter = new UserManagementDao();

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
            if (httpMethod.equals("POST") && requestPath.equals("/user/register")) {
                String contentType = headers.get("Content-Type");
                String contentLengthLine = headers.get("Content-Length");
//                if (contentLengthLine != null) {
                    int contentLength = Integer.parseInt(contentLengthLine.trim());
                    char[] bodyChars = new char[contentLength];
                    inFromClient.read(bodyChars);
                    String requestBody = new String(bodyChars);
//                    if ("application/json".equalsIgnoreCase(contentType)) {
                        registerUser(requestBody, outToClient);
//                    } else {
//                        sendError(outToClient, "Unsupported Media Type", 415);
//                    }
//                } else {
//                    sendError(outToClient, "Bad Request: Missing content length.", 400);
//                }
            }
            else if (httpMethod.equals("POST") && requestPath.equals("/user/login")) {
                String contentType = headers.get("Content-Type");
                String contentLengthLine = headers.get("Content-Length");
//                if (contentLengthLine != null) {
                    int contentLength = Integer.parseInt(contentLengthLine.trim());
                    char[] bodyChars = new char[contentLength];
                    inFromClient.read(bodyChars);
                    String requestBody = new String(bodyChars);
//                    if ("application/json".equalsIgnoreCase(contentType)) {
                        loginUser(requestBody, outToClient, requestMessageLine);
//                    }else {
//                        sendError(outToClient, "Unsupported Media Type", 415);
//                    }
//                } else {
//                    sendError(outToClient, "Bad Request: Missing content length.", 400);
//                }
            } else {
                sendError(outToClient, "Invalid request.", 400);
            }

            connectionSocket.close();
        }
    }
    
    private static void registerUser(String requestBody, DataOutputStream outToClient) throws IOException {
        try {
            User newUser = gson.fromJson(requestBody, User.class);

            if (dataAdapter.isUsernameTaken(newUser.getUsername())) {
                sendError(outToClient, "Username is already taken", 400);
                return;
            }
            if (dataAdapter.isEmailTaken(newUser.getEmail())) {
            	sendError(outToClient, "Email is already taken", 400);
                return;
            }
            if (dataAdapter.saveUser(newUser)) {
                sendJsonResponse(outToClient, "User Registered Successfully");
            } else {
                sendError(outToClient, "Error registering user", 500);
            }
        } catch (Exception e) {
        	sendError(outToClient, "Bad Request: Error registering user", 400);
        }
    }
    
    private static void loginUser(String requestBody, DataOutputStream outToClient, String requestMessageLine) throws IOException {
        try {
            User loginAttempt = gson.fromJson(requestBody, User.class);
            System.out.println("Login Attempt: " + loginAttempt.getUsername() + ", " + loginAttempt.getPassword());

            User existingUser = dataAdapter.loadUser(loginAttempt.getUsername(), loginAttempt.getPassword());
            if (existingUser != null) {
            	
//            	if(existingUser.get)
//            	int membershipTypeID = dataAdapter.loadMembershipType(0)         	
//				if (acceptsJson(requestMessageLine)) {
					String jsonResponse = gson.toJson(existingUser);
					sendJsonResponse(outToClient, jsonResponse);
//				} 
			} else {
				sendError(outToClient, "Invalid username or password", 401);
			}
        } catch (Exception e) {
            sendError(outToClient, "Bad Request: Error logging in", 400);
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
        String res = "HTTP/1.1 " + statusCode + " Error\r\n" + "Content-Type: application/json\r\n" +
                "Content-Length: " + jsonRes.length() + "\r\n" + "\r\n" + jsonRes;
        outToClient.writeBytes(res);
        outToClient.flush();
    }
   

}

