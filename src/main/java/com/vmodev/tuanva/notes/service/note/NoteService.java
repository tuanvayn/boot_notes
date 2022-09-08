package com.vmodev.tuanva.notes.service.note;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;
import com.vmodev.tuanva.notes.dto.NoteRequestDTO;
import com.vmodev.tuanva.notes.model.PlainNote;

public interface NoteService {
	boolean create(NoteRequestDTO request) throws Exception;

	boolean delete(Long id);

	BaseResponseDTO<PlainNote> update(NoteRequestDTO request);

	BaseResponseDTO<PlainNote> get(Long id);

}
