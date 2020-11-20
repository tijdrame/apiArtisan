package com.emard.artisan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.emard.artisan.web.rest.TestUtil;

public class ArtisanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artisan.class);
        Artisan artisan1 = new Artisan();
        artisan1.setId(1L);
        Artisan artisan2 = new Artisan();
        artisan2.setId(artisan1.getId());
        assertThat(artisan1).isEqualTo(artisan2);
        artisan2.setId(2L);
        assertThat(artisan1).isNotEqualTo(artisan2);
        artisan1.setId(null);
        assertThat(artisan1).isNotEqualTo(artisan2);
    }
}
