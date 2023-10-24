package se327.project.metric.service;

import se327.project.metric.entity.Library;
import se327.project.metric.entity.LibraryRequest;

import java.util.List;

public interface LibraryService {
    List<Library> getRanking(LibraryRequest libraryRequest) ;
}
