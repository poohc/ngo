package com.dev.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;

@Controller
@RequestMapping("ngo")
public class MainController {
    
    @RequestMapping(value = "main")    
    public ModelAndView main(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("main/main");
        
        String developerToken = "S=s408:U=421754d:E=1574f838823:C=14ff7d25990:P=1cd:A=en-devtoken:V=2:H=5ac7ecb17d1851d8c95ead0a49ec9ac1";
		
		EvernoteAuth everNoteAuth = new EvernoteAuth(EvernoteService.PRODUCTION, developerToken);
		ClientFactory factory = new ClientFactory(everNoteAuth);
		
		List<Map<String, String>> noteContentsList = new ArrayList<Map<String, String>>();
		
		try {
			
			NoteStoreClient noteStore = factory.createNoteStoreClient();
			List<Notebook> noteBooks = noteStore.listNotebooks();	
			
			for (Notebook notebook : noteBooks){
				
				NoteFilter filter = new NoteFilter();
    	        filter.setNotebookGuid(notebook.getGuid());
		        filter.setOrder(NoteSortOrder.CREATED.getValue());
		        filter.setAscending(true);

		        NoteList noteList = noteStore.findNotes(filter, 0, 100);
		        List<Note> notes = noteList.getNotes();
		        
		        for (Note note : notes) {
		        	Map<String, String> noteMap = new HashMap<String, String>();
		        	Note fullNote = noteStore.getNote(note.getGuid(), true, true, false, false);
		            noteMap.put("title", fullNote.getTitle());
		            noteMap.put("contents", fullNote.getContent());
		            noteContentsList.add(noteMap);
		        }
				
			}			
			
			mav.addObject("noteContentsList", noteContentsList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
        return mav;
    }
}
