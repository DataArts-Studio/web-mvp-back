package com.data_arts_studio.web_mvp_back.test_case.adapter.out.persistence.converter;

import com.data_arts_studio.web_mvp_back.test_case.domain.ResultStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 테스트 케이스 결과 상태를 DB 코드값과 도메인 enum 사이에서 변환.
 *
 * 현재는 Supabase PostgreSQL 스키마가 소문자 상태값을 요구하므로 이 변환기를 사용
 * 추후 MySQL 등 다른 DB로 옮기면서 상태값 규칙이 바뀌면, DB별 차이는 이 클래스에서만 조정하거나 제거할 수 있음
 */
@Converter(autoApply = false)
public class ResultStatusConverter implements AttributeConverter<ResultStatus, String> {

    /**
     * 도메인 엔티티의 Enum 상태를 데이터베이스 컬럼값으로 변환합 (Java Enum -> DB String)
     * 
     * @param attribute 자바 도메인 레이어에서 사용하는 ResultStatus Enum 객체
     * @return DB에 저장될 실제 값(String). null인 경우 null을 반환하며, 그 외에는 Enum에 정의된 소문자 기반의 dbValue를 반환
     */
    @Override
    public String convertToDatabaseColumn(ResultStatus attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    /**
     * DB에서 조회한 컬럼값을 도메인 엔티티의 Enum으로 변환 (DB String -> Java Enum)
     * 
     * @param dbData 데이터베이스 테이블에 저장되어 있는 상태값
     * @return 도메인 레이어에서 사용할 ResultStatus Enum 객체     
     *         데이터가 없으면 null을, 데이터가 있으면 해당 코드값에 매핑되는 Enum 상수를 반환
     */
    @Override
    public ResultStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ResultStatus.fromDbValue(dbData);
    }
}
