// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2024-06-25
// (c) licensed under GPL-3.0 or later


/* 
	This script appends a child node and a hyperlink for a selelcted file
	at the actual selected node.
	Filename is used for node text.

	To append a whole directory you can use 'File > Import > Folder structure'
	in Freeplane.
*/

import javax.swing.JFileChooser
import javax.swing.JOptionPane

JFileChooser fC = ui.newFileChooser()

int retVal = fC.showOpenDialog()
if (retVal != JFileChooser.APPROVE_OPTION) return
file = fC.getSelectedFile()
newChildName = file.getName()

exists = false
for (child in node.getChildren()) {
	if (child.getText() == newChildName) {
		exists = true
		break
	}
}
if (exists) {
	retVal = ui.showConfirmDialog(null, "This child exists. Do you want to append another child ?",
        "Appending child node", JOptionPane.YES_NO_OPTION)
        if (retVal != JOptionPane.YES_OPTION) return
}
//appending child node
child = node.createChild(newChildName)
child.getLink().setFile(file)
