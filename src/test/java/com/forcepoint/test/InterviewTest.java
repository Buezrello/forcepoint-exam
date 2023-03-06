package com.forcepoint.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forcepoint.dto.UrlCategories;
import com.forcepoint.model.RootUrlCategories;
import com.forcepoint.model.UrlCategory;
import com.forcepoint.model.UrlPattern;
import com.opencsv.bean.CsvToBeanBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Test
public class InterviewTest {

    private static Path path;
    private static List<UrlCategories> urlCategoriesList;

    @BeforeClass
    public void beforeAll() throws FileNotFoundException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("url_categories_2020_06_21.csv");
        path = Paths.get(Objects.requireNonNull(resource).toURI());
        urlCategoriesList = new CsvToBeanBuilder<UrlCategories>(new FileReader(path.toFile()))
                .withType(UrlCategories.class)
                .build()
                .parse();
    }

    public void test01_countNumberOfUrlRows() throws IOException {

        try (Stream<String> lines = Files.lines(path)) {
            System.out.println(lines.count());
        }
    }

    public void test02_countNumberOfDistinctUrlCategories() {
        Map<String, List<UrlCategories>> collect = urlCategoriesList.stream()
                .collect(Collectors.groupingBy(UrlCategories::getName));

        System.out.println("Number of Distinct Categories: " + collect.size());

        collect.forEach((k, v) -> System.out.println(k + " : " + v.size()));
    }

    public void test03_convertUrlCategoriesToJson() throws IOException {
        Map<String, List<String>> urlsByName = new HashMap<>();
        Map<String, List<UrlCategories>> collect = urlCategoriesList.stream()
                .collect(Collectors.groupingBy(UrlCategories::getName));

        for (Map.Entry<String, List<UrlCategories>> entry : collect.entrySet()) {
            urlsByName.put(entry.getKey(),
                    entry.getValue().stream().map(UrlCategories::getUrlPattern).collect(Collectors.toList()));
        }

        RootUrlCategories rootUrlCategories = createUrlCategories(urlsByName);

        new ObjectMapper().writeValue(new File("url_categories.json"), rootUrlCategories);

    }

    private RootUrlCategories createUrlCategories(Map<String, List<String>> urlsByName) {

        List<UrlCategory> urlCategories = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : urlsByName.entrySet()) {
            String urlCategoryId = UUID.randomUUID().toString();
            String name = entry.getKey();
            List<UrlPattern> urlPatterns = entry.getValue().stream()
                    .map(e -> new UrlPattern("EXACT", e)).collect(Collectors.toList());
            urlCategories.add(new UrlCategory(urlCategoryId, name, urlPatterns));
        }

        return new RootUrlCategories(urlCategories);
    }
}
