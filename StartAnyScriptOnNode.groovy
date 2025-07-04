// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2024-05-26
// (c) licensed under GPL-3.0 or later

/*
    This script allows to select a file on your system and executes it as a script.
    With this approach you can edit your Freeplane scripts in the editor you 
    prefer (VSCodium, Notepad++, SublimeText,..) and save it anywhere you want.
    If you assign a shortcut in Freeplane ('F4' or 'F12' e.g.), you can start any script very easy !

    ATTENTION : 
    This Script permits all rigths to selected Script ! (read, write, execute)
*/

import javax.swing.JFileChooser

JFileChooser fC = ui.newFileChooser()
int retVal = fC.showOpenDialog()

if (retVal != JFileChooser.APPROVE_OPTION) return

file = fC.getSelectedFile()
if (!file.exists()) {
    ui.errorMessage(file.getAbsolutePath() + "..does not exist !")
    return
}

// script: Inherited Controller method from HeadlessMapCreator
sc = c.script(file)
sc.withAllPermissions()
sc.executeOn(node)
c.setStatusInfo("standard", file.getName() + " executed !", "button_ok")

