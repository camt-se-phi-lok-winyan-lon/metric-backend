package se327.project.metric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se327.project.metric.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
