//package com.tamu.service;
//
//import com.google.gson.Gson;
//
//import java.io.*;
//import java.net.*;
//
//public class SubscriptionService {
//
//    private static final int PORT = 8083;
//    private static final String SERVICE_NAME = "order-service";
//    private static final Gson gson = new Gson();
//
//    public static void main(String[] args) throws IOException {
//        ServiceRegistry.registerService(SERVICE_NAME, PORT);
//        System.out.println("Order Service is registered with the Service Registry.");
//
//        ServerSocket serverSocket = new ServerSocket(PORT);
//        System.out.println("Order Service is listening on port " + PORT);
//
//        while (true) {
//            Socket clientSocket = serverSocket.accept();
//            handleRequest(clientSocket);
//        }
//    }
//
//    private static void handleRequest(Socket clientSocket) {
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
//
//            String request = reader.readLine();
//            System.out.println("Order Service received a request: " + request);
//
//            // Handle order service logic based on the request (getOrders, placeOrder, etc.)
//
//            // For illustration purposes, let's assume getOrders returns a JSON response
//            String jsonResponse = getOrders();
//            sendJsonResponse(writer, jsonResponse);
//
//            writer.close();
//            reader.close();
//            clientSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getOrders() {
//        // Dummy logic to return a JSON response (replace with real logic)
//        Order[] orders = {
//                new Order(201, 101, 1),
//                new Order(202, 102, 2)
//        };
//        return gson.toJson(orders);
//    }
//}
