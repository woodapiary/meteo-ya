/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.domain.YaFact;
import com.woodapiary.meteo.ya.domain.YaForecast;
import com.woodapiary.meteo.ya.domain.YaMessage;
import com.woodapiary.meteo.ya.model.YaFactDto;
import com.woodapiary.meteo.ya.model.YaForecastDto;
import com.woodapiary.meteo.ya.model.YaMessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageDtoModelMapperTest {

    private static final String testDataFile = "ya_v1.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private YaMessageDtoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        assertNotNull(mapper);
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, YaMessageDto.class);
        }
    }

    @Test
    public void testMessageMapper() throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        dto.setFact(null);
        dto.setForecast(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }

    @Test
    public void testFactMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaFactDto dto1 = dto.getFact();
        final YaFact entity = mapper.factDtoToFact(dto1);
        System.out.println(entity);
        final YaFactDto dto2 = mapper.factDtoFromFact(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testForecastMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaForecastDto dto1 = dto.getForecast();
        final YaForecast entity = mapper.forecastDtoToForecast(dto1);
        final YaForecastDto dto2 = mapper.forecastDtoFromForecast(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testFullMessageMapper() throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }


}
