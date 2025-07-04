// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-07-02
// (c) licensed under GPL-3.0 or later

/*
	This script opens a selected or all links in node attributes with
	the associated application in your system. The standard behaviour 
	of Freeplane is extended, because values of attributes are recognized
	which are not explicit defined as links :
	- Markdown links like '[link_text](link)'
	- URLs like 'https://github.com'
	- local files like './OpenLinkFrom Attributes.groovy' or
		'./../Scripts/CreateTrackingID.groovy'
*/

import javax.swing.JOptionPane
import java.util.concurrent.TimeUnit

final long pause = 500L  //puse 500 miliseconds

// opens given link with associated application
def void openLink(String linkText) {
	// substring of real link
	start = linkText.indexOf("(") + 1
	end = linkText.lastIndexOf(")")
	if (start != -1 && end != -1) linkText = linkText.substring(start, end)
	uri = java.net.URI.create(linkText)
	loadUri(uri)
}

// create list of attributes
attrMap = node.getAttributes()
size = attrMap.size() + 1
attrList = new String[size]

if (node.getAttributes().size() == 0){
    ui.errorMessage("No attributes found in node $node.text !")
    return
}

attrList[0] = "..open all links"
i = 1
node.attributes.each {
	attrList[i] = it.value.toString()
    i++
}

//show OptionPane to select attribute value
linkText = JOptionPane.showInputDialog(ui.getFrame(),"Values in attributes (" + i + ") : ",
     "Select link to open.. ", JOptionPane.QUESTION_MESSAGE, null, attrList, attrList[0])
if (!linkText) return

if (linkText == "..open all links") {
	for (i = 1; i < size; i++) {
		openLink(attrList[i])
		//..waiting to avoid problems with multiple loads
		TimeUnit.MILLISECONDS.sleep(pause)
	}
} else {
	openLink(linkText)
}