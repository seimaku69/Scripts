// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2025-06-30
// (c) licensed under GPL-3.0 or later

/*
    This script imports a Markdown file (GFM) as childs of selected node.
    Every heading (#..) defines a new node - the text in paragraphs is written into each note.
    The actual selected node is used as root for import - all chapters in markdown are childs.
    
    Attention : 
    Markdown headers have to use '# ...' - underline style is not supported (Atx-style)!
    All other text is copied notes.
    Metadata is written to the node which imports the Markdown text.
    It is recommended to set the 'contentType' and 'coreTextFormat' to your own needs - default
    is standard text, because rendering of Markdown in Freeplane is not the best and
    you can see reference links only in the editor.
*/

/*
*/ 

import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter
import org.freeplane.api.Node

final String lf = System.lineSeparator()

// Indicator for being a header-line - they start with '#' (Atx-style)
final String hIndicator = "#"

// content types for Markdown
//String contentType = "markdown"
//String coreTextFormat = "markdownPatternFormat"
// content types for standard text
String contentType = "auto"
String coreTextFormat = "STANDARD_TEXT"

// global variable rootNodeLevel - level of the user-selected node - import-root
final int rootNodeLevel = node.getNodeLevel(true)
// Buffer for normal text in notes
StringBuilder outBuff = new StringBuilder()
// Buffer for all reference links
StringBuilder refBuff = new StringBuilder()

// actual focused Node (actualNode) in mindmap
def actualNode = node

/* 
    method  : getParentNode(current Node, level of new header) - recursive
    task    : This function finds the parent node for given header - recursive. 
    input   : current Node, level of heading in actual MD-line, rootLevel
    return  : parent in mindmap for this heading
    remark  : The parent header is earlier in the Markdown-file - the correct parent 
                must already exist in the mindmap ! The correct parent is the first in 
                parent-hierarchy with a lower level.
                ..an equal level is a sibling.
*/
def Node getParentNode(Node currNode, int headLevel, int rootLevel) {
    //ui.informationMessage(currNode.toString() + " , " + currNode.getNodeLevel().toString() + " , " + headLevel.toString())
    if (currNode.getNodeLevel(true) - rootLevel < headLevel) {
        return currNode
    } else {
        return getParentNode(currNode.getParent(), headLevel, rootLevel)
    }
}

// choose a Markdown file
JFileChooser fChooser = ui.newFileChooser()
fChooser.setDialogTitle("Import Markdown into selected node")
FileNameExtensionFilter filter = new FileNameExtensionFilter("Markdown files", "md")
fChooser.setFileFilter(filter);  
int retVal = fChooser.showOpenDialog()
if (retVal != JFileChooser.APPROVE_OPTION) return

File selFile = fChooser.getSelectedFile()
// adding some information about markdown import to import-root
node["Import_File"] = new URI(selFile.getAbsolutePath())
node["Import_Date"] = format(new Date(), "yyyy-MM-dd : HH:mm")

// iterate the lines of markdown file
selFile.eachLine { line,nb ->   // no continue possible !
    if (line.startsWith(hIndicator)) {
        // header begins like '### ' - first space indicates header level (=3)
        hLevel = line.indexOf("# ") + 1
        // hLevel == 0 iff line is not a header - something like '#todo'
        if (hLevel == 0) {
            if (!line.isEmpty()) outBuff << line << "  " << lf
        } else {            // find parent node of new header and add a child
            parentNode = getParentNode(actualNode, hLevel, rootNodeLevel)
            newChild = parentNode.createChild()
            newChild.setNoteContentType(contentType)
            newChild.setDetailsContentType(contentType)
            newChild.setFormat(coreTextFormat)
            // truncate the '###s' - title begins after hlevel
            newChild.text = line.substring(hLevel + 1)
            // write and delete outBuff
            if (outBuff.length() > 0) {
                actualNode.setNote(outBuff.toString())
                outBuff.delete(0, outBuff.length())
            }
            // newChild is the new starting point
            actualNode = newChild
        }
    } else {
        // reference links and paragraph text
        if (line.matches("\\[.+\\]\\: .+")) {
            refBuff << line << "  " << lf
        } else {
            if (!line.isEmpty()) outBuff << line << "  " << lf
        }
    }
}
// write and delete outBuff for last node
if (outBuff.length() > 0) {
    actualNode.setNote(outBuff.toString())
    outBuff.delete(0, outBuff.length())
}
// write references in separate node
if (refBuff.length() > 0) {
    parentNode = getParentNode(actualNode, 2, rootNodeLevel)
    refChild = parentNode.createChild("Reference-Links")
    refChild.setNoteContentType(contentType)
    refChild.setFormat(coreTextFormat)
    refChild.setNote(refBuff.toString())
}

// append hierarchy of nodes as table in note of import root
outBuff << lf << "  " << lf
outBuff << "**Imported node hirarchy :**  " + lf + "  " + lf
outBuff << "| Node text | Node ID | Level |  " + lf
outBuff << "| ----- | ----- | -----: |  " + lf
node.findAll().each{
    outBuff << "| " << it.text.replaceAll('#','') << " | "
    outBuff << it.Id << " | " << it.getNodeLevel(true) << " |  " + lf
}
noteTxt = node.getNote() ?: ""
node.setNote(noteText + "  " + lf + outBuff.toString())
//node.note = noteTxt + "  " + lf + outBuff.toString()