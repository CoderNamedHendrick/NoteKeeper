package com.example.coursenotekeeper;

import android.provider.ContactsContract;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    static DataManager mDataManager;

    @BeforeClass
    public static void classSetUp(){
        mDataManager = DataManager.getInstance();
    }

    @Before
    public void setup(){
        mDataManager.getNotes().clear();
        mDataManager.initializeExampleNotes();
    }

    @Test
    public void createNewNote() {

        final CourseInfo course = mDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";

        int noteIndex = mDataManager.createNewNote();
        NoteInfo newNote = mDataManager.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = mDataManager.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }

    @Test
    public void findSimilarNote(){

        final CourseInfo course = mDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText1 = "This is the body text of my test note";
        final String noteText2 = "This is the body of my second test note";

        int noteIndex1 = mDataManager.createNewNote();
        NoteInfo newNote1 = mDataManager.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = mDataManager.createNewNote();
        NoteInfo newNote2 = mDataManager.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = mDataManager.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = mDataManager.findNote(newNote2);
        assertEquals(noteIndex2, foundIndex2);
    }

    @Test
    public void createNewNoteOneStepCreation(){
        final CourseInfo course = mDataManager.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body of my test note";

        int noteIndex = mDataManager.createNewNote(course, noteTitle, noteText);

        NoteInfo compareNote = mDataManager.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }
}