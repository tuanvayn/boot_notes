package com.vmodev.tuanva.notes.service.note;

import com.vmodev.tuanva.notes.dto.BaseResponseDTO;
import com.vmodev.tuanva.notes.dto.NoteRequestDTO;
import com.vmodev.tuanva.notes.model.PlainNote;

public interface NoteService {

	/**
	 * Creates the Note.
	 *
	 * @param request the request
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean create(NoteRequestDTO request) throws Exception;

	/**
	 * Delete note by it's id.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean delete(Long id);

	/**
	 * Update note.
	 *
	 * @param request the request
	 * @return the base response DTO
	 */
	BaseResponseDTO<PlainNote> update(NoteRequestDTO request);

	/**
	 * Gets the note by id.
	 *
	 * @param id the id
	 * @return the base response DTO
	 */
	BaseResponseDTO<PlainNote> get(Long id);

}
