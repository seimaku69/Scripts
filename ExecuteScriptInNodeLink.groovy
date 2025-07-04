// @ExecutionModes({ON_SELECTED_NODE})

// author : M.Seilnacht
// date : 2025-06-26
// (c) licensed under GPL-3.0 or later

/* 
    Executes the script which is a Hyperlink of selected node.

    ATTENTION :
    This Script permits all rigths to selected Script ! (read, write, execute)

    The linked script is executed on selected node !
    Paths can be absolut or relativ - see Freeplanes configuration.
*/

def final lf = System.lineSeparator()
def final fsep = File.separator

String path

String linkText = node.getLink().getText()

if (!linkText) {
    ui.errorMessage(node.getText() + " : No link in node available !")
    return
}
// absolut or relativ path ?
if (linkText.startsWith('file:') || linkText.startsWith(fsep)) {
    path = linkText.replace('file:','')
} else {
    path = node.getMindMap().getFile().getParent() + fsep + linkText
}
path = path.strip()

File scriptFile = new File(path)
if (!scriptFile.exists()) {
    ui.errorMessage('Could not find : ' + lf + path)
    return
}
script = c.script(scriptFile)
script.withAllPermissions()
script.executeOn(node)