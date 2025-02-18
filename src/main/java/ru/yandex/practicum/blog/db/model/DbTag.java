package ru.yandex.practicum.blog.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(schema = "blog", name = "tag")
public class DbTag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn
  private DbPost post;
  @Column
  private String tagName;
  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedAt;
  @Column
  private LocalDateTime deletedAt;
}
