package com.emard.artisan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.artisan.web.rest.TestUtil;

public class TypeUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeUser.class);
        TypeUser typeUser1 = new TypeUser();
        typeUser1.setId(1L);
        TypeUser typeUser2 = new TypeUser();
        typeUser2.setId(typeUser1.getId());
        assertThat(typeUser1).isEqualTo(typeUser2);
        typeUser2.setId(2L);
        assertThat(typeUser1).isNotEqualTo(typeUser2);
        typeUser1.setId(null);
        assertThat(typeUser1).isNotEqualTo(typeUser2);
    }
}
