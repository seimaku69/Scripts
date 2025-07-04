// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-07-02
// (c) licensed under GPL-3.0 or later

/*
	This script opens a selected MD-link or all MD-links in note with the associated application.
	It finds normal links and referenced links - no embedded HTML.

	It is recommended to adjust 'pause' to your own needs (system) if there are missing loadUris.
*/

/*
*/

import javax.swing.JOptionPane
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.util.concurrent.TimeUnit

final long pause = 500L

/*
	method	: openMDLink
	task	: open a markdown-link with associated application
	input	: link text
*/
def void openMdLink(String linkText) {
	start = -1
	end = -1
	// links without reference
	if (linkText.contains("](")) {		
		start = linkText.indexOf("](") + 2
		//check for optional title - like : '[linktext](.... "opt_titel")'
		end = (linkText.contains(' "')) ? linkText.indexOf(' "') : linkText.indexOf(")")
	}
	// defined references - like : '[ref]: ....'
	if (linkText.contains("]: ")) {		
		if (linkText.contains("<")) {
			start = linkText.indexOf("<") + 1
			end = linkText.indexOf(">")
		} else {
			start = linkText.indexOf(":") + 2
			end = linkText.length()
		}
	}
	if (start != -1 && end != -1) linkText = linkText.substring(start, end).trim()
	URI uri = java.net.URI.create(linkText)
	loadUri(uri)
}

// find links in note
noteText = node.note.toString()

ArrayList <String> linkList = new ArrayList<String>()

// Adding option to open all MD-links at once as first one
linkList.add(0, "..open all links")

// Finding links like '[LinkText](LinkToResource "opt_title")'
Pattern pattern = Pattern.compile("\\[.+\\]\\(.+\\)")
Matcher matcher = pattern.matcher(noteText)
while (matcher.find()) {
	linkList.add(matcher.group())
}

// Finding references like '[ref]: LinkToResource ...' or '[ref]: <LinkToResource> ...'
// Optional titles are truncated at the end after the necessary whitespace ('[^\\h]')
pattern = Pattern.compile("\\[.+\\]:\\h[^\\h]+")
matcher = pattern.matcher(noteText)
while (matcher.find()) {
	linkList.add(matcher.group())
}

if (linkList.size() == 1) {
	ui.informationMessage("No MD-Links found in note !")
	return
}
//show OptionPane to select link to open..
int count = linkList.size() - 1
linkArray = linkList.toArray()
linkText = JOptionPane.showInputDialog(ui.getFrame(),"Values of MD-Links in note (" + count.toString() + ") : ",
     "Select link to open with associated application.. ", JOptionPane.QUESTION_MESSAGE, null, linkArray, linkArray[0])
if (!linkText) return

//ui.informationMessage(exVal)
if (linkText == "..open all links") {
	for (i = 1; i < linkList.size(); i++) {
		openMdLink(linkList[i])
		//..waiting to avoid problems with multiple loads
		TimeUnit.MILLISECONDS.sleep(pause)
	}
} else {
	openMdLink(linkText)
}