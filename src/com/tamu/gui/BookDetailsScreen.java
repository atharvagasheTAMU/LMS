package com.tamu.gui;
import javax.swing.*;

import com.tamu.entity.Book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class BookDetailsScreen extends JFrame implements ActionListener {
    private JLabel lblBookTitle = new JLabel();
    private JLabel lblAuthor = new JLabel();
    private JLabel lblGenre = new JLabel();
    private JLabel lblDescription = new JLabel();
    private JLabel lblPrice = new JLabel();
    private JLabel lblQuantity = new JLabel();
    private JButton btnBack = new JButton("Back to Dashboard");

    public BookDetailsScreen(Book book) {
        this.setTitle("Book Details");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 300);

        lblBookTitle.setText("Title: " + book.getBookName());
        lblAuthor.setText("Author: " + book.getAuthorName());
        lblGenre.setText("Genre: " + book.getGenre());
        lblDescription.setText("Description: " + book.getBookDescription());
        lblPrice.setText("Price: $" + book.getBookPrice());
        lblQuantity.setText("Quantity Available: " + book.getQuantity());

        this.getContentPane().add(lblBookTitle);
        this.getContentPane().add(lblAuthor);
        this.getContentPane().add(lblGenre);
        this.getContentPane().add(lblDescription);
        this.getContentPane().add(lblPrice);
        this.getContentPane().add(lblQuantity);
        this.getContentPane().add(btnBack);

        btnBack.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            BookDashboardScreen bookDashboardScreen = null;
			try {
				bookDashboardScreen = new BookDashboardScreen();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            setVisible(false);
            bookDashboardScreen.setVisible(true);
        }
    }
}