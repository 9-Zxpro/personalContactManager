package com.jiba.pcm.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "social_links")
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;
    private String link;
    private String title;
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
