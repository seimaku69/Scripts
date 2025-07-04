// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2024-07-02
// (c) licensed under GPL-3.0 or later

/*
This script opens all defined URL/URI in seleceted nodes.
Be sure to permit operations (network, execute other applications) for Scripting-Plugins !
*/


def linkText = node.link.text

if (linkText){
    def javaURI = new java.net.URI(linkText)
    loadUri(linkText)
    c.setStatusInfo("standard", "Link loaded.. " + linkText, "button_ok")
}
else {
    c.setStatusInfo("standard", "Selected node has no link.", "button_cancel")
    ui.errorMessage(node.getText() + " : Selected node has no link !")
}