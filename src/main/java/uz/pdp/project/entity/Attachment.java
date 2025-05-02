package uz.pdp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private String fileName;
    private String fileType;


    private byte[] content;
}

