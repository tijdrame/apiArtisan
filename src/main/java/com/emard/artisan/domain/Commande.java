package com.emard.artisan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cmde_seq_gen")
    @SequenceGenerator(name = "cmde_seq_gen", sequenceName = "cmde_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "date_commande")
    private Instant dateCommande;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private EtatCommande etatCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public Commande dateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Commande quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Commande deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Produit getProduit() {
        return produit;
    }

    public Commande produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Client getClient() {
        return client;
    }

    public Commande client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EtatCommande getEtatCommande() {
        return etatCommande;
    }

    public Commande etatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
        return this;
    }

    public void setEtatCommande(EtatCommande etatCommande) {
        this.etatCommande = etatCommande;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", quantite=" + getQuantite() +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
