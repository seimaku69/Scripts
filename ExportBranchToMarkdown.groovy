// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-07-11
// (c) licensed under GPL-3.0 or later

/*
    This script exports a selected node and all nodes in this branch to Markdown.
    Text elements except headings are exported as they are..
    ..so be sure to work with Markdown in your notes.
    To export the whole mindmap just select the root.
    You can choose to create a TOC in your export.

    Be sure to permit operations (file/write) for Scripting-Plugin in Preferences !
*/

/* 
    #todo 04 : Bereitstellung einer Auswahl, welche Elemente exportiert werden sollen..
        (s. MergeSelectedNodes)
*/

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.JOptionPane


lf = System.lineSeparator()
// Indicator for being a header-line - they start with "#"
final String hIndicator = "#"

// creating a TOC in Markdown file
boolean createTOC = false

def sb = new StringBuffer()
def sbTOC = new StringBuffer()

/*
    method  : getPrecStr
    task    : create sequence of preceding characters
    input   : counter of needed chars, character for sequence
    return  : String with a sequence of charStr - e.g. for headers or TOC
*/
def String getPrecStr(int relLevel, String charStr) {
	String prec = ""
	for (i=0; i < relLevel; i++) {
		prec += charStr
	}
	return prec
}

/*
    method  : addMetadata
    task    : write metadata to given StringBuffer
    input   : StringBuffer to write to
*/
def addMetadata(StringBuffer strBuff) {
    strBuff << "<!-- Begin Metadata  " << lf
    strBuff << "  " << lf
    strBuff << "[Freeplane file]:- '" << node.getMindMap().getFile().getPath() << "'  " + lf
    strBuff << "[Export date]:- '" << format(new Date(), "yyyy-MM-dd HH:mm:ss") << "'  " + lf
    strBuff << "[Export script]:- 'ExportBranchToMarkdown.groovy'" << "  " + lf
    strBuff << "[Script author]:- 'Markus Seilnacht; seimaku(at)proton(dot)me'" << "  " + lf
    strBuff << "  " << lf
    strBuff << "End Metadata -->  " + lf
}

/*
    method  : getMDExportFile
    task    : asking for a file and overwrite
    return  : File to save Markdown export or null (aborted)
*/
def File getMdExportFile() {
    JFileChooser fChooser = ui.newFileChooser()
    fChooser.setDialogTitle("Export selected node as Markdown to file..")
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Markdown files", "md")
    fChooser.setFileFilter(filter);
    fName = node.text.strip().replace(" ","") + ".md"
    fChooser.setSelectedFile(new File(fName))

    File file = null
    while (!file) {
        int retVal = fChooser.showSaveDialog(ui.getFrame())
        if (retVal != JFileChooser.APPROVE_OPTION) return null
        file = fChooser.getSelectedFile()
        if (file.exists()) {
            int over = ui.showConfirmDialog(null, "Overwrite existing file with new export ?",
                "Markdown Export", JOptionPane.YES_NO_OPTION)
            if (over != JOptionPane.YES_OPTION) file = null
        }
    }
    return file
}



// get a file for Markdown export
file = getMdExportFile()
if(!file) {
    c.setStatusInfo("standard", "Markdown export aborted ! ", "messagebox_warning")
    return
}

// asking for creation of TOC in Markdown file
int toc = ui.showConfirmDialog(null, "Do you want to create a TOC (table of contents) ?",
"Markdown Export", JOptionPane.YES_NO_OPTION)
if (toc == JOptionPane.YES_OPTION) createTOC = true

// level of selected node for export - global
startNodeLevel = node.getNodeLevel(true)

// write TOC to it's own Stringbuffer
if (createTOC) {
    sbTOC << "  " << lf << getPrecStr(2, hIndicator) << " Table of Contents  "
    sbTOC << lf << "  " << lf
    for (n in node.findAll()) {
        nLevel = n.getNodeLevel(true)
        stripText = n.getTransformedText().strip()
        lnk = stripText.toLowerCase().replace(" ", "-")
        sbTOC << getPrecStr(nLevel - startNodeLevel, "\t") << "- [$stripText](#$lnk)  " << lf
    }
    sbTOC << lf << "  " << lf
}

// adding metadata block
addMetadata(sb)

node.findAll().each {
    // write header - don't touch user defined headers
    transText = it.getTransformedText().strip()
    if (it.getPlainText().startsWith(hIndicator)) {
        sb << "  " << lf << transText << lf
    }
    else {
        sb << "  " << lf << getPrecStr(it.getNodeLevel(true) - startNodeLevel + 1, hIndicator) << " $transText" << lf
    }
    sb << "  " << lf

    // picture assigned to node
    if (it.getExternalObject()) {
        uri = it.getExternalObject().getUri()
        sb << "![$it.text]($uri)  " << lf
    }

    // alias of node
    if (!it.getAlias().isEmpty()) {
        sb << "*Alias :* " << it.getAlias() << "  " << lf
    }

    // tags of node
    tagsList = it.getTags().getTags()
    if (tagsList.size() > 0) {
        sb << "*Tags :* " << tagsList.toString() << "  " << lf
    }

    // details of node
    if (it.getDetails()) {
        sb << "*Details :*  " << lf << it.getDetails().toString() << "  " << lf
    }

    // handle attributes - written as code-block - formulas calculated
    if (!it.attributes.empty) {
        attrs = it.getAttributes()
        sb << "*Attributes :*  " << lf
        sb << "  " << lf
        sb << "~~~" << lf
        for (i = 0; i < attrs.size(); i++) {
            sb << attrs.getNames()[i] << " : " << attrs.getValues()[i] << "  " << lf
        }
        sb << "~~~" << lf
    }

    // export Freeplane links (hyper, local hyper, website) as markdown
    if (it.getLink()) {
        linkText = it.getLink().getText()
        // link to another node - 'local hyperlink' - structure : #ID_...
        if (linkText.startsWith("#")) {
            linkID = linkText.replace("#", "")
            if (linkID) {
                linkedNode = it.getMindMap().node(linkID)
                urlText = linkedNode.getText().toLowerCase()
                urlText = urlText.replace(" ","-")
                outLnkUrl = "#".concat(urlText)
                outLnkText = linkedNode.getText()
            }
        }
        // link to a file - cutting 'file:'
        if (linkText.startsWith("file:")) {
            outLnkUrl = linkText.replace("file:", "")
            outLnkText = it.getPlainText()
        }
        // link to a website - link is correct in markdown
        if (linkText.startsWith("https:")) {
            outLnkUrl = linkText
            outLnkText = it.getPlainText()
        }
        sb << lf << "*Link :* [" << outLnkText << "]("
        sb << outLnkUrl << ")  " << lf
    }

    it.getConnectorsOut().each { conn ->
        linkedNode = conn.getTarget()
        urlText = linkedNode.getText().toLowerCase()
        urlText = urlText.replace(" ", "-")
        outLnkUrl = "#".concat(urlText)
        outLnkText = linkedNode.getText()
        srcLabel = conn.getSourceLabel() ?: "n.a."
        midLabel = conn.getMiddleLabel() ?: "n.a."
        trgLabel = conn.getTargetLabel() ?: "n.a."
        sb << lf << "*..linked to :* [" << outLnkText << "]("
        sb << outLnkUrl << ") ; " << " Labesls{" << srcLabel << ", " 
            << midLabel << ", " << trgLabel << "}" << lf
    }

    it.getConnectorsIn().each { conn ->
        sourceNode = conn.getSource()    
        urlText = sourceNode.getText().toLowerCase()
        urlText = urlText.replace(" ", "-")
        srcLnkUrl = "#".concat(urlText)
        srcLnkText = sourceNode.getText()
        srcLabel = conn.getSourceLabel() ?: "n.a."
        midLabel = conn.getMiddleLabel() ?: "n.a."
        trgLabel = conn.getTargetLabel() ?: "n.a."
        sb << lf << "*..linked from :* [" << srcLnkText << "]("
        sb << srcLnkUrl << ") ; " << " Labesls{" << srcLabel << ", " 
            << midLabel << ", " << trgLabel << "}" << lf
    }

    // adding TOC after first title - it's empty if not created
    if (it == node) sb << sbTOC.toString()

    // copy markdown note as paragraph text
    if (it.getNote()) {
        sb << "  " << lf << it.getNote() << "  " << lf
    }

}

// write export to choosen file
try {
    FileWriter myWriter = new FileWriter(file)
    myWriter.write(sb.toString())
    myWriter.close();
    c.setStatusInfo("standard", "Successfully exported to " + file, "button_ok")
    ui.informationMessage("Successfully exported to " + file)
} catch (IOException ex) {
    ui.errorMessage("An error occurred ! " + ex)
}
// write export to Root-Note
// node.mindMap.root.note = sb.toString()
