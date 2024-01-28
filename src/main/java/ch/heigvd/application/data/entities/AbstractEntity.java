package ch.heigvd.application.data.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * This class is used to represent the abstract entity.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    /**
     * The id of the entity (primary key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;
    /**
     * The version of the entity.
     */
    @Column(name = "version")
    @Version
    private int version;

    /**
     * Get the id of the entity.
     *
     * @return The id of the entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the entity.
     *
     * @param id The id of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the version of the entity.
     *
     * @return The version of the entity
     */
    public int getVersion() {
        return version;
    }

    /**
     * Hashcode method for the entity.
     *
     * @return The hashcode of the entity
     */
    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    /**
     * Equals method for the entity.
     *
     * @param obj The object to compare
     * @return True if the object is equal to the entity, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity that)) {
            return false; // null or not an AbstractEntity class
        }
        if (getId() != null) {
            return getId().equals(that.getId());
        }
        return super.equals(that);
    }
}
