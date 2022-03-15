package com.example.club_project.domain.converter;

import com.example.club_project.domain.JoinState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JoinStateConverter implements AttributeConverter<JoinState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(JoinState joinState) {
        return joinState.getCode();
    }

    @Override
    public JoinState convertToEntityAttribute(Integer joinStateCode) {
        return JoinState.from(joinStateCode);
    }
}
