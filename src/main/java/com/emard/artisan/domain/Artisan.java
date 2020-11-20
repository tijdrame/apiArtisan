package com.emard.artisan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Artisan.
 */
@Entity
@Table(name = "artisan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Artisan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "artisan_seq_gen")
    @SequenceGenerator(name = "artisan_seq_gen", sequenceName = "artisan_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    
    @Column(name = "login", unique = true)
    private String login;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "lang_key")
    private String langKey;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    
    @Column(name = "email", unique = true)
    private String email;

    
    @Column(name = "telephone", unique = true)
    private String telephone;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "artisans", allowSetters = true)
    private TypeUser typeUser;

    @ManyToOne
    @JsonIgnoreProperties(value = "artisans", allowSetters = true)
    private Specialite specialite;

    @ManyToOne
    @JsonIgnoreProperties(value = "artisans", allowSetters = true)
    private Localisation localisation;

    @ManyToOne
    @JsonIgnoreProperties(value = "artisans", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Artisan nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Artisan prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public Artisan login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Artisan password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLangKey() {
        return langKey;
    }

    public Artisan langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Artisan photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Artisan photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getEmail() {
        return email;
    }

    public Artisan email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Artisan telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Artisan deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public Artisan typeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
        return this;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Artisan specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public Artisan localisation(Localisation localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public User getUser() {
        return user;
    }

    public Artisan user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artisan)) {
            return false;
        }
        return id != null && id.equals(((Artisan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artisan{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
