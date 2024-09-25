package org.pb.controller.dto.card;

import lombok.Data;
import org.pb.model.utils.Status;

@Data
public class CardDto {
    private Long id;
    private String type;
    private String bin;
    private Status status;
    private String operatorFullName;
}
