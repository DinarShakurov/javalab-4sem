package ru.shakurov.jlmq.jlmq_server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jlmq_consumer")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ws_username")
    private String username;

    @Column(name = "ws_session_id")
    private String sessionId;

    @Enumerated(EnumType.STRING)
    private ConsumerState state;

    @OneToOne
    private Queue queue;
}
