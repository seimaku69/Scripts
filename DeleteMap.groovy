// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2024-06-26
// (c) licensed under GPL-3.0 or later

/*
    This script deletes the actuel Mindmap from system.
*/

// ATTENTION : 
// A deleted file can not be restored - it is not saved in Trash !

import javax.swing.JOptionPane

def sout = new StringBuffer()
def serr = new StringBuffer()

def actFile = map.getFile()

if (!actFile) {
    ui.errorMessage("There's no file assigned to this map - save it !")
    return
}
if (!actFile.exists()) {
    ui.errorMessage(actFile.getAbsolutePath() + " .. does not exist !")
    return
}

int input = ui.showConfirmDialog(null, "Do you really want to delete this map finally ?",
    "Deleting Mindmap", JOptionPane.YES_NO_OPTION)
if (input == JOptionPane.YES_OPTION) {
    map.close(true, false)
    if (!actFile.delete()) {
        ui.errorMessage("Could not delete this file !")
    }
}

