// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-06-26
// (c) licensed under GPL-3.0 or later

/*
	This script creates a link to a node in another Mindmap and copies it to Clipboard 
	for further use - e.g. setting an attributes value as Hyperlink(URL) manually.
	(If you insert a Hyperlink in an attribute and not only text you get a little 
	arrow to click on.)

	1. Select Target Mindmap
	2. Select Target Node from list
	3. Optional - create a link to selected target node
	4. Optional - create a link from target backwards

	It is recommended to set the values for your own needs :
		sortList	: sorting the list of possible target nodes in ascending numerical (ASCII) order
		askFwd 		: show Dialog to ask for link forward (from actual node to selected target)
		askBkwd 	: show Dialog to ask for link backwards (from selected target back to node)
		linkFwd 	: create a link in node to target (default iff askFwd == false)
		linkBkwd 	: create a link from target node backwards (default iff askBkwd == false)

	sortList == false : target nodes are listed in order of occurrence with preceding dashes
*/

/* 
*/

import javax.swing.JOptionPane
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import org.freeplane.core.util.TextUtils

final String lf = System.lineSeparator()

// ..sorting node list
final boolean sortList = false

// ask for links
final boolean askFwd = true
final boolean askBkwd = true

// defualt values for creating links iff ask.. == false
boolean linkFwd = true
boolean linkBkwd = false

/*
	method	: getLevelStr
	task	: define preceding dashes depending on node-level
	input	: number of dashes - node level
	return	: A string of dashes
*/
def String getLevelStr(int count) {
	String prec = ""
	for (i=0; i < count; i++) {
		prec += "- "
	}
	return prec
}

/*
	method	: getTargetArray
	task 	: define Array of strings of target nodes
	input	: boolean for sorting (..determines preceding dashes), root of target map
	return	: Array of Strings with text and ID of target nodes
*/
def String[] getTargetArray(boolean sort, targetRoot) {
	ArrayList <String> nodes = new ArrayList<String>()
	if (sort) {
		for (tNode in targetRoot.findAll()) {
			nodes.add(tNode.getText() + " : " + tNode.getId())
		}
	} else {
		for (tNode in targetRoot.findAll()) {
			nodes.add(getLevelStr(tNode.getNodeLevel(true)) + tNode.getText() + " : " + tNode.getId())
		}
	}
	return nodes.toArray()
}


// choose target mindmap
JFileChooser fChooser = ui.newFileChooser()
fChooser.setDialogTitle('Select Target Mindmap for Link..')
FileNameExtensionFilter filter = new FileNameExtensionFilter("Mindmap files", "mm")
fChooser.setFileFilter(filter);

int retVal = fChooser.showOpenDialog()
if (retVal != JFileChooser.APPROVE_OPTION) return

File targetFile = fChooser.getSelectedFile()
targetMap = c.mapLoader(targetFile).getMindMap()

nodesArray = getTargetArray(sortList, targetMap.getRoot())
if (sortList) Arrays.sort(nodesArray)

// choose target node
String selNodeStr = JOptionPane.showInputDialog(ui.getFrame(),"Create link to node : ", "Select from '" + targetMap.getName() + "'", 
	JOptionPane.QUESTION_MESSAGE, null, nodesArray, nodesArray[0])
if (!selNodeStr) return

int start = selNodeStr.indexOf(":") + 2
String targetID = selNodeStr.substring(start, selNodeStr.length()).trim()
targetNode = targetMap.node(targetID)

//create string (Hyperlink with #ID) for link to target
String targetLink = targetFile.getAbsolutePath() + "#" + targetID

//copy link to Clipboard for further use - f.e. setting an attributes value
TextUtils.copyToClipboard(targetLink)
c.setStatusInfo(targetLink + " .. copied to Clipboard !")
ui.informationMessage("Created link copied to Clipboard !")

//creating Hyperlink in node to target-node via ID
if (askFwd) {
	fwd = ui.showConfirmDialog(null, " Do you want to link (overwrite) actual node to target ?", 
		"Link to Node", JOptionPane.YES_NO_OPTION)
	linkFwd = (fwd == JOptionPane.YES_OPTION) ? true : false
}
if (linkFwd) node.getLink().setText(targetLink)

// creating Hyperlink backwards in target-node to node via ID
if (askBkwd) {
	back = ui.showConfirmDialog(null, "Do you want to link (overwrite) target node backwards ?",
			"Link to Node", JOptionPane.YES_NO_OPTION)
	linkBkwd = (back == JOptionPane.YES_OPTION) ? true : false
}
if (linkBkwd) {
	srcFile = node.getMindMap().getFile()
	if (!srcFile) {
		ui.errorMessage("Can't link backwards to non existing file ! ..unsaved ?")
		return
	}
	revLink = srcFile.getAbsolutePath() + "#" + node.getId()
	targetNode.getLink().setText(revLink)
}


