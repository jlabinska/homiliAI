package com.homiliai.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.FetchProfile.FetchOverride;

@Entity
@Table(name = "sermons")
@Getter
@Setter
@NoArgsConstructor
public class Sermon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_ID"))
  private User user;

  @ManyToOne
  @JoinColumn(name = "gospel_id", foreignKey = @ForeignKey(name = "FK_GOSPLE_ID"))
  private Gospel gospel;

  @ManyToOne
  @JoinColumn(name = "occasion_id", foreignKey = @ForeignKey(name = "FK_OCCASION_ID"))
  private Occasion occasion;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Audience audience;

  public enum Audience {
    CHILDREN,
    YOUTH,
    ADULTS,
    MARRIED
  }

  @Column(name = "google_docs_url", nullable = false, length = 255)
  private String googleDocsUrl;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

}
