package mate.academy.onlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE books SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String title;
    @Column(nullable = false)
    @NotBlank
    private String author;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String isbn;
    @Column(nullable = false)
    @NotNull
    private BigDecimal price;
    private String description;
    private String coverImage;
    @Column(nullable = false)
    private boolean deleted = false;
}
