package com.dotv.memories.service.impl;

import com.dotv.memories.config.PjUnitl;
import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.dotv.memories.repository.NotesRepository;
import com.dotv.memories.service.NotesService;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private Drive driveService;
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private PjUnitl pjUnitl;
    @Value("${id.folder.notes}")
    private String idFolderNotes;

    @Override
    @Transactional
    public Boolean saveNote(NotesDTO notesDTO) throws Exception {
        File file = driveService.files().get("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk").setFields("*").execute();
//        driveService.files().export("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk",file.getMimeType()).execute();
//        OutputStream outputStream = new ByteArrayOutputStream();
//        driveService.files().get("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk")
//                .executeMediaAndDownloadTo(outputStream);
//        driveService.files().export("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk",file.getMimeType()).execute();
//        driveService.files().delete("1cvZfEAGeODDf02q3_oLqnYgil9Tdqpoc").execute();

        Notes notes = new Notes();
            notes.setTitle(notesDTO.getTitle());
            notes.setContent(notesDTO.getContent());
            notes.setIdAcc(pjUnitl.getAcc().getId());
            notes.setCreatedDate(pjUnitl.getDateCurr());
            notes.setStatus(notesDTO.getStatus());
            //Save file lên gg drive
            if(!notesDTO.getFileImage()[0].isEmpty()) {
                File fileImg = pjUnitl.saveFile(idFolderNotes, notesDTO.getFileImage()[0]);
                notes.setImage(fileImg.getId());
                notes.setViewImage(fileImg.getWebViewLink());
            }
            notesRepository.save(notes);
        return true;
    }

    @Override
    public List<Notes> getNotesByAcc(Boolean status) throws Exception {
        return notesRepository.findAllByIdAccAndStatusOrderByCreatedDateDesc(pjUnitl.getAcc().getId(),status);
    }
}
