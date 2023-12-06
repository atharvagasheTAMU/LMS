package com.tamu.gui;
import javax.swing.*;

import com.tamu.entity.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class BookDashboardScreen extends JFrame implements ActionListener {
    private JTextField txtSearchTerm = new JTextField(20);
    private JRadioButton radioTitle = new JRadioButton("Search by Title");
    private JRadioButton radioAuthor = new JRadioButton("Search by Author");
    private JButton btnSearch = new JButton("Search");

    // Sample books
    private List<Book> sampleBooks;

    public BookDashboardScreen() throws UnknownHostException, IOException {
        this.setTitle("Book Dashboard");
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        
        sampleBooks = Application.getInstance().getDataAdapter().getBooks();
        // Sample data for books
        // Panel for search and buttons
        JPanel panelSearch = new JPanel();
        panelSearch.add(new JLabel("Search Term:"));
        panelSearch.add(txtSearchTerm);
        panelSearch.add(radioTitle);
        panelSearch.add(radioAuthor);
        panelSearch.add(btnSearch);
        btnSearch.addActionListener(this);
        ButtonGroup group = new ButtonGroup();
        group.add(radioTitle);
        group.add(radioAuthor);
        this.add(panelSearch, BorderLayout.NORTH);

        // Panel for displaying books in a 2x3 grid
        
        JPanel panelBooks = new JPanel(new GridLayout(2, 3, 10, 10));
        for (Book book : sampleBooks) {
            panelBooks.add(createBookPanel(book));
        }
        JScrollPane scrollPane = new JScrollPane(panelBooks);
        this.add(scrollPane, BorderLayout.CENTER);
    }


 // Method to create a panel for displaying book details
    private JPanel createBookPanel(Book book) {
        JPanel bookPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(book.getBookName());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(titleLabel, BorderLayout.NORTH);

        // Load the image using ImageIcon
        ImageIcon imageIcon = new ImageIcon(book.getImageURL());
        JLabel imageLabel = new JLabel(imageIcon);
        bookPanel.add(imageLabel, BorderLayout.CENTER);

        // You can add more labels or components to display other details like author, price, etc.

        JButton detailsButton = new JButton("View Details");
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookDetailsScreen bookDetailsScreen = new BookDetailsScreen(book);
                setVisible(false);
                bookDetailsScreen.setVisible(true);
            }
        });
        bookPanel.add(detailsButton, BorderLayout.SOUTH);

        return bookPanel;
    }



    public void actionPerformed(ActionEvent e) {
        // Handle the search action if needed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					new BookDashboardScreen().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
}
