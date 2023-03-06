package com.forcepoint.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class UrlCategories {

    @CsvBindByPosition(position=0)
    String name;

    @CsvBindByPosition(position=1)
    String urlPattern;

    @CsvBindByPosition(position=2)
    String description;
}
