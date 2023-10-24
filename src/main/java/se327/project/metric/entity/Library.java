package se327.project.metric.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Library implements Comparable<Library> {
    @Id
    @Column(unique = true)
    Long id;

    String name;
    String url;
    Double lintScore;
    String testPercentage;
    Integer documentScore;
    Double activeness;

    public String getTestPercentageGrade() {
        if (this.getTestCoverage() >= 0.8) {
            return "A";
        } else if (this.getTestCoverage() >= 0.5) {
            return "B";
        } else if (this.getTestCoverage() >= 0.3) {
            return "C";
        } else if (this.getTestCoverage() >= 0.1) {
            return "D";
        } else {
            return "F";
        }
    }

    public String getActivenessGrade() {
        if (this.getActiveness() >= 1 / 30f) {
            return "A";
        } else if (this.getActiveness() >= 1 / 60f) {
            return "B";
        } else if (this.getActiveness() >= 1 / 90f) {
            return "C";
        } else if (this.getActiveness() >= 1 / 120f) {
            return "D";
        } else {
            return "F";
        }
    }

    public Double getTestCoverage() {
        String result = null;
        if ((this.getTestPercentage() != null) && (this.getTestPercentage().length() > 0)) {
            result = this.getTestPercentage().substring(0, this.getTestPercentage().length() - 1);
        }
        return Double.parseDouble(result) / 100;
    }

    private Integer getGradeScore(String grade) {
        if (grade.equals("A")) return 4;
        if (grade.equals("B")) return 3;
        if (grade.equals("C")) return 2;
        if (grade.equals("D")) return 1;
        return 0;
    }

    public Double getTotalScore() {
        Double totalScore = 0.0;
        totalScore += this.lintScore;
        totalScore += this.documentScore;
        totalScore += this.getGradeScore(this.getActivenessGrade());
        totalScore += this.getGradeScore(this.getTestPercentageGrade());
        return totalScore;
    }

    @Override
    public int compareTo(Library lib) {
        if (this.getTotalScore() > lib.getTotalScore()) {
            return 1;
        } else if (this.getTotalScore() == lib.getTotalScore()) {
            return 0;
        } else {
            return -1;
        }
    }
}
