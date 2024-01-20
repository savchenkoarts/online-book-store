package mate.academy.onlinebookstore;

import java.math.BigDecimal;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    private final BookService bookService;

    @Autowired
    public OnlineBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setCoverImage("image");
            book.setIsbn("Ranok");
            book.setDescription("About nature");
            book.setTitle("From nature");
            book.setPrice(BigDecimal.valueOf(500));
            book.setAuthor("Stiven");

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
