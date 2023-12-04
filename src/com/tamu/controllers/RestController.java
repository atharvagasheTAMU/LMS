package com.tamu.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
//import java.sql.Connection;
//import java.sql.DriverManager;
import java.util.StringTokenizer;

import com.tamu.service.ServiceRegistry;


public class RestController {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final String USER_MANAGEMENT_SERVICE = "user-management-service";
    private static final String BOOK_SERVICE = "book-service";
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("API Controller is listening on port " + PORT);
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket connectionSocket) {
        try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//			Class.forName("org.sqlite.JDBC"); // connect to its local database
//			Connection connection = DriverManager.getConnection("jdbc:sqlite:store.db");
//			dataAdapter = new DataAdapter(connection);
			final int BOOK_SERVICE_PORT = 8082;
	        final int USER_MANAGEMENT_SERVICE_PORT = 8081;
			String requestMessageLine = inFromClient.readLine();

			if (requestMessageLine == null) {
				connectionSocket.close();
				return;
			}

			StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine, " ");
			String httpMethod = tokenizedLine.nextToken();
			String requestPath = tokenizedLine.nextToken();
			System.out.println("Request: " + requestMessageLine);
			int x =1;
			if (requestPath.startsWith("/user/")) {
			    delegateRequestToMicroservice(httpMethod, requestPath, inFromClient, outToClient, HOST, USER_MANAGEMENT_SERVICE_PORT);
			}else if (requestPath.startsWith("/book/")) {
			    delegateRequestToMicroservice(httpMethod, requestPath, inFromClient, outToClient, HOST, BOOK_SERVICE_PORT);
			} else {
			    sendError(outToClient, "Invalid request.", 500);
			}
//			if (httpMethod.equals("GET") && requestPath.startsWith("/product/")) {
//				int productId = Integer.parseInt(requestPath.substring("/product/".length()));
//				Product product = dataAdapter.loadProduct(productId);
//				if (product != null) {
//					if (acceptsJson(inFromClient)) {
//						String jsonResponse = getJsonProduct(product);
//						sendJsonResponse(outToClient, jsonResponse);
//					} else {
//						String htmlResponse = getHtmlProduct(product);
//						sendHtmlResponse(outToClient, htmlResponse);
//					}
//				} else {
//					if (acceptsJson(inFromClient)) {
//						sendJsonError(outToClient, "Product Not Found", 404);
//					} else {
//						sendHtmlError(outToClient, "Product Not Found", 404);
//					}
//				}
//			} else if (httpMethod.equals("POST") && requestPath.equals("/product/update")) {
//				handlePostRequest(requestPath, inFromClient, outToClient);
//			} else {
//				sendError(outToClient, "Invalid request.", 500);
//			}

			connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void delegateRequestToMicroservice(String httpMethod, String path, BufferedReader inFromClient, DataOutputStream outToClient, String microserviceHost, int microservicePort) {
//        try {
//            Socket microserviceSocket = new Socket(microserviceHost, microservicePort);
//
//            PrintWriter microserviceWriter = new PrintWriter(microserviceSocket.getOutputStream(), true);
//            BufferedReader microserviceReader = new BufferedReader(new InputStreamReader(microserviceSocket.getInputStream()));
//
//            // Forward the request to the microservice
//            microserviceWriter.println(httpMethod + " " + path + " HTTP/1.1");
//            microserviceWriter.println("Host: " + microserviceHost);
//            microserviceWriter.println();
//
//            // If it's a POST request, read the request body and forward it
//            if (httpMethod.equals("POST")) {
//                String line;
//                while ((line = inFromClient.readLine()) != null && !line.isEmpty()) {
//                    microserviceWriter.println(line);
//                }
//                microserviceWriter.println(); // Ensure a blank line after the request body
//            }
//
//            // Receive the response from the microservice
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = microserviceReader.readLine()) != null) {
//                response.append(line).append("\n");
//            }
//
//            // Forward the response back to the client
//            outToClient.writeBytes(response.toString());
//
//            microserviceWriter.close();
//            microserviceReader.close();
//            microserviceSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void delegateRequestToMicroservice(String httpMethod, String path, BufferedReader inFromClient, DataOutputStream outToClient, String microserviceHost, int microservicePort) {
        try {
            Socket microserviceSocket = new Socket(microserviceHost, microservicePort);

            PrintWriter microserviceWriter = new PrintWriter(microserviceSocket.getOutputStream(), true);
            BufferedReader microserviceReader = new BufferedReader(new InputStreamReader(microserviceSocket.getInputStream()));

            // Read the entire request body to calculate Content-Length
            StringBuilder requestBody = new StringBuilder();
            if (httpMethod.equals("POST")) {
            	
            	Map<String, String> headers = new HashMap<>();
        		String headerLine;
        		while ((headerLine = inFromClient.readLine()) != null && !headerLine.isEmpty()) {
        			String[] header = headerLine.split(": ", 2);
        			if (header.length > 1) {
        				headers.put(header[0], header[1]);
        			}
        		}
    			String contentType = headers.get("Content-Type");
    			String contentLengthLine = headers.get("Content-Length");
    			
    			
    			if (contentLengthLine != null) {
    			    int contentLength = Integer.parseInt(contentLengthLine.trim());
    			    char[] bodyChars = new char[contentLength];
    			    int bytesRead = inFromClient.read(bodyChars, 0, contentLength);

    			    if (bytesRead == contentLength) {
    			        requestBody = new StringBuilder(new String(bodyChars));
    			    } else {
    			        // Handle the case where not all expected bytes are read
    			        // This could be due to a timeout or incomplete data
    			        System.err.println("Error reading request body");
    			    }
    			}
    			
                String line;
//                while ((line = inFromClient.readLine()) != null && !line.isEmpty()) {
                    requestBody.append("\r\n");
//                }
            }

            // Forward the request to the microservice with Content-Type and Content-Length headers
            microserviceWriter.println(httpMethod + " " + path + " HTTP/1.1");
            microserviceWriter.println("Host: " + microserviceHost);
            microserviceWriter.println("Content-Type: application/json"); // Change as needed
            microserviceWriter.println("Content-Length: " + requestBody.length());
            microserviceWriter.println();

            // If it's a POST request, write the request body
            if (httpMethod.equals("POST")) {
                microserviceWriter.print(requestBody.toString());
                microserviceWriter.println(); // Ensure a blank line after the request body
                microserviceWriter.flush();
            }

            // Receive the response from the microservice
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = microserviceReader.readLine()) != null) {
                response.append(line).append("\r\n");
            }

            // Forward the response back to the client
            outToClient.writeBytes(response.toString());

            // Close resources
            inFromClient.close();
            outToClient.close();
            microserviceWriter.close();
            microserviceReader.close();
            microserviceSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static void sendError(DataOutputStream outToClient, String message, int statusCode) throws IOException {
		// Create a JSON representation of the error message
		String jsonResponse = "{\"error\": \"" + message + "\"}";

		// Construct and send the HTTP response
		String response = "HTTP/1.1 " + statusCode + " Error\r\n" + "Content-Type: application/json\r\n"
				+ "Content-Length: " + jsonResponse.length() + "\r\n" + "\r\n" + jsonResponse;
		outToClient.writeBytes(response);
		outToClient.flush();
	}


}

