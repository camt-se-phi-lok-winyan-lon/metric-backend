package se327.project.metric.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @Column(unique = true)
    Long id;

    String name;
    String url;
    Double lint_score;
    String testPercentage;
    Integer documentScore;
    Double activeness;
}
