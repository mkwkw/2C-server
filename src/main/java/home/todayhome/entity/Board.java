package home.todayhome.entity;

import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
@Where(clause = "is_deleted is NULL")
public class Board {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "title", nullable = false, length = 63)
    private String title;

    @NotNull
    @Lob
    @Column(name = "board_contents", nullable = false)
    private String content;

    @NotNull
    @Column(name = "heart_count", nullable = false)
    private Integer heartCount = 0;

    @NotNull
    @Column(name = "visitor_count", nullable = false)
    private Integer visitorCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "moidified_at")
    private LocalDateTime moidifiedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}