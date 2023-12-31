package se327.project.metric.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import se327.project.metric.entity.Library;
import se327.project.metric.entity.LibraryRequest;
import se327.project.metric.repository.LibraryRepository;
import se327.project.metric.service.LibraryService;

@Controller
@RequiredArgsConstructor
public class LibraryController {
    final LibraryRepository libRepository;
    final LibraryService libraryService;

    @GetMapping("/library")
    ResponseEntity<?> getLibrary(){
        return ResponseEntity.ok(libRepository.findAll());
    }

    @PostMapping("/ranking")
    ResponseEntity<?> getRanking(@RequestBody LibraryRequest libraryRequest) {
        return ResponseEntity.ok(libraryService.getRanking(libraryRequest));
    }


    @PostMapping("/csv")
    public ResponseEntity<?> addCSV(@RequestParam(value = "csv") MultipartFile file) throws IOException, ServletException {
        List<Library> newLib = new ArrayList<Library>();
        final String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            if (extension.equals("csv")) {
                List<String[]> list = new ArrayList<>();
                InputStream inputStream = file.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try (CSVReader csvReader = new CSVReader(reader)) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        list.add(line);
                    }
                }
                list.remove(0);
                list.forEach(row -> {
                    Library tempLib = Library.builder()
                        .id(Long.parseLong(row[0]))
                        .name(row[1])
                        .url(row[2])
                        .lintScore(Double.parseDouble(row[3]))
                        .testPercentage(row[4])
                        .documentScore(Integer.parseInt(row[5]))
                        .activeness(row[6].isEmpty()? 0 : Double.parseDouble(row[6]))
                        .build();
                    newLib.add(libRepository.save(tempLib));
                });
            } else {
                throw new ServletException("file must be a csv");
            }
        }
        return ResponseEntity.ok(newLib);
    }

    
}
