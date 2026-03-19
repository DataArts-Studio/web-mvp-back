package com.data_arts_studio.web_mvp_back.milestone.adapter.out.persistence.jpa.converter;

import com.data_arts_studio.web_mvp_back.milestone.domain.MilestoneProgressStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Supabase PostgreSQL 스키마의 progress_status 코드값과 도메인 enum 사이를 변환
 * 추후 DB가 바뀌더라도 상태 표현 차이는 이 converter 한 곳에서 조정
 */
@Converter(autoApply = false)
public class MilestoneProgressStatusConverter implements AttributeConverter<MilestoneProgressStatus, String> {

    @Override
    public String convertToDatabaseColumn(MilestoneProgressStatus attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public MilestoneProgressStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MilestoneProgressStatus.fromDbValue(dbData);
    }
}
