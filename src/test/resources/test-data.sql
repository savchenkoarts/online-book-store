INSERT INTO categories
(
    id,
    name,
    description,
    is_deleted
)
VALUES
    (
        3,
        'Testing',
        'Testing books',
        false
    );


INSERT INTO books
(
    id,
    title,
    author,
    isbn,
    price,
    description,
    cover_image,
    is_deleted
)
VALUES
    (
        5,
        'Effective Java',
        'Joshua Bloch',
        '9780134685991',
        40.00,
        'Java programming book',
        'cover.jpg',
        false
    );


INSERT INTO books_categories
(
    book_id,
    category_id
)
VALUES
    (
        5,
        3
    );
