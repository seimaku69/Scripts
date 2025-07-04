// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-06-27
// (c) licensed under GPL-3.0 or later

// status : develop

/* 
    This script searches in a selectable folder for
    files which are named with the 'TrID' from the actual selected node.
    'TrID' : Tracking ID : Can be generated (see script 'GenerateTrackingID') or 
	set manually for a node to connect files, emails or documents in the system.
	To define a connection you can use the Tracking ID in their filename, header, a.s.o.
*/
/*
*/

import javax.swing.JFileChooser
import javax.swing.JOptionPane

final lf = System.lineSeparator()

final File actFile = node.getMindMap().getFile()

/*
	method	: findFiles
	task	: find files with matching names to given search-string
	input	: initial Folder, search-string (regular expression), list of found files
	return	: list of all found files
	remark	: recursive - to step down the subfolders

*/
def int findFiles(File folder, String searchStr, ArrayList<File> foundFiles) {
	for (file in folder.listFiles()) {
		if (file.isFile()) {
			if (file.getPath().matches(searchStr)) foundFiles.add(file)
		} else {
			findFiles(file, searchStr, foundFiles)
		}
	}
	return foundFiles.size()
}


// create search pattern
String searchStr = ""
// files matching the search string (regexp)
ArrayList<File> foundFiles = new ArrayList<File>()

String trID = node['TrID']
if (trID) {
	searchStr = ".*" + trID + ".*"
} else {
	ui.errorMessage(node.getText() + " : No TrID found !")
	return
}

// select a directory to start searching
JFileChooser fc = ui.newFileChooser(actFile)
fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
fc.setDialogTitle("Select folder to search for files..")
int retVal = fc.showOpenDialog()

if (retVal != JFileChooser.APPROVE_OPTION) return
String searchDir = fc.getSelectedFile().getAbsolutePath()
// start directory
File folder = new File(searchDir)
if (!folder.exists()) {
    ui.errorMessage(folder.getPath() + lf + "..does not exist ?")
    return
}

// adding files in selected directory - recursive
int filesCount = findFiles(folder, searchStr,foundFiles)

if (filesCount == 0) {
	ui.errorMessage("No files found for TrID : " + trID + " in " + searchDir)
	return
}

ui.informationMessage(filesCount.toString() + " files found : " + lf + foundFiles)

// ask for creation of child nodes
int exec = ui.showConfirmDialog(null, "Do you want to create a child node for each file with this Tracking-ID ?",
	"Child Creation", JOptionPane.YES_NO_OPTION)
	if (exec != JOptionPane.YES_OPTION) return
// create a child node for every found file
foundFiles.each {
	newChild = node.createChild(it.getName())
	newChild.getLink().setFile(it)
	newChild["file"] = it.getAbsolutePath()
}