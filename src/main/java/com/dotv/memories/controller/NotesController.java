package com.dotv.memories.controller;

import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.dotv.memories.service.NotesService;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class NotesController {
    @Autowired
    private Drive driveService;
    @Autowired
    private NotesService notesService;

//    @GetMapping("dn/notes")
//    public String notes1(){
//        return "notes/notes";
//    }
//    @GetMapping("dn/notes-detail")
//    public String notes2(){
//        return "notes/notes_detail";
//    }

    @GetMapping("dn/notes")
    public String getHtmlNotes(Model model,@RequestParam Boolean status) throws Exception {
        model.addAttribute("type",status);
        model.addAttribute("lstNotes",notesService.getNotesByAcc(status));
        return "notes/add_notes";
    }

    @GetMapping("dn/get-notes")
    public ResponseEntity<List<Notes>> getAllNotes(@RequestParam Boolean status) throws Exception {
        return ResponseEntity.ok(notesService.getNotesByAcc(status));
    }

    @PostMapping("dn/add-notes")
    public ResponseEntity<JSONObject> addNote(NotesDTO notesDTO){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.saveNote(notesDTO));
        }
        catch (Exception e){
            result.put("message",Boolean.FALSE);
        }
        return ResponseEntity.ok(result);
    }


    @GetMapping("dn/add1")
    public String addNote1() throws IOException {
        String pageToken = null;
        List<File> list = new ArrayList<File>();
        String fileNameLike="portfolio_item_6";

        String query = "name contains '" + fileNameLike + "' " //
                + " and mimeType != 'application/vnd.google-apps.folder' ";

        do {
            FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
                    // Fields will be assigned values: id, name, createdTime, mimeType
                    .setFields("nextPageToken, files(id, name, createdTime, mimeType)")//
                    .setPageToken(pageToken).execute();
            for (File file : result.getFiles()) {
                list.add(file);
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        return "notes/notes";
    }

}
