package com.pluralsight;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptService {
    public static void printReceipt(Order order) {
        System.out.println("\n----- RECEIPT -----");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getReceiptText());
        }
        System.out.printf("Total: $%.2f%n", order.getTotal());
        System.out.println("-------------------\n");
    }

    public static void saveReceiptToFile(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String fileName = "receipts/" + LocalDateTime.now().format(formatter) + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (OrderItem item : order.getItems()) {
                writer.write(item.getReceiptText() + System.lineSeparator());
            }
            writer.write(String.format("Total: $%.2f%n", order.getTotal()));
            System.out.println("Receipt saved to: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to save receipt: " + e.getMessage());
        }
    }
}