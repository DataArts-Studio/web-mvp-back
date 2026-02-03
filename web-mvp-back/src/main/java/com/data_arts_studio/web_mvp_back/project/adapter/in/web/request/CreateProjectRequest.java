package com.data_arts_studio.web_mvp_back.project.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

// 프론트엔드에서 보낸 Json 데이터를 담을 객체 
public record CreateProjectRequest(
    @NotBlank String name,
    @NotBlank String identifier,
    @NotBlank String identifierConfirm,
    String description,
    String ownerName
) {
}
