package com.data_arts_studio.web_mvp_back.project.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
    @NotBlank String name,
    @NotBlank String password,
    @NotBlank String passwordConfirm,
    String description,
    String ownerName
) {
}
