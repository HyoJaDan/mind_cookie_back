package hobbit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Picture {
    @Id @GeneratedValue
    private Long id;

    private String originalFileName;
    private String storedFileName;

    @JsonIgnore
    @OneToOne(mappedBy = "picture", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MealRecord mealRecord;

    public static Picture toPictureEntity(String originalFileName, String storedFileName){
        Picture picture = new Picture();
        picture.setOriginalFileName(originalFileName);
        picture.setStoredFileName(storedFileName);
        return picture;
    }
}
