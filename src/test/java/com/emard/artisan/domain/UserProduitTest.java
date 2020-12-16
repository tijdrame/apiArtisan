package com.emard.artisan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.artisan.web.rest.TestUtil;

public class UserProduitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProduit.class);
        UserProduit userProduit1 = new UserProduit();
        userProduit1.setId(1L);
        UserProduit userProduit2 = new UserProduit();
        userProduit2.setId(userProduit1.getId());
        assertThat(userProduit1).isEqualTo(userProduit2);
        userProduit2.setId(2L);
        assertThat(userProduit1).isNotEqualTo(userProduit2);
        userProduit1.setId(null);
        assertThat(userProduit1).isNotEqualTo(userProduit2);
    }
}
