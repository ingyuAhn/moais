package com.assignment.core.domain.entity;

import com.assignment.core.domain.common.BaseEntity;
import com.assignment.core.domain.entity.value.TodoStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TodoId;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private TodoStatus todoStatus;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @Builder
    public Todo(String content, TodoStatus todoStatus, Member member) {
        this.content = content;
        this.todoStatus = todoStatus;
        this.member = member;
    }

    public void todoStatusModify(TodoStatus todoStatus) {
        this.todoStatus = todoStatus;
    }
}
