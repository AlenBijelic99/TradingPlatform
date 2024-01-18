package ch.heigvd.application.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "trade")
public class Trade extends AbstractEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="price_id", nullable=false)
    private Price price;

    @Column(name = "quantity", nullable = false)
    private double quantity;
}
