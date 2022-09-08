package com.vmodev.tuanva.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmodev.tuanva.notes.service.note.ImageNoteServiceImpl;
import com.vmodev.tuanva.notes.service.note.MultipleChoiceNoteServiceImpl;
import com.vmodev.tuanva.notes.service.note.NoteService;
import com.vmodev.tuanva.notes.service.note.PlainNoteServiceImpl;
import com.vmodev.tuanva.notes.service.util.Constants;

@Service
public class NoteFactory implements NoteAbstractFactory {
	@Autowired
	PlainNoteServiceImpl plainNoteServiceImpl;

	@Autowired
	MultipleChoiceNoteServiceImpl multipleChoiceNoteServiceImpl;

	@Autowired
	ImageNoteServiceImpl imageServiceImpl;

	@Override
	public NoteService get(String type) {
		switch (type) {
		case Constants.NoteType.PLAIN_NOTE:
			return plainNoteServiceImpl;
		case Constants.NoteType.MC_NOTE:
			return multipleChoiceNoteServiceImpl;
		case Constants.NoteType.IMAGE_NOTE:
			return imageServiceImpl;
		default:
			throw new IllegalArgumentException("Type not support");
		}
	}
}
