package com.vmodev.tuanva.notes.service.note;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;
import com.vmodev.tuanva.notes.dto.NoteRequestDTO;
import com.vmodev.tuanva.notes.exception.CustomNotFoundException;
import com.vmodev.tuanva.notes.model.PlainNote;
import com.vmodev.tuanva.notes.repo.PlainNoteRepo;
import com.vmodev.tuanva.notes.service.user.SecurityContextService;

@Service
public class PlainNoteServiceImpl implements NoteService {

	@Autowired
	private PlainNoteRepo plainNoteRepo;

	@Override
	public boolean create(NoteRequestDTO request) throws Exception {
		PlainNote note = PlainNote.builder().title(request.getTitle()).type(request.getType())
				.createdBy(SecurityContextService.getUser()).createdAt(new Date()).desc(request.getDesc()).build();
		plainNoteRepo.save(note);
		return true;
	}

	@Override
	public boolean delete(Long id) {
		PlainNote note = plainNoteRepo.findById(id).orElseThrow(() -> new CustomNotFoundException());
		plainNoteRepo.delete(note);
		return true;
	}

	@Override
	public BaseResponseDTO<PlainNote> update(NoteRequestDTO request) {
		PlainNote note = plainNoteRepo.findById(request.getId()).orElseThrow(() -> new CustomNotFoundException());
		note = note.setTitle(request.getTitle()).setType(note.getType()).setCreatedBy(note.getCreatedBy())
				.setCreatedAt(note.getCreatedAt()).setUpdatedAt(new Date()).setDesc(request.getDesc());
		return new BaseResponseDTO<PlainNote>(plainNoteRepo.save(note));
	}

	@Override
	public BaseResponseDTO<PlainNote> get(Long id) {
		return new BaseResponseDTO<PlainNote>(
				plainNoteRepo.findById(id).orElseThrow(() -> new CustomNotFoundException()));
	}

	public BaseResponseDTO<List<PlainNote>> list() throws Exception {
		return new BaseResponseDTO<List<PlainNote>>(plainNoteRepo.findByCreatedBy(SecurityContextService.getUser()));
	}

}
