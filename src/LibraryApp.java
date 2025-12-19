import java.util.ArrayList;
import java.util.Scanner;

/* =======================
   TASK 1 — Rectangle
   ======================= */
class Rectangle {

    private int id;
    private static int idGen = 1;
    private double width;
    private double height;

    public Rectangle() {
        this.id = idGen++;
    }

    public Rectangle(double width, double height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public int getId() {
        return id;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "Rectangle{id=" + id +
                ", width=" + width +
                ", height=" + height + "}";
    }
}

/* =======================
   TASK 2 — Book
   ======================= */
class Book {

    private int id;
    private static int idGen = 1;
    private String title;
    private String author;
    private int year;
    private boolean available;

    public Book() {
        this.id = idGen++;
        this.available = true;
    }

    public Book(String title, String author, int year) {
        this();
        setTitle(title);
        setAuthor(author);
        setYear(year);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1500 || year > 2025) {
            throw new IllegalArgumentException("Invalid year");
        }
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void markAsBorrowed() {
        available = false;
    }

    public void markAsReturned() {
        available = true;
    }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", available=" + available + "}";
    }
}

/* =======================
   TASK 3 — LibraryApp
   ======================= */
public class LibraryApp {

    private ArrayList<Book> books = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt();

            switch (choice) {
                case 1:
                    printAllBooks();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    searchByTitle();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nWelcome to Library App!");
        System.out.println("1. Print all books");
        System.out.println("2. Add new book");
        System.out.println("3. Search books by title");
        System.out.println("4. Borrow a book");
        System.out.println("5. Return a book");
        System.out.println("6. Delete a book by id");
        System.out.println("7. Quit");
        System.out.print("Choose an option: ");
    }

    private void printAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void addBook() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Author: ");
            String author = scanner.nextLine();

            System.out.print("Year: ");
            int year = readInt();

            books.add(new Book(title, author, year));
            System.out.println("Book added successfully");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByTitle() {
        System.out.print("Enter part of title: ");
        String query = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found");
        }
    }

    private void borrowBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findById(id);
        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (book.isAvailable()) {
            book.markAsBorrowed();
            System.out.println("Book borrowed");
        } else {
            System.out.println("Book is already borrowed");
        }
    }

    private void returnBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findById(id);
        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (!book.isAvailable()) {
            book.markAsReturned();
            System.out.println("Book returned");
        } else {
            System.out.println("Book was not borrowed");
        }
    }

    private void deleteBook() {
        System.out.print("Enter book id: ");
        int id = readInt();

        Book book = findById(id);
        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        books.remove(book);
        System.out.println("Book deleted");
    }

    private Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Please enter a number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static void main(String[] args) {
        new LibraryApp().run();
    }
}

