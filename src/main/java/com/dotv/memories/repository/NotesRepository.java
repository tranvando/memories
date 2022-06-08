package com.dotv.memories.repository;

import com.dotv.memories.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    List<Notes> findAllByIdAccAndStatusOrderByCreatedDateDesc(int idAcc, Boolean status);
}
