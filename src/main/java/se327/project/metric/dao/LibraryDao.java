package se327.project.metric.dao;

import se327.project.metric.entity.Library;
import se327.project.metric.entity.LibraryRequest;

import java.util.List;

public interface LibraryDao {

    Library getById(Long id);
    List<Library> getRanking(LibraryRequest libraryRequest) ;
}
