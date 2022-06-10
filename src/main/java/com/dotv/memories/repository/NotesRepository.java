package com.dotv.memories.repository;

import com.dotv.memories.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    List<Notes> findAllByIdAccAndStatusOrderByCreatedDateDesc(int idAcc, Boolean status);

    @Modifying
    @Query("update Notes n set n.status=?2, n.showDate=?3, n.statusHide=?4 where n.id=?1")
    int updateStatus(int id, Boolean status, Timestamp showDate,Boolean statusHide);

    @Modifying
    @Query("update Notes n set n.status=true, n.showDate=?2 where n.idAcc=?1 and n.status=false")
    int showAllNote(int idAcc,Timestamp showDate);


}
