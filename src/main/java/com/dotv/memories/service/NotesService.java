package com.dotv.memories.service;

import com.dotv.memories.dto.NotesAllDTO;
import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {
    Boolean saveNote(NotesDTO notesDTO) throws Exception;
    List<Notes> getNotesByAcc(Boolean status) throws Exception;
    Boolean deleteNote(int id);
    Boolean updateStatus(int id,Boolean status);
    Boolean showAllNote() throws Exception;
    List<NotesAllDTO> getAllNoteByType(int type) throws Exception;
    Boolean uploadImage(MultipartFile[] files);
    List<File> getListFile() throws IOException;
}
