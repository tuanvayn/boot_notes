package com.vmodev.tuanva.notes.service;

import com.vmodev.tuanva.notes.service.note.NoteService;

public interface NoteAbstractFactory {
	public NoteService get(String type);
}
