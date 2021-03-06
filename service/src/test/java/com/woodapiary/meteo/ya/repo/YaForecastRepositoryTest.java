/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.repo;

import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.domain.YaForecast;
import com.woodapiary.meteo.ya.domain.YaMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaForecastRepositoryTest {

    @Autowired
    private YaForecastRepository repo;
    @Autowired
    private YaMessageRepository repoM;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
    }

    @Test
    public void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Qualifier("forecast_skinny") YaForecast fore) {
        mes = repoM.save(mes);
        mes.addForecast(fore);
        fore = repo.save(fore);
        assertEquals(1, repo.count());
        assertNotNull(fore.getForecastId());
        final YaForecast findById = repo.findById(fore.getForecastId()).orElseThrow();
        assertEquals(fore, findById);
    }

    @AfterEach
    public void tearDown() {
        repoM.deleteAll();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }


}
