package se327.project.metric.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se327.project.metric.dao.LibraryDao;
import se327.project.metric.entity.Library;
import se327.project.metric.entity.LibraryRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{
    final LibraryDao libraryDao;
    @Override
    public List<Library> getRanking(LibraryRequest libraryRequest) {
        return libraryDao.getRanking(libraryRequest);
    }
}
