package com.dotv.memories.service.impl;

import com.dotv.memories.config.PjUnitl;
import com.dotv.memories.dto.NotesAllDTO;
import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.dotv.memories.repository.NotesRepository;
import com.dotv.memories.service.NotesService;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private PjUnitl pjUnitl;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private Drive driveService;
    @Value("${id.folder.images}")
    private String idFolder;

    @Override
    @Transactional
    public Boolean saveNote(NotesDTO notesDTO) throws Exception {
            Notes notes = new Notes();
            if(notesDTO.getId()!=null){
                Optional<Notes> notesOld=notesRepository.findById(notesDTO.getId());
                notes.setCreatedDate(notesOld.get().getCreatedDate());
                notes.setId(notesDTO.getId());
                notes.setShowDate(pjUnitl.getDateCurr());
                notes.setStatusHide(notesOld.get().getStatusHide());
                if(notesDTO.getStatus()==false){
                    notes.setStatusHide(true);
                }
            }
            else{
                notes.setCreatedDate(pjUnitl.getDateCurr());
                if(notesDTO.getStatus()==false){
                    notes.setStatusHide(true);
                }
                else{
                    notes.setStatusHide(false);
                }
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
        if(notesRepository.findById(id).get().getStatusHide()==false) {
            Boolean statusHide = false;
            if (status == false)
                statusHide = true;
            notesRepository.updateStatus(id,status,pjUnitl.getDateCurr(),statusHide);
        }
        else{
            notesRepository.updateStatus(id,status,pjUnitl.getDateCurr(),true);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean showAllNote() throws Exception {
        notesRepository.showAllNote(pjUnitl.getAcc().getId(),pjUnitl.getDateCurr());
        return true;
    }

    @Override
    public List<NotesAllDTO> getAllNoteByType(int type) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select n.id id,n.title title,n.content content,");
        sqlBuilder.append("n.created_date createdDate,n.show_date showDate,a.full_name fullName ");
        sqlBuilder.append("from notes n inner join account a on n.id_acc=a.id  ");
        sqlBuilder.append("where n.status=true  ");
        if(type==1){
            sqlBuilder.append("order by n.created_date desc");
        }
        if(type==2){
            sqlBuilder.append("and n.id_acc!=:idAcc ");
            parameters.put("idAcc",pjUnitl.getAcc().getId());
            sqlBuilder.append("order by n.created_date desc");
        }
        if(type==3){
            sqlBuilder.append("and n.id_acc!=:idAcc and n.status_hide=true ");
            parameters.put("idAcc",pjUnitl.getAcc().getId());
            sqlBuilder.append("order by n.show_date desc");
        }
        if(type==4){
            sqlBuilder.append("and n.id_acc=:idAcc ");
            parameters.put("idAcc",pjUnitl.getAcc().getId());
            sqlBuilder.append("order by n.created_date desc");
        }
        if(type==5){
            sqlBuilder.append("and n.id_acc=:idAcc and n.status_hide=true ");
            parameters.put("idAcc",pjUnitl.getAcc().getId());
            sqlBuilder.append("order by n.show_date desc");
        }
        return namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, BeanPropertyRowMapper.newInstance(NotesAllDTO.class));
    }

    @Override
    public FileList getListFile() throws IOException {
        String pageToken = null;
//        List<File> list = new ArrayList<File>();
        FileList result=null;
        String query ="'"+idFolder+"' in parents and mimeType != 'application/vnd.google-apps.folder' and trashed = false";
        do {
            result = driveService.files().list().setQ(query).setSpaces("drive")
                    .setFields("nextPageToken, files(id,createdTime)")
                    .setOrderBy("createdTime desc")
                    .setPageToken(pageToken).execute();
//            for (File file : result.getFiles()) {
//                list.add(file);
//            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        return result;
    }

    @Override
    public Boolean deleteFile(String id) throws IOException {
//        File newContent = new File();
//        newContent.setTrashed(true);
//        driveService.files().update(id, newContent).execute();
//        driveService.files().trash(id).execute();
        driveService.files().delete(id).execute();
        return true;
    }

    @Override
    public Boolean uploadImage(MultipartFile[] files) throws IOException {
        if(files[0].isEmpty())
            return false;
        for(MultipartFile file:files){
            File newGGDriveFile = new File();
            List<String> parents = Arrays.asList(idFolder);
            newGGDriveFile.setParents(parents).setName(file.getOriginalFilename());
            AbstractInputStreamContent uploadStreamContent = new ByteArrayContent(file.getContentType(),file.getBytes());
            driveService.files().create(newGGDriveFile,uploadStreamContent).setFields("id").execute();
        }
        return true;

        //File file = driveService.files().get("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk").setFields("*").execute();
//        driveService.files().export("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk",file.getMimeType()).execute();
//        OutputStream outputStream = new ByteArrayOutputStream();
//        driveService.files().get("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk")
//                .executeMediaAndDownloadTo(outputStream);
//        driveService.files().export("1zJokmrQrUuOIDX2Cpor99hWGEByluYkk",file.getMimeType()).execute();
//        driveService.files().delete("1cvZfEAGeODDf02q3_oLqnYgil9Tdqpoc").execute();
    }
}
