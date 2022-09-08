package com.vmodev.tuanva.notes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmodev.tuanva.notes.model.MultipleChoiceNote;

@Repository
public interface MultipleChoiceNoteRepo extends JpaRepository<MultipleChoiceNote, Long> {

}
