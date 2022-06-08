package com.dotv.memories.service;

import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;

import java.util.List;
import java.util.Optional;

public interface NotesService {
    Boolean saveNote(NotesDTO notesDTO) throws Exception;
    List<Notes> getNotesByAcc(Boolean status) throws Exception;
}
