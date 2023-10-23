package se327.project.metric.config;


import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se327.project.metric.entity.Library;
import se327.project.metric.repository.LibraryRepository;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final LibraryRepository libraryRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            List<String[]> list = new ArrayList<>();
            Path path = Paths.get(ClassLoader.getSystemResource("output.csv").toURI());
            Reader reader = Files.newBufferedReader(path);
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            }

//            Drop header
            list.remove(0);
            list.forEach( row -> {
                Library lib = Library.builder()
                        .id(Long.parseLong(row[0]))
                        .name(row[1])
                        .url(row[2])
                        .lint_score(Double.parseDouble(row[3]))
                        .testPercentage(row[4])
                        .documentScore(Integer.parseInt(row[5]))
                        .activeness(Double.parseDouble(row[6]))
                        .build();
                libraryRepository.save(lib);
            });


        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
