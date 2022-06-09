package com.dotv.memories.controller;

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
import java.util.List;


@Controller
public class NotesController {
    @Autowired
    private NotesService notesService;

    @GetMapping("dn/notes1")
    public String notes1(){
        return "notes/notes";
    }
//    @GetMapping("dn/notes-detail")
//    public String notes2(){
//        return "notes/notes_detail";
//    }

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

}
