import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookDashboardScreen extends JFrame implements ActionListener {
    private JTextField txtSearchTerm = new JTextField(20);
    private JRadioButton radioTitle = new JRadioButton("Search by Title");
    private JRadioButton radioAuthor = new JRadioButton("Search by Author");
    private JButton btnSearch = new JButton("Search");

    // Sample books
    private List<Book> sampleBooks;

    public BookDashboardScreen() {
        this.setTitle("Book Dashboard");
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);

        // Sample data for books
        createSampleBooks();
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

    // Method to create sample books
    private void createSampleBooks() {
    	sampleBooks = new ArrayList<>();
        sampleBooks.add(new Book(1, "The Great Gatsby", "Classic novel about the American Dream", "F. Scott Fitzgerald", 10.99, 50, "Fiction", "src/images/book.png"));
        sampleBooks.add(new Book(2, "To Kill a Mockingbird", "Powerful story of racial injustice", "Harper Lee", 12.99, 40, "Fiction", "src/images/book.png"));
        sampleBooks.add(new Book(3, "1984", "Dystopian novel by George Orwell", "George Orwell", 15.99, 30, "Science Fiction", "src/images/book.png"));
        sampleBooks.add(new Book(4, "The Hobbit", "Fantasy adventure novel", "J.R.R. Tolkien", 14.99, 45, "Fantasy", "src/images/book.png"));
        sampleBooks.add(new Book(5, "Pride and Prejudice", "Romantic novel by Jane Austen", "Jane Austen", 11.99, 55, "Romance", "src/images/book.png"));
        sampleBooks.add(new Book(6, "The Catcher in the Rye", "Coming-of-age novel", "J.D. Salinger", 13.99, 25, "Fiction", "src/images/book.png"));
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
                new BookDashboardScreen().setVisible(true);
            }
        });
    }
}
