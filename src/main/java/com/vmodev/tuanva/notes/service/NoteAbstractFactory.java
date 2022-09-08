package com.vmodev.tuanva.notes.service;

import com.vmodev.tuanva.notes.service.note.NoteService;

public interface NoteAbstractFactory {

	/**
	 * Gets the @NoteService by type.
	 *
	 * @param type the type
	 * @return the note service
	 */
	public NoteService get(String type);
}
