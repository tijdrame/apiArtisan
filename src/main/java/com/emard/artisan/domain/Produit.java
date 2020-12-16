package com.emard.artisan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "produit_seq_gen")
    @SequenceGenerator(name = "produit_seq_gen", sequenceName = "produit_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "description")
    private String description;

    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JsonIgnoreProperties(value = "produits", allowSetters = true)
    private Artisan artisan;

    @OneToMany(mappedBy = "produit", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Produit libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public Produit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrix() {
        return prix;
    }

    public Produit prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Produit deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public Produit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public Produit genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Artisan getArtisan() {
        return artisan;
    }

    public Produit artisan(Artisan artisan) {
        this.artisan = artisan;
        return this;
    }

    public void setArtisan(Artisan artisan) {
        this.artisan = artisan;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Produit photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Produit addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setProduit(this);
        return this;
    }

    public Produit removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setProduit(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", prix=" + getPrix() +
            ", deleted='" + isDeleted() + "'" +
            ", description='" + getDescription() + "'" +
            ", genre='" + getGenre() + "'" +
            "}";
    }
}
