// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-06-26
// (c) licensed under GPL-3.0 or later

/*
    This script generates a unique Tracking-ID which is based
    on actual date-time.
    ..useful to connect other elements to a mindmap.
    ..expand headers of emails or filenames with a Tracking-ID to recover them in the system.

    Attention :
    Uncheck the 'Feature' to recognize date-formats automatically in Freeplanes configuration !
    Otherwise the value of the attribute  'TrID' will be converted to a date-format by insertion.
    To avoid unchecking use brackets in format string for 'today' or hashCode.
    Using a date-time brings an extra meaning in all elements for the user.
*/

import org.freeplane.core.util.TextUtils
import javax.swing.JOptionPane

final lf = System.lineSeparator()

// trID = new Date().hashCode().toString()
String trID = format(new Date(), "yyMMdd-HHmmss")
TextUtils.copyToClipboard(trID)
retVal = ui.showConfirmDialog(null,"TrID " + trID + " copied to Clipboard. " + lf + 
    "Do you want to set attribute TrID ? ", "Setting attribute..", JOptionPane.YES_NO_OPTION)
if (retVal == JOptionPane.YES_OPTION) node['TrID'] = trID
