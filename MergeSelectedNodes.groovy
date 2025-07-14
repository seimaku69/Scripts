// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2025-06-15
// (c) licensed under GPL-3.0 or later

/*
	This script merges selected nodes in the first selected one.
	You can choose which elements should be merged - like tags, details, attributes, 
		notes, childs and connectors.
	After merge you can choose to delete the merged nodes - except the first one.

	Aliases of nodes are single Strings - they can't be merged !
	Connectors of selected nodes are put to the merged one - their styles will be copied.
*/

/*
*/

import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JCheckBox

import java.awt.Event
import java.awt.event.ItemListener
import java.awt.event.ItemEvent
import java.awt.GridLayout

// presets for merging elements
boolean mergeIcons = true
boolean mergeTags = true
boolean mergeDetails = true
boolean mergeAttrs = true
boolean mergeNotes = true
boolean mergeChilds = true
boolean mergeConns = true

/*
	task	: copy style of src connector to dest
*/
def copyConnectorStyle(src, dest) {
	dest.setLabelFontFamily(src.getLabelFontFamily())
	dest.setSourceLabel(src.getSourceLabel())
	dest.setMiddleLabel(src.getMiddleLabel())
	dest.setTargetLabel(src.getTargetLabel())
	dest.setColor(src.getColor())
	dest.setWidth(src.getWidth())
	dest.setEndArrow(src.getEndArrow())
	dest.setStartArrow(src.getStartArrow())
	dest.setOpacity(src.getOpacity())
	dest.setShape(src.getShape())
	dest.setDashArray(src.getDashArray())
}

// Array for nodes to delete after merge
ArrayList <String> delNodeIDs = new ArrayList<String>()

selNodes = c.getSelecteds()

// selected more than one ?
if (selNodes.size() <= 1) {
	ui.errorMessage("Not enough nodes selected !")
	return
}

// choosing which elements to merge
JPanel panel = new JPanel()
panel.setLayout(new GridLayout(7,1))
// Checkbox for Icons
ckIcons = new JCheckBox("Icons", mergeIcons)
ckIcons.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeIcons = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckIcons)
// Checkbox for Tags
ckTags = new JCheckBox("Tags", mergeTags)
ckTags.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeTags = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckTags)

// Checkbox for details
ckDetails = new JCheckBox("Details", mergeDetails)
ckDetails.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeDetails = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckDetails)
// Checkbox for attributes
ckAttrs = new JCheckBox("Attributes", mergeAttrs)
ckAttrs.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeAttrs = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckAttrs)
// Checkbox for notes
ckNotes = new JCheckBox("Notes", mergeNotes)
ckNotes.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeNotes = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckNotes)
// Checkbox for childs
ckChilds = new JCheckBox("Childs", mergeChilds)
ckChilds.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeChilds = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckChilds)
// Checkbox for Connectors
ckConns = new JCheckBox("Connectors", mergeConns)
ckConns.addItemListener(new ItemListener() {
	public void itemStateChanged(ItemEvent e) {
		mergeConns = (e.getStateChange() == ItemEvent.SELECTED) ? true : false
	}
})
panel.add(ckConns)
// show dialog to select elements
ret = JOptionPane.showConfirmDialog(ui.getFrame(), panel, "Select elements for merge : ", JOptionPane.OK_CANCEL_OPTION)
if (ret == JOptionPane.CANCEL_OPTION) return

// first selected node is the first (index == 0) in selNodes
firstNode = selNodes[0]
map = firstNode.getMindMap()
// generate list of IDs for merged nodes without the first one
for (i = 1; i < selNodes.size(); i++) {
	delNodeIDs.add(selNodes[i].getId())
}

firstIcons = firstNode.getIcons()
firstTags = firstNode.getTags()
firstDetails = firstNode.getDetails()
firstAttrs = firstNode.getAttributes()
firstNote = firstNode.getNote()

// merging details, attributes and notes in firstNode
for (i = 1; i < selNodes.size(); i++) {
	if (mergeIcons) {
		for (icon in selNodes[i].getIcons()) {
			firstIcons.add(icon)
		}
	}
	if (mergeTags) {
		firstTags.add(selNodes[i].getTags().getTags())
	}
	if (mergeDetails) {
		if (firstDetails == null) {
			if (selNodes[i].getDetails() != null) firstNode.setDetails(selNodes[i].getDetails())
		} else {
			if (selNodes[i].getDetails() != null) firstNode.setDetails(firstDetails + selNodes[i].getDetails())
		}
	}
	if (mergeAttrs) { 
		attrs = selNodes[i].getAttributes()
		for (j = 0; j < attrs.size(); j++) {
			firstAttrs.set(attrs.getKey(j), attrs.get(j))
		}
	}
	if (mergeNotes) {
		if (firstNote == null) {
			if (selNodes[i].getNote() != null) firstNode.setNote(selNodes[i].getNote())
		} else {
			if (selNodes[i].getNote() != null) firstNode.setNote(firstNote + selNodes[i].getNote())
		}
	}
	if (mergeChilds) {
		for (child in selNodes[i].getChildren()) {
			firstNode.appendBranch(child)
			//child.moveTo(firstNode)
		}
	}
	if (mergeConns) {
		// ConnectorsIn
		for (conn in selNodes[i].getConnectorsIn()) {
			if (conn.getSource() != firstNode) {
				newConn = conn.getSource().addConnectorTo(firstNode)
				copyConnectorStyle(conn, newConn)
			}
		}
		// ConnectorsOut
		for (conn in selNodes[i].getConnectorsOut()) {
			if (conn.getTarget() != firstNode) {
				newConn = firstNode.addConnectorTo(conn.getTarget())
				copyConnectorStyle(conn, newConn)
			}
		}
	}
}
// delete merged nodes - except first one
int input = JOptionPane.showConfirmDialog(ui.getFrame(),"Do you want to delete merged nodes ?", 
    "Merging Nodes", JOptionPane.YES_NO_OPTION)
if (input == JOptionPane.YES_OPTION) {
	for (id in delNodeIDs) {
		map.node(id).delete()
	}
	c.setStatusInfo("..merged nodes deleted !")
}

	
