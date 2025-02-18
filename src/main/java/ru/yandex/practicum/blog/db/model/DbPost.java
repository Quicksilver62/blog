package ru.yandex.practicum.blog.db.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(schema = "blog", name = "post")
public class DbPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private String title;
  @Column
  private String body;
  @Column
  private String preview;
  @Column
  private UUID picture;
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  @JoinColumn
  List<DbComment> comments;
  @Column
  Integer likesCount;
  @OneToMany(mappedBy = "post")
  @JoinColumn
  List<DbTag> tags;
  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedAt;
  @Column
  private LocalDateTime deletedAt;

}
