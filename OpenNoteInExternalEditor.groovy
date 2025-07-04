// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-06-25
// (c) licensed under GPL-3.0 or later

/*
This script opens the note of a selected node in an external editor with a new generated 
temporary file. After closing this editor, the new content is written to node and
the minmap will be saved.
If you don't save the temporary file in your editor there are no changes in your note.

    Attention :
    1. Don't use an existing instance of any editor to edit the temporary file. 
        This script waits for the termination of the process - otherwise it fails..
    2. It is recommended to set the value of 'editor' to your own preferences.
    3. The external editor is opened in modal-mode - means Freeplane isn't available 
        before closing it.
    
    Tip : You can assign a shortcut to this script in Freeplane to make access easier !
*/

/* 
*/

import java.io.File

// final String editor = "xed --new-window"
final String editor = "/usr/bin/ghostwriter"    // Markdown editor with preview
// final String editor = "/usr/bin/notepadqq"

// check content type of note to set extension 
// extensions determines often syntax highlighting in an editor
switch (node.getNoteContentType()) {
    case "markdown":    // ContentType : Markdown
        ext = ".md"
        break
    case "html":        // ContentType : Text / HTML
        ext = ".html"
        break
    case "latex":       // ContentType : Latex
        ext = ".latex"
        break
    default:            // ContentType : Standard (auto)
        ext = ".txt"
}

//String tmpPath = c.getUserDirectory().getAbsolutePath() + File.separator + "~" + node.id + ext

//create temporary file
try {
    //tmpFile = new File(tmpPath)
    tmpFile = File.createTempFile('~' + node.id + ":", ext)
    String text = node.note ?: ""
    tmpFile.setText(text, 'UTF-8')
} catch (IOException e) {
    ui.errorMessage("Can not create temporary file !")
    return
}

String exCmd = editor + " " + tmpFile.getAbsolutePath()
Process process = exCmd.execute()
process.waitFor()

// read new content and save
node.note = tmpFile.getText('UTF-8')
node.getMindMap().save(true)
// delete temporary file
tmpFile.delete()



