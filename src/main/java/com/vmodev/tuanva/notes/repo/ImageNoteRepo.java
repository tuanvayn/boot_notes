package com.vmodev.tuanva.notes.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmodev.tuanva.notes.model.ImageNote;

@Repository
public interface ImageNoteRepo extends JpaRepository<ImageNote, Long> {
	void deleteByIdIn(List<Long> ids);
}
