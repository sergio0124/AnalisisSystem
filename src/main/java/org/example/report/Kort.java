package org.example.report;

import lombok.Data;

@Data
public class Kort {
    String id;
    String value;
    public Kort(String id, String value) {
        this.id = id;
        this.value = value;
    }
}