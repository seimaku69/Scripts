// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2024-05-20
// (c) licensed under GPL-3.0 or later

/*
    This scripts opens actual mindmap in a new Freeplane instance.
    Map in actual instance will be closed if user submits.
    ATTENTION : 
        - Be sure to enable multiple instances of Freeplane in settings.
*/

/* 
*/

import javax.swing.JOptionPane

//def sout = new StringBuffer()
//def serr = new StringBuffer()

map = node.getMindMap()
actFile = map.getFile()
if (!actFile || !actFile.exists()) {
    ui.errorMessage("Ther's no file for this map !")
    return
}

int input = ui.showConfirmDialog(null,"Close this Mindmap in this instance ?", 
    "Open map in new instance", JOptionPane.YES_NO_CANCEL_OPTION)
switch (input) {
    case JOptionPane.YES_OPTION :
        map.close(false, true)
        break
    case JOptionPane.CANCEL_OPTION :
        return
}

//pmdCommand = "freeplane " + actFile.getAbsolutePath() // don't work with whitespaces
pmdCommand = "freeplane " + actFile
try {
    def process = pmdCommand.execute()
} catch (Exception e) {
    ui.errorMessage("Can not start Freeplane !")
}
