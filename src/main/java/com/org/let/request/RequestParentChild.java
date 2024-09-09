package com.org.let.request;

import com.org.let.response.DTOChild;

import java.util.List;

public class RequestParentChild {
    private String parentTableName;
    private Boolean parentTableEnabled; // Changed to Boolean for better readability
    private List<DTOChild> childEntries;
}
