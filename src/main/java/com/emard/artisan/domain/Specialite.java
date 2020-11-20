package com.emard.artisan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "spec_seq_gen")
    @SequenceGenerator(name = "spec_seq_gen", sequenceName = "spec_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "having_genre")
    private Boolean havingGenre;

    @Column(name = "deleted")
    private Boolean deleted;

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

    public Specialite libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public Specialite code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isHavingGenre() {
        return havingGenre;
    }

    public Specialite havingGenre(Boolean havingGenre) {
        this.havingGenre = havingGenre;
        return this;
    }

    public void setHavingGenre(Boolean havingGenre) {
        this.havingGenre = havingGenre;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Specialite deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialite)) {
            return false;
        }
        return id != null && id.equals(((Specialite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", havingGenre='" + isHavingGenre() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
