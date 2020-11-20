package com.emard.artisan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.artisan.web.rest.TestUtil;

public class EtatCommandeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatCommande.class);
        EtatCommande etatCommande1 = new EtatCommande();
        etatCommande1.setId(1L);
        EtatCommande etatCommande2 = new EtatCommande();
        etatCommande2.setId(etatCommande1.getId());
        assertThat(etatCommande1).isEqualTo(etatCommande2);
        etatCommande2.setId(2L);
        assertThat(etatCommande1).isNotEqualTo(etatCommande2);
        etatCommande1.setId(null);
        assertThat(etatCommande1).isNotEqualTo(etatCommande2);
    }
}
