package com.vmodev.tuanva.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;
import com.vmodev.tuanva.notes.dto.NoteRequestDTO;
import com.vmodev.tuanva.notes.dto.group.NoteCreateGroup;
import com.vmodev.tuanva.notes.dto.group.NoteUpdateGroup;
import com.vmodev.tuanva.notes.model.PlainNote;
import com.vmodev.tuanva.notes.service.NoteAbstractFactory;
import com.vmodev.tuanva.notes.service.note.PlainNoteServiceImpl;

@RestController
@RequestMapping(value = "/api/v1/notes")
public class NoteController {

	@Autowired
	private NoteAbstractFactory noteAbstractFactory;

	@Autowired
	private PlainNoteServiceImpl plainNoteServiceImpl;

	@GetMapping("/")
	public ResponseEntity<BaseResponseDTO<List<PlainNote>>> getList() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(plainNoteServiceImpl.list());
	}

	@PostMapping("/")
	public ResponseEntity<Boolean> create(@Validated(NoteCreateGroup.class) @RequestBody NoteRequestDTO request)
			throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(noteAbstractFactory.get(request.getType()).create(request));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BaseResponseDTO<PlainNote>> view(@PathVariable("id") Long id) throws Exception {
		BaseResponseDTO<PlainNote> plainNoteRes = plainNoteServiceImpl.get(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(noteAbstractFactory.get(plainNoteRes.getData().getType()).get(id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) throws Exception {
		BaseResponseDTO<PlainNote> plainNoteRes = plainNoteServiceImpl.get(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(noteAbstractFactory.get(plainNoteRes.getData().getType()).delete(id));
	}

	@PutMapping("/")
	public ResponseEntity<BaseResponseDTO<PlainNote>> update(
			@Validated(NoteUpdateGroup.class) @RequestBody NoteRequestDTO request) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(noteAbstractFactory.get(request.getType()).update(request));
	}

	@GetMapping("/")
	public ResponseEntity<BaseResponseDTO<Integer>> countIncompleted() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(plainNoteServiceImpl.countIncompleted());
	}

}
