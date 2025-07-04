// @ExecutionModes({ON_SINGLE_NODE})

// author : Markus Seilnacht
// date : 2025-06-20
// (c) licensed under GPL-3.0 or later

/*
	This script selects successors with a choosen distance (..only one level) or the whole subtree.
	If multiple nodes are selected in Freeplane, the set of distant successors for some nodes
	may be empty.

	If distance == 0 it selects the whole subtree including node itself
	If distance == 1 it selects childs..
	If distance == 2 it selects grandschilds..
	a.s.o.

	It is recommended to set 'askForDistance' to your own needs - 'false' selects successors with
	the default distance value.
*/

/*
*/

import javax.swing.JOptionPane
import java.util.HashMap

// ask for distance - false sets distance to 1 (childs)
boolean askForDistance = true

distance = 1 // default iff 'askForDistance == false'

final lf = System.lineSeparator()

// possible distances of successors 
ArrayList<Integer> distList = new ArrayList<Integer>()

// level of selected node itself
rootLevel = node.getNodeLevel(true)

// map of lists <nodelevel, list of nodes on this level>
HashMap<Integer, ArrayList<Node>> nodeMap = new HashMap<Integer, ArrayList<Node>>()
// creating map of lists of nodes
for (sNode in c.getSelecteds()) {
	for (n in sNode.findAll()) {
		level = n.getNodeLevel(true) - rootLevel
		if (!nodeMap.get(level)) {
			distList.add(level)
			newList = new ArrayList<Node>()
			nodeMap.put(level, newList)
		}
		nodeMap.get(level).add(n)
		// add node to subtree-list
		if (level != 0) nodeMap.get(0).add(n)
	}
}
// ..normally it should be sorted because of 'findAll'-breadth-first search
// Arrays.sort(distArray)
// show Pulldown to select distance
if (askForDistance) {
	distance = JOptionPane.showInputDialog(ui.getFrame(), "Choose distance " + lf + "(0:subtree, 1:child, 2:grandchild,..) :" , "Select successors", JOptionPane.QUESTION_MESSAGE,
		null, distList.toArray(), distList[1])
	if (distance == null) {
		c.setStatusInfo(" ..selection of successors aborted !")
		return
	}
}

c.selectMultipleNodes(nodeMap.get(distance))
