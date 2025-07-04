// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2025-06-25
// (c) licensed under GPL-3.0 or later

/* 
    Executes the script named "tmpScript" in Freeplane's UserDirectory e.g. '~/.config/freeplane/[version]/tmpScript.groovy
    This tmpScript - file is generated and used by an external editor which substitutes the internal script editor.
    In 'OpenExternalScriptEditor' the same file have to be used !

    ATTENTION : 
    This Script permits all rigths to the executed Script ! (read, write, execute)
    It is recommended to set the value of 'askExec' for your own needs - you can use 'Ask' in scripting prefereces 
    of Freeplane too.
*/

import javax.swing.JOptionPane
final lf = System.lineSeparator()

// ..asking for execution with all permissions
final askExec = true

tmpPath = c.getUserDirectory().getAbsolutePath() + File.separator + "tmpScript.groovy"

scriptFile = new File(tmpPath)
if (!scriptFile.exists()) {
    ui.errorMessage("Temporary File : " + tmpPath + " does not exist ! " + lf +
        "Open Script-Editor and save it.")
    return
}else {
    if (askExec) {
    int exec = ui.showConfirmDialog(null, "Do you really want to execute the script in your external editor with all permissions ? " + lf + " $tmpPath",
        "Executing Script", JOptionPane.YES_NO_OPTION)
        if (exec != JOptionPane.YES_OPTION) return
    }
}
script = c.script(scriptFile)
script.withAllPermissions()
script.executeOn(node)
c.setStatusInfo("standard", "Script in editor executed !", "button_ok")
