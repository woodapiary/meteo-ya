/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class YaInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * lat
     * Широта (в градусах).
     * Число
     */
    private Double lat;
    /**
     * lon
     * Долгота (в градусах).
     * Число
     */
    private Double lon;
    /**
     * url
     * Страница населенного пункта на сайте Яндекс.Погода.
     * Строка
     */
    private String url;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
