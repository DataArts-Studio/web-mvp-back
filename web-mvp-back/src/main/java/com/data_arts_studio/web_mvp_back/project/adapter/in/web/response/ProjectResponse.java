package com.data_arts_studio.web_mvp_back.project.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public record ProjectResponse(
    String id,
    String name,
    String description,
    String ownerName,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    OffsetDateTime createdAt
) {
    
}
