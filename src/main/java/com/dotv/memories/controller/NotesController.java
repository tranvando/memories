package com.dotv.memories.controller;

import com.dotv.memories.dto.ImageDTO;
import com.dotv.memories.dto.NotesAllDTO;
import com.dotv.memories.dto.NotesDTO;
import com.dotv.memories.entity.Notes;
import com.dotv.memories.service.NotesService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping("dn/home")
    public String home(){
        return "notes/notes";
    }

    @GetMapping("dn/get-all-notes")
    public ResponseEntity<List<NotesAllDTO>> getAllNoteFilter(@RequestParam int type) throws Exception {
        return ResponseEntity.ok(notesService.getAllNoteByType(type));
    }

    @GetMapping("dn/notes")
    public String getHtmlNotes(Model model,@RequestParam Boolean status) throws Exception {
        List<Notes> lst=notesService.getNotesByAcc(status);
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("type",status);
        model.addAttribute("size",lst.size());
        model.addAttribute("lstNotes",lst);
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

    @PostMapping("dn/delete-notes")
    public ResponseEntity<JSONObject> deleteNote(@RequestParam int id){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.deleteNote(id));
        }
        catch (Exception e){
            result.put("message",Boolean.FALSE);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("dn/update-notes")
    public ResponseEntity<JSONObject> updateNote(@RequestParam int id,@RequestParam Boolean status){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.updateStatus(id,status));
        }
        catch (Exception e){
            result.put("message",Boolean.FALSE);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("dn/show-notes")
    public ResponseEntity<JSONObject> showAllNote(){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.showAllNote());
        }
        catch (Exception e){
            result.put("message",Boolean.FALSE);
        }
        return ResponseEntity.ok(result);
    }

    //upload
    @GetMapping("dn/image")
    public String image(Model model) throws IOException {
        model.addAttribute("lstImage",notesService.getListFile().getFiles());
        return "upload/images";
    }

    @GetMapping("dn/upload")
    public String upload(){
        return "upload/upload";
    }

    @PostMapping("dn/upload-image")
    public ResponseEntity<JSONObject> uploadImage(ImageDTO imageDTO){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.uploadImage(imageDTO.getFileImage()));
        }
        catch (Exception e){
            result.put("message",1);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("dn/delete-image")
    public ResponseEntity<JSONObject> deleteImage(String id){
        JSONObject result = new JSONObject();
        try{
            result.put("message",notesService.deleteFile(id));
        }
        catch (Exception e){
            result.put("message",Boolean.FALSE);
        }
        return ResponseEntity.ok(result);
    }

}
