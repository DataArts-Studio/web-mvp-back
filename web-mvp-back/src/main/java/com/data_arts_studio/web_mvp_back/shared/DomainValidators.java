package com.data_arts_studio.web_mvp_back.shared;


// 도메인 모델 검증 유틸 클래스 
public final class DomainValidators {
    // 유틸 클래스이므로 인스턴스화 방지
    private DomainValidators() {} 

    /*
    * null 검증 메서드
    * para: obj = 검증 대상 객체, message = 예외 발생 시 메시지
    */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }

    /*
    * 빈 문자열 검증 메서드
    * para: str = 검증 대상 문자열, message = 예외 발생 시 메시지
    */
    public static String requireNonEmpty(String str, String message) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return str;
    }
    
     /*
     * sortOrder 규칙: NOT NULL, DEFAULT 0, 음수 불가
     */
    public static int normalizeSortOrder(Integer sortOrder) {
        if (sortOrder == null) return 0;
        if (sortOrder < 0) throw new IllegalArgumentException("sortOrder는 0 이상이어야 합니다.");
        return sortOrder;
    }
}
