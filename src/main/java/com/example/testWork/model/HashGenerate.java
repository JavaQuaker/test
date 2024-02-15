package com.example.testWork.model;

import com.example.testWork.dto.HashCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Stack;

@Entity
@Table(name = "stack")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
public class HashGenerate implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    static Stack<HashCreateDTO> stack = new Stack<>();
    public static void add(HashCreateDTO dto) {
       stack.add(dto);
    }
}
