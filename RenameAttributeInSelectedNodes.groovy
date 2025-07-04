// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2025-05-25
// (c) licensed under GPL-3.0 or later

/*
	Idea : 
		With 'Edit > Node properties > Find and replace attribute..' you can only add some attributes or
		change name AND value of an Attribute. It is not possible to change the name of an attribute
		whithout touching the value in every node.

	This script renames an attribute in all selected nodes.
	Selectable attributes are taken from the first selected node only - otherwise the selection
	doesn't make sense.

	ATTENTION :	This script needs Freeplane 1.12.11 or newer because of a Bug in
				'attributes.set(index, name, value)' in earlier versions.
*/

/*
*/

import javax.swing.JOptionPane
import org.freeplane.api.Attributes

List selNodes = c.getSelecteds()

Attributes attrs = node.getAttributes()
if(attrs.isEmpty()) {
	ui.informationMessage("No attributes available in first selected node !")
	return
}

String[] attrNames = attrs.getNames().toArray()

// Ask for attribute to rename
Arrays.sort(attrNames)
renAttr = JOptionPane.showInputDialog(ui.getFrame(), "Select an attribute (first selected node) to rename : ", "Select Attribute", JOptionPane.QUESTION_MESSAGE,
	null, attrNames, attrNames[0])
if (!renAttr) return
// Ask for new name
newName = JOptionPane.showInputDialog(ui.getFrame(), "New Name for '" + renAttr + "' ?" , "Rename Attribute", JOptionPane.QUESTION_MESSAGE)
if (!newName || newName == "") {
	c.setStatusInfo(" Rename attribute aborted !")
	return
}

for (n in c.getSelecteds()) {
	attributes = n.getAttributes()
	// ..scheint stabiler -> umbenanntes Attribut steht am Ende ?!
	// attrs.add(newName, value)
	// attrs.remove(index)

	// rename attribute
	int i = 0
	for (String name : attributes.getNames()) {
		if (name.equals(renAttr))
			attributes.set(i, newName, attributes.get(i))
		++i
	}
}
//Statusline Info
c.setStatusInfo(" Attribute(s) '" + renAttr + "' renamed to '" + newName + "'")
