package zeh.projects.Task_App.Products.models;

import jakarta.persistence.*;
import lombok.Data;
import zeh.projects.Task_App.users.models.User;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String description;

    @Column(unique = false, nullable = false)
    private Double price;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id", nullable = false)
//    private long category_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(unique = false, nullable = false)
    private boolean available;

    @Column(unique = false, nullable = false)
    private String image_url;

}
