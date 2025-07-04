// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-06-26
// (c) licensed under GPL-3.0 or later

/* 
    This script executes a script-file which is defined as value in an attribute.

    ATTENTION : 
    This Script permits all rigths to selected Script ! (read, write, execute)
*/


import javax.swing.JOptionPane
import javax.swing.JFileChooser

def final lf = System.lineSeparator()
def final fsep = File.separator

String path

attrValues = node.getAttributes().getValues()

if (attrValues.size() == 0){
    ui.errorMessage(node.getText() + " : No attributes found !")
    return
}

//show OptionPane to select attribute value
String linkText = JOptionPane.showInputDialog(ui.getFrame(), "Node : " + node.getText() + lf + "Values in attributes (" 
    + attrValues.size().toString() + ") : ",
    "Select to execute.. ", JOptionPane.QUESTION_MESSAGE, null, attrValues.toArray(), attrValues[0])
if (!linkText) return
// ..adding absolut path if link is relativ
if (linkText.startsWith('file:') || linkText.startsWith(fsep)) {
    path = linkText.replace('file:','')
} else {
    path = node.getMindMap().getFile().getParent() + fsep + linkText
}
path = path.strip()
File scriptFile = new File(path)
if (!scriptFile.exists()) {
    ui.errorMessage(node.getText() + " - Could not find : " + lf + path)
    return
}
script = c.script(scriptFile)
script.withAllPermissions()
script.executeOn(node)