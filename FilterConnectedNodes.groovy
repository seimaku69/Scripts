// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// licensed under GPL
// date : 2025-06-21
// (c) licensed under GPL-3.0 or later

/*
	This script filters all childs, parents and all other
	connected (Connection, Links) nodes.
	All parents (path to root) is added to the filter because Freeplane
	shows a 'ambigous' connection to root for the seleced one
	if there are nodes in between (getNodeLevel() > 1).

	It is recommended to set 'allUndold' to your own needs.
	If 'allUnfold' is true all nodes are shown unfolded.
*/
/*
*/

final allUnfold = true

// add parents of actNode to list
void addParents(list, actNode) {
	while (actNode.getParent()) {
		if (!list.contains(actNode.getParent())) list.add(actNode.getParent())
		actNode = actNode.getParent()
	}
}

ArrayList<Node> nodeList = new ArrayList<Node>()


final map = node.getMindMap()
final root = map.getRoot()
final idStrSelf = '#' + node.getId()

// add selected node (self) and childs to filter list
for (n in node.findAll()) {
	nodeList.add(n)
}

// adding all connections to other nodes in attributes
for (val in node.getAttributes().getValues()) {
	valStr = val.toString()
	if (valStr.startsWith('#')) {
		tarNode = map.node(valStr.substring(1))
		if (tarNode) {
			nodeList.add(tarNode)
			addParents(nodeList, tarNode)
		}
	}
}

// add all predecessors of self
addParents(nodeList, node)

// ..add linked node (via '#ID') from self to another
// and parents
linkedNode = node.getLink().getNode()
if (linkedNode) {
	nodeList.add(linkedNode)
	addParents(nodeList, linkedNode)
}

// ..add connected nodes (via Connector - visible arrow)
for (n in node.getConnectorsOut()) {
	nTar = n.getTarget()
	nodeList.add(nTar)
	addParents(nodeList, nTar)
}
for (n in node.getConnectorsIn()) {
	nSrc = n.getSource()
	nodeList.add(nSrc)
	addParents(nodeList, nSrc)
}

// ..add sources and their parents which are linked to self
// (linked via '#ID' in Hyperlink or Attribute) 
for (n in root.findAll()) {
	if (n.getLink().getNode() == node) {
		nodeList.add(n)
		addParents(nodeList, n)
	}
	attrVal = n.getAttributes().getValues()
	if (!attrVal.isEmpty()) {
		for (name in attrVal) {
			if (name.equals(idStrSelf)) {
				nodeList.add(n)
				addParents(nodeList, n)
			}
		}
	}
}

// add root - Freeplane adds root actual anyway..
//nodeList.add(root)
ui.informationMessage(nodeList)
// make all folded nodes unfolded
if (allUnfold) {
	for (n in nodeList) {
		n.setFolded(false)
	}
}

//map.hide(false, false, { (nodeList.indexOf(it) == -1) })
// ..similar to
map.filter(false, false, { (nodeList.indexOf(it) != -1) })
