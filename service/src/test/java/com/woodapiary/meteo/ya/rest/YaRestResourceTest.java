/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.rest;

import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.domain.YaMessage;
import com.woodapiary.meteo.ya.model.YaFactDto;
import com.woodapiary.meteo.ya.model.YaMessageDto;
import com.woodapiary.meteo.ya.repo.YaFactRepository;
import com.woodapiary.meteo.ya.repo.YaMessageRepository;
import com.woodapiary.meteo.ya.service.YaDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = YaTestApplication.class)
class YaRestResourceTest {

    private final String messageUuid = "11111111-1111-1111-1111-111111111111";
    private final Integer geonameId = 1;
    private final String host = "localhost";
    private final String scheme = "http";
    private final String path = "/api/v1/ya";

    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private YaMessageRepository msgRepo;
    @Autowired
    private YaFactRepository factRepo;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private YaDao dao;

    @BeforeEach
    public void setUp(@Qualifier("message") YaMessage mes) {
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
        Integer geonameId = 1;
        dao.saveMessage(mes, geonameId);
        assertEquals(1, factRepo.count());
        assertEquals(1, msgRepo.count());
        assertThat(restTemplate).isNotNull();
        restTemplate.getRestTemplate().setErrorHandler(new YaRestTemplateResponseErrorHandler());
    }

    @Test
    public void testGetFacts() {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(randomServerPort)
                .path(path)
                .pathSegment("messages/facts")
                .queryParam("geonameId", geonameId)
                .buildAndExpand()
                .toUri();
        ResponseEntity<YaFactDto[]> response = restTemplate.getForEntity(uri, YaFactDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length == 1).isTrue();
    }

    @Test
    public void testGetLastMessage() {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(randomServerPort)
                .path(path)
                .pathSegment("messages/last")
                .queryParam("geonameId", geonameId)
                .buildAndExpand()
                .toUri();
        ResponseEntity<YaMessageDto> response = restTemplate.getForEntity(uri, YaMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetMessages() {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(randomServerPort)
                .path(path)
                .pathSegment("messages")
                .queryParam("geonameId", geonameId)
                .buildAndExpand()
                .toUri();
        ResponseEntity<YaMessageDto[]> response = restTemplate.getForEntity(uri, YaMessageDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length == 1).isTrue();
    }

    @Test
    public void testGetMessage() {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(randomServerPort)
                .path(path)
                .pathSegment("messages/{messageUuid}")
                .buildAndExpand(messageUuid)
                .toUri();
        ResponseEntity<YaMessageDto> response = restTemplate.getForEntity(uri, YaMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }


}
