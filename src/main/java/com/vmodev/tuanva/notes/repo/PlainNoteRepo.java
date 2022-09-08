package com.vmodev.tuanva.notes.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmodev.tuanva.notes.model.PlainNote;

@Repository
public interface PlainNoteRepo extends JpaRepository<PlainNote, Long> {
	List<PlainNote> findByCreatedBy(String username);
}
