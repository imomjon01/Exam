package uz.pdp.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")

    private Attachment attachment;

    @ManyToOne
    private User user;
    @OneToMany
    private List<Comment> comment;

    private String title;
}
