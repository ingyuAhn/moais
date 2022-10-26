package com.assignment.core.repository;

import com.assignment.core.domain.entity.Member;
import com.assignment.core.domain.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findByMember(Member member, Pageable pageable);
}
