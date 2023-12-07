package com.tamu.gui;

import com.tamu.dto.MyBookDto;
import com.tamu.entity.Book;
import com.tamu.entity.Subscription;
import com.tamu.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class MyBooksScreen extends JFrame implements ActionListener {

    private JPanel bookListPanel;

    public MyBooksScreen() throws UnknownHostException, IOException {
        this.setTitle("My Books");
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        // Panel for book display
        bookListPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(bookListPanel);

        // Combine all panels in the main layout
        this.add(scrollPane, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BookDashboardScreen bookDashboardScreen = new BookDashboardScreen();
                    setVisible(false);
                    bookDashboardScreen.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.add(btnBack, BorderLayout.SOUTH);

        // Load the user's subscribed/rented books and display them
        loadMyBooks();
    }

    private void loadMyBooks() throws UnknownHostException, IOException {
        User user = Application.getInstance().getCurrentUser();
        List<MyBookDto> myBooks = Application.getInstance().getDataAdapter().getUserSubscriptions(Integer.toString(user.getUserId()));

        // Clear existing books
        bookListPanel.removeAll();

        // Create book panels for each subscribed/rented book
        for (MyBookDto myBook : myBooks) {
        	myBook.setImageURL("src/images/book.png");
            bookListPanel.add(createBookPanel(myBook));
        }

        bookListPanel.revalidate();
        bookListPanel.repaint();
    }

    private JPanel createBookPanel(MyBookDto book) {
        JPanel bookPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(book.getBookName());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(titleLabel, BorderLayout.NORTH);

        // Load the image using ImageIcon
        ImageIcon imageIcon = new ImageIcon(book.getImageURL());
        JLabel imageLabel = new JLabel(imageIcon);
        bookPanel.add(imageLabel, BorderLayout.CENTER);

        // You can add more labels or components to display other details like author, price, etc.

		/*
		 * JButton detailsButton = new JButton("View Details");
		 * detailsButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { BookDetailsScreen
		 * bookDetailsScreen = new BookDetailsScreen(book); setVisible(false);
		 * bookDetailsScreen.setVisible(true); } });
		 */

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the return functionality here
                // You can prompt the user for confirmation and then update the database accordingly
                // After returning, reload the user's subscribed/rented books
                try {
                    Application.getInstance().getDataAdapter().unsubscribe(Integer.toString(book.getSubscriptionId()));
                    JOptionPane.showMessageDialog(null, "Book Unsubscribed Successfully");
                    loadMyBooks();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
//        buttonPanel.add(detailsButton);
        buttonPanel.add(returnButton);

        bookPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bookPanel;
    }

    public void actionPerformed(ActionEvent e) {
        // Handle any additional actions if needed
    }
}
