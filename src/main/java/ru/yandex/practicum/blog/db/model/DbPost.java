package ru.yandex.practicum.blog.db.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@EqualsAndHashCode
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
  private String picture;
  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DbComment> comments;
  @Column
  Integer likesCount;
  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DbTag> tags;
  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;
  @Column
  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
