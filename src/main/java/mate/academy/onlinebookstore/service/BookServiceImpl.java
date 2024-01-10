package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.model.Book;

public class BookServiceImpl implements BookService {
    @Override
    public Book save(Book book) {
        return new Book();
    }

    @Override
    public List findAll() {
        return null;
    }
}
