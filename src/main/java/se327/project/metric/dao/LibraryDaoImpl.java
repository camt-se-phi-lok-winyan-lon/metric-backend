package se327.project.metric.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se327.project.metric.entity.Library;
import se327.project.metric.entity.LibraryRequest;
import se327.project.metric.repository.LibraryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class LibraryDaoImpl implements LibraryDao {
    final LibraryRepository libraryRepository;

    @Override
    public Library getById(Long id) {
        return libraryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Library> getRanking(LibraryRequest libraryRequest) {
        List<Library> libraryList = new ArrayList<>();
        libraryRequest.getLibraries().forEach(
                library -> {
                    if(libraryRepository.existsById(library.getId())){
                        Library tempLib = this.getById(library.getId());
                        libraryList.add(tempLib);
                    }
                    return;
                }
        );


        libraryList.sort(Comparator.naturalOrder());
        Collections.reverse(libraryList);
        return libraryList;
    }
}
