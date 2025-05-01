package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
  }