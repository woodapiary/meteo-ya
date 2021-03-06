/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ya_message")
@EntityListeners(AuditingEntityListener.class)
public class YaMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Long messageId;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createdOn;

    @NotNull
    @Column(nullable = false, updatable = false, unique = true, columnDefinition = "binary(16)")
    private UUID messageUuid;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Integer geonameId;

    /**
     * now
     * Время сервера в формате unixtime.
     * Число
     */
    @NotNull
    private OffsetDateTime now;

    /**
     * now_dt
     * Время сервера в UTC.
     * Строка
     */
    @NotNull
    private OffsetDateTime nowDt;

    @Setter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "message", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private YaFact fact;

    @Setter(value = AccessLevel.NONE)
    @OneToOne(mappedBy = "message", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private YaForecast forecast;

    public void addFact(final YaFact fact) {
        this.fact = fact;
        fact.setMessage(this);
    }

    public void addForecast(final YaForecast forecast) {
        this.forecast = forecast;
        forecast.setMessage(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YaMessage)) {
            return false;
        }
        YaMessage other = (YaMessage) o;
        return messageId != null &&
                messageId.equals(other.getMessageId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
