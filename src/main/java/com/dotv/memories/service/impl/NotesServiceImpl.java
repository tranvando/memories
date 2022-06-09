package com.dotv.memories.service.impl;

import com.dotv.memories.config.PjUnitl;
import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.dotv.memories.repository.NotesRepository;
import com.dotv.memories.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private PjUnitl pjUnitl;

    @Override
    @Transactional
    public Boolean saveNote(NotesDTO notesDTO) throws Exception {
            Notes notes = new Notes();
            if(notesDTO.getId()!=null){
                Optional<Notes> notesOld=notesRepository.findById(notesDTO.getId());
                notes.setCreatedDate(notesOld.get().getCreatedDate());
                notes.setId(notesDTO.getId());
            }
            else{
                notes.setCreatedDate(pjUnitl.getDateCurr());
            }
            if(!notesDTO.getStatus()){
                notes.setStatusHide(true);
            }
            else{
                notes.setStatusHide(false);
            }
            notes.setTitle(notesDTO.getTitle());
            notes.setContent(notesDTO.getContent());
            notes.setIdAcc(pjUnitl.getAcc().getId());
            notes.setStatus(notesDTO.getStatus());
            notesRepository.save(notes);
        return true;
    }

    @Override
    public List<Notes> getNotesByAcc(Boolean status) throws Exception {
        return notesRepository.findAllByIdAccAndStatusOrderByCreatedDateDesc(pjUnitl.getAcc().getId(),status);
    }

    @Override
    public Boolean deleteNote(int id) {
        notesRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateStatus(int id, Boolean status) {
        notesRepository.updateStatus(id,status);
        return true;
    }

    @Override
    @Transactional
    public Boolean showAllNote() throws Exception {
        notesRepository.showAllNote(pjUnitl.getAcc().getId());
        return true;
    }
}
