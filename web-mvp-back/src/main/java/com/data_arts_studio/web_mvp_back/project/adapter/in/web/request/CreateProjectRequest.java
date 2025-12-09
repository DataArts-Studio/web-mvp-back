package com.data_arts_studio.web_mvp_back.project.adapter.in.web.request;


public record CreateProjectRequest(
    String name,
    String password,
    String passwordConfirm,
    String description,
    String ownerName
) {
}
