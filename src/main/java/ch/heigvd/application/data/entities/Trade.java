package ch.heigvd.application.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "trade")
public class Trade extends AbstractEntity {
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Price price;

    @Column(name = "quantity", nullable = false)
    private double quantity;
}
