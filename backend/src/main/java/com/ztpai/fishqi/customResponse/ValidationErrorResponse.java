package com.ztpai.fishqi.customResponse;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ValidationErrorResponse {
    private List<String> errorMessages = new ArrayList<>();

    public ValidationErrorResponse() {
    }

    public void addErrorMessage(String message) {
        errorMessages.add(message);
    }
}
