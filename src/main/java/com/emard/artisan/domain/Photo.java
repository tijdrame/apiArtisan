package com.emard.artisan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "photo_seq_gen")
    @SequenceGenerator(name = "photo_seq_gen", sequenceName = "photo_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "deleted")
    private Boolean deleted;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "photos", allowSetters = true)
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Photo deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Photo photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Photo photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Produit getProduit() {
        return produit;
    }

    public Photo produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", deleted='" + isDeleted() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
