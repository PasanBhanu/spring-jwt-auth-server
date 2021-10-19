package com.softinklab.notification.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DefaultEmail extends Email {
    private String buttonText = null;
    private String buttonUrl = null;
    private List<String> preParagraphs = new ArrayList<>();
    private List<String> postParagraphs = new ArrayList<>();

    @Override
    public Map<String, Object> getModel() {
        Map<String, Object> map = new HashMap<>();
        map.put("greeting", this.greeting);
        map.put("sender", this.sender);
        map.put("buttonText", this.buttonText);
        map.put("buttonUrl", this.buttonUrl);
        map.put("preParagraphs", this.preParagraphs);
        map.put("postParagraphs", this.postParagraphs);
        map.put("year", this.year);
        map.put("applicationName", this.applicationName);

        return map;
    }
}
