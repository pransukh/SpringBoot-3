package com.org.let.response;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOParent {
    private Long parentTableId; // Changed to String for UUID
    private String parentTableName;
    private String parentTableValue;
    private Boolean parentTableEnabled; // Changed to Boolean for better readability
    private List<DTOChild> childEntries;
}


