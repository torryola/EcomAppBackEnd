package net.torrydev.ecombackend.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.swing.text.StyledEditorKit;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "sku")
    String sku;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "unit_price")
    BigDecimal unitPrice;
    @Column(name = "image_url")
    String imageUrl;
    @Column(name = "active")
    boolean active;
    @Column(name = "units_in_stock")
    int unitsInStock;
    @Column(name = "date_created")
    @CreationTimestamp
    Date dateCreated;
    @Column(name = "last_updated")
    @UpdateTimestamp
    Date lastUpdated;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    ProductCategory category;
}
