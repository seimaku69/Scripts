<!-- Begin Metadata  -->
[Freeplane file]:- '/home/markus/Zentrum/Projekte/Implementierung/Freeplane/Mindmaps/Github_Readme.mm'  
[Export date]:- '2025-07-06 19:17:20'  
[Export script]:- 'ExportBranchToMarkdown.groovy'  
[Script author]:- 'Markus Seilnacht; seimaku(at)proton(dot)me'  
<!-- End Metadata -->  
  
# README - Freeplane Scripts  
  
*Attributes :*  

 > author : Markus Seilnacht  
 > date : 2025-07-06 19:17    
  
## Table of Contents  
  
- [README - Freeplane Scripts](#readme---freeplane-scripts)  
	- [Introduction](#introduction)  
	- [Installation of scripts](#installation-of-scripts)  
	- [Scripts](#scripts)  
		- [Script-Developement](#script-developement)  
			- [Start any script on a node](#start-any-script-on-a-node)  
			- [Open external script editor](#open-external-script-editor)  
			- [Execute script in editor](#execute-script-in-editor)  
			- [Open note in external editor](#open-note-in-external-editor)  
		- [Handling Mindmaps](#handling-mindmaps)  
			- [Delete a mindmap](#delete-a-mindmap)  
			- [Open a mindmap in new Freeplane instance](#open-a-mindmap-in-new-freeplane-instance)  
			- [Select distant successors](#select-distant-successors)  
			- [Filter connected nodes](#filter-connected-nodes)  
			- [Merge selected nodes](#merge-selected-nodes)  
			- [Rename attributes in selected nodes](#rename-attributes-in-selected-nodes)  
		- [Handling Links and Files](#handling-links-and-files)  
			- [Execute script in attribute value](#execute-script-in-attribute-value)  
			- [Append node with file-link](#append-node-with-file-link)  
			- [Execute script in node link](#execute-script-in-node-link)  
			- [Open links of selected nodes](#open-links-of-selected-nodes)  
			- [Open link from attributes](#open-link-from-attributes)  
			- [Create link to a node in any map](#create-link-to-a-node-in-any-map)  
		- [Task management](#task-management)  
			- [List all tasks](#list-all-tasks)  
			- [List due tasks](#list-due-tasks)  
			- [Create Tracking-ID](#create-tracking-id)  
			- [Find files with Tracking-ID](#find-files-with-tracking-id)  
		- [Markdown](#markdown)  
			- [Export branch to Markdown](#export-branch-to-markdown)  
			- [Import Markdown to node](#import-markdown-to-node)  
			- [Open Markdown link in note](#open-markdown-link-in-note)  

  
  
## Introduction  
  
Freeplane is widely used in many fields of activity. Mindmapping isn't just a concept to support brainstorming,
it can be used to manage a lot of things in your private or business life.  
For a Mindmap it doesn't matter which information is modeled with its nodes, attributes, connections a.s.o.
  
Here you can find some scripts I developed to face my needs in very different working areas - especially
authoring with Markdown, task management, code developement, project management and many others.  
  
All of them do an important part of work in my IT-environment and using of Freeplane. They are published with 
my hope to be useful for you - it's a little appreciation for the work that has already been done in Freeplane.
For the most of them you have to permit the rights in Freeplane settings.  
  
*These scripts are published under GPL version 3 or later. There exists no warranty in any way for this code !*  
 
  
  
## Installation of scripts  
  
To use these scripts you just have to copy them in your script folder (e.g. '~/.config/freeplane/\<version\>/scripts)
('Tools \> Open User directory')/scripts and restart Freeplane - it's the standard procedure for installation of new scripts
in Freeplane.  
  
  
## Scripts  
  
### Script-Developement  
  
#### Start any script on a node  
  
**Idea :** Execute any script on your system in Freeplane.  
Normally you can only start scripts in the script editor or if they are installed in your script directory.  
Editing a script wherever you want and execute it without installation extends the possibilities enormous.  
  
**File :** *[StartAnyScriptOnNode.groovy](./StartAnyScriptOnNode.groovy)*  
  
**Task :** This script allows you to start any script in your environment without installing it in Freeplane's 
script folder.  
  
**Tip :** Define a shortcut in Freeplane to use this script.  
  
**Attention :** It permits all rights to your selected script - be sure about your code !
  
  
#### Open external script editor  
  
**Idea :** The standard (groovy) script editor in Freeplane is very restrictive. Often it is helpful to have some
features of a more powerful editor for implementation and testing code.  
If we use a predefined name for the script file during developement, we can start this one at any time we want and
keep it in the favorite editor.  
Is everything perfect, we can rename and install it in Freeplane's script directory..  
  
**File :** *[OpenExternalScriptEditor.groovy](./OpenExternalScriptEditor.groovy)*  
  
**Task :** You can define your favorite editor as your script editor and use all of its features like Syntax Highlighting a.s.o.  
By opening this editor, via this script, a temporary file, with a predefined name ("tmpScript.groovy"), is generated in 
your user directory. This temporary file is used (see [Execute script in editor](#execute-script-in-editor)) to execute it 
in Freeplane without installation and restart.  
This functionality replaces the standard groovy script editor with your favorite one.  
Of course, a real step-by-step debugging is not possible in this way, but in most cases it is more than sufficient if you
use messages an the coming up script engine errors.  
  
**Tip :** Use a shortcut in Freeplane to start this one and open your favorite editor. You don't have to use it, if you want
to open and edit "\<Freeplane-User-directory\>/tmpScript.groovy" manually.('~/.config/freeplane/\<version\>')  
  
**Attention :** The script opens the user-defined editor - it is neccessary to set the final String for 'editor' in this script 
to your own needs or preferences. Of course it should be unique..  
It permits all rights to your script in the editor - so be sure about your code.  
  
  
  
#### Execute script in editor  
  
**Idea :** Execute the script (predefined name) which is edited in your prefered editor.  
  
**File :** *[ExecuteScriptInEditor.groovy](./ExecuteScriptInEditor.groovy)*  
  
**Task :** This script is only reasonable in combination with the existing temporary script file ("tmpScript.groovy") - see 
[Open external script editor](#open-external-script-editor).  
It executes this file as a groovy script.  
With this you've got a total replacement of the standard groovy script editor in Freeplane.  
  
**Tip :** Use a shortcut in Freeplane to start this script in a very fast and simple way.  
  
**Attention :** It doesn't matter which editor you use to work with "tmpScript.groovy" - only name and path are used
in this execution.
  
  
#### Open note in external editor  
  
**Idea :** The standard note-editor in Freeplane is not very useful if you want to write text in a non-standard way
like **Markdown**, programming code, a.s.o. It would be much more comfortable to use an editor which offers more
features for your requirements - f.e. Syntax-Highlighting, Code-completion, Markdown rendering and a lot of others.  
Of course it should not be a 'big' IDE like Eclipse, VSCode or VSCodium because you will start it every time you edit a note
and yout don't need 99% of their features for editing Markdown only.  
If you've got an affinity for coding or lightwight IDEs [Geany](https://www.geany.org) (see Plugind) and 
[Kate](https://kate-editor.org) with activated 'Preview' module could be your best choice. If not, you can take a 
look to [Ghostwriter](https://ghostwriter.kde.org/de/), [Marktext](https://sourceforge.net/projects/marktext.mirror/)
or [Sublime Text](https://www.sublimetext.com)(install package "Markdown Preview").  
Freeplane's behaviour with Markdown is not the best - mostly it is better to use the Contenttype 'Standard'
and edit 'normal' ASCII-text..  
  
**File :** *[OpenNoteInExternalEditor.groovy](./OpenNoteInExternalEditor.groovy)*  
  
**Task :** This script opens the favorite editor with the note of the actual selected node - it replaces the internal 
note editor (e.g. F7).  
Therefore a temporary file is generated (name like '~ID_.....ext)' in '/tmp' directory. Its name
depends on the ID for the actual node and it's extension is determined by the content type of this note 
(see 'Tool panel -- Content types). The extension (ext) is important because your editor can recognize your file-type.  
Some editors, like Ghostwriter or Marktext don't care about the file-extension..  
  
**Tip :** ..same as above - use shortcut in Freeplane (e.g. Shift + F7) for quick access.  
  
**Attention :** It is recommended to set the path to your favorite editor in the final 'editor' String.  
Your editor is opened modal - means you can't access Freplane before closing this file. Freeplane is waiting
until you finished your edits to overtake the new text.  
In some cases it causes difficulties if you are using an existing instance of an editor !
  
  
### Handling Mindmaps  
  
#### Delete a mindmap  
  
**Idea :** You can not delete a map in your system directly in Freeplane. This makes it save on the one hand and is sometimes
obstructive on the other hand.  
  
**File :** *[DeleteMap.groovy](./DeleteMap.groovy)*  
  
**Task :** Deleting a map in filesystem from Freeplane.  
  
**Attention :** The file is not moved to trash - it's deleted finally !!  
  
#### Open a mindmap in new Freeplane instance  
  
**Idea :** Sometimes it is useful to see two maps at once or see the same map in 'Outline View' and 'Edit View'. Of course 
you can use a new Map-View ('View - new map view') and undock this window. But an undocked window is not a new instance 
of Freeplane - you miss a lot of things like menu, icon-bars, a.s.o.  
Especially when you want to link to a node in another map, it is very helpful to move around and see the IDs in the status bar.  
  
**File :** *[OpenMapInNewInstance.groovy](OpenMapInNewInstance.groovy)*  
  
**Task :** Open the actual map in a new instance of Freeplane. You can close or not this map in your first instance.  
  
#### Select distant successors  
  
**Idea :** Sometimes it is useful to select successors of a node at a certain level (in perspective of actual node).
Selecting childs is a subset of this question with the distance '1'.  
  
**File :** *[SelectDistantSuccessors.groovy](./SelectDistantSuccessors.groovy)*  
  
**Task :** This script asks for a level (dpendant of the selected nodes) and selects all successors at this level.  
(0 == complete subtree, 1 == childs, 2 == grandchilds,..) - default is 1.  
  
**Attention :**  

1. It is recommended to set 'askForDistance' to your own needs - 'false' means using the value of 'distance' as default.
That's a short way to configure it to often used selects - e.g. childs (distance = 1).
2. If multiple nodes are selected, it may be possible that some of them dont have any successors at the choosen distance.  
  
#### Filter connected nodes  
  
**Idea :** Often it is useful to which nodes are connected, in some case, to my actual one.  
It can be difficult to see because connections or relations between nodes have many possibilities :  
  
- connections as predecessors or successors
- explicit connections (In-Connectors and Out-Connectors; links with an arrow)
- implicit connection as Link in node core (links by IDs without an arrow; linked to or referenced by)
- implicit connections as links in attributes of the actual node
  
**File :** *[FilterConnectedNodes.groovy](FilterConnectedNodes.groovy)*  
  
**Task :** Filtering all nodes which are connected (in the sense above) with the actual node.  
Do not filter all connections of the predecessors or successors.  
  
**Attention :** Always add predecessors of connected nodes to avoid misleading standard behavior of Freeplane.  
(Standard behavior is to show a filtered node always with a direct connection to root - no matter which level
it occurs..)  
  
#### Merge selected nodes  
  
**Idea :** During the work with mindmaps you create a lot of nodes, attributes, notes and other
things which are representing your thoughts or informations.  
One of the big features of mindmapping is it's possibilities to create a fast picture of your interests and 
order them later. Not seldom, in a later overview, you recognize that some informations should be represented by one node
rather than by many.  
Or you created a few nodes for the same entity with slightly different informations..  
In this case a *merge* become handy.  
  
**File :** *[MergeSelectedNodes.groovy](./MergeSelectedNodes.groovy)*  
  
**Task :** This script merges the selected nodes in the first selected one.  
You can choose :  
  
1. Which elements of a node should be merged (icons, tags, details, attributes, notes, childs, connectors).
2. If you want to delete the merged nodes - except the first selected, of course.  
  
#### Rename attributes in selected nodes  
  
**Idea :** Attributes are properties of a represented entity in our mindmap. In some use-cases it would be necessary
to give them another name. Freeplane in it's actual version (1.12.11) does not offer any comfortable way to do that
for a set of nodes in your mindmap.  
  
**File :** *[RenameAttributeInSelectedNodes.groovy](./RenameAttributeInSelectedNodes.groovy)*  
  
**Task :** The script allows to rename an attribute for all selected nodes. The list of attributes you can choose from, 
is taken from the first selected.  
  
**Attention :** This script needs Freeplane 1.12.11 or later because of a former error in 'attributes.set(index,name,value)'.  
It renames only the attributes of selected nodes.  
  
### Handling Links and Files  
  
#### Execute script in attribute value  
  
**Idea :** Sometimes it can be useful to start a script which is linked in an attribute of a node.  
For example you can create a node with attributes and script-links as values to do some work in your 
mindmap in a very direct way, without installing the script generally in Freeplane.  
As value you can use any script-file..
  
**File :** [ExecuteScriptInAttributeValue.groovy](./ExecuteScriptInAttributeValue.groovy)  
  
**Task :** This script shows all attribute values for a node where you can choose one to execute it.  
  
**Attention :** The script permits all rights to your choosen one - so be sure about what your script
is doing.  
  
#### Append node with file-link  
  
**Idea :** If you managing tasks, orders, meeting-protocols or any other thigs it is often neccessary
to link some documents or pictures to an entity (node) in your mindmap.  
Of course you can manually add a node and set a link in this node, but it requieres a few steps to
do that..  
  
**File :** [AppendNodeWithFileLink.groovy](./AppendNodeWithFileLink.groovy)  
  
**Task :** This scripts appends a child-node with a link to the choosen file. The text is set to
the name of your file. You get a warning if you try to create a duplicate.  
  
**Attention :** If you want to add all files (and folders) in a directory, you can use Freeplane's 
funcionality in the menu 'File > Import > Folder structure'.  
  
#### Execute script in node link  
  
**Idea :** As in [Execute script in attribute value](#execute-script-in-attribute-value) it will be useful
to add some functionality with a script to a mindmap and access it via a link in a node.  
  
**File :** *[ExecuteScriptInNodeLink.groovy](./ExecuteScriptInNodeLink.groovy)*  
  
**Task :** This script executes a linked (Hyperlink in node) script-file.  
  
**Attention :** This script permits all rights to the script which is linked in the node (if possible).  
So be sure about your code ;-)  
  
#### Open links of selected nodes  
  
**Idea :** In Freeplane you can define a link to a node. You get little arrow before the node text and 
you can open this link by a single click.  
OK that's nice - but if I've got multiple nodes, let's say as resources (childs) to a task or project and I want
to open all these links simultaneously ?  
  
**File :** *[OpenLinksOfSelectedNodes.groovy](./OpenLinksOfSelectedNodes.groovy)*  
  
**Task :** This script opens all links of selected nodes at once.  
  
#### Open link from attributes  
  
**Idea :** In a lot of use-cases it can be useful to link some files or URLs in attribute values.  
Additionally it could be nice to use Markdown links in some values.  
Freeplane offers a possibility to set attribute values as links to files or web-pages (Add hyperlink 
file or URL - right click in attribute value).
These links are marked with a little red arrow at the beginning - you can double-click them to open.  
Texts without defining such a link, perhaps as simple text, are not recognized. Especially Markdown
links are not realised as links.  
  
**File :** *[OpenLinkFromAttributes.groovy](./OpenLinksFromAttributes.groovy)*  
  
**Task :** With this script you get a pull-down menu to choose from the attributes values of a node 
to open them with the associated application. If you choose 'all' this script tries to open all
the links at once.  
  
**Remark :** There's a little time-lag (default 500ms) to open one link after the other to avoid 
problems during the start process of needed application. I think it depends a little bit to the
running system, which value is best - keep an eye on that, if there are any problems.  
(variable 'pause' in the first lines of the script)  
  
#### Create link to a node in any map  
  
**Idea :** Freeplane has some functionality to create implicit and explicit links from one node to another.  
*Explicit links* are links which create an arrow (line) from one node to another in the mindmap - they can have 
an arrow-head at each side.  
*Implicit links* don't have an arrow - they are realized via a node ID (#ID_...). You can use them in node-text
and as a value in attributes. That's what happens if you set an attribute value via 'Set Node Link..'.  
Of course you can link to another mindmap via 'Hyperlink' but in the actual version (1.12.11) there's no 
possibility to set an implicit link to a node in another map.  
  
**File :** *[CreateLinkToNodeInAnyMap.groovy](./CreateLinkToNodeInAnyMap.groovy)*  
  
**Task :** With this script you can create an implicit link (via node-ID) to an arbitrary node in any Freeplane mindmap.  
You will be asked about the map and the node you want to create a link.  
This created link is copied to the Clipboard by default - perhaps you want to use it in an attribute or something - and you 
can choose if you want to set this link in your selected node and an vice-versa link in your target node.  
  
**ATTENTION :** Set these variables to your own needs :  
  
*sortList*	:	(true|false) - show target nodelist sorted or in order of occurrence (tree)  
  
*askFwd*	:	(true|false) - asking for setting link to target node (default : true)  
  
*askBkwd*	:	(true|false) - asking for setting link from target node backwards (default : true)  
  
*linkFwd*	:	(true|false) - setting for link to target node - default iff askFwd == false (default : true)  
  
*linkBkwd*	:	(true|false) - setting for link backwards from target node - default iff askBkwd == false (default : false)  
  
If you don't change any 'ask..' default value, you will be asked for both cases..
  
  
### Task management  
  
#### List all tasks  
  
**Idea :** Managing tasks is one of the most important use-cases today. Using a mindmapping application brings
a lot of benefits.  
Represinting tasks with nodes is very easy - adding attributes for priority, status or a person for responsibility,
linking some resources, appoint a due date reorder them, organize them in categories or split them dynamically into 
subtasks with proceeding projects are basic workflows with nodes in Freeplane.  
In my mindmaps I use some templates for task-nodes realize Kanban-Boards or GTD (Getting Things Done) concepts. 
One of the questions is how to get all undone or due tasks in one view..  
  
**File :** *[ListAllTasks.groovy](./ListAllTasks.groovy)*  
  
**Task :** Searching all undone tasks in a selected subtree and show them in a compact view - new created node
with timestamp and counter. So you can always see at which date/time you've generated the undone list.  
Create a child-node for every undone task and link it to the node representing this task for a quick access.  
If you do'nt need your 'report'(task-node) anymore, you can simply delete it in your mindmap.
  
**Remark :** This script looks for some attributes in your task-nodes :  

 | Variable | Value | meaning |
 | --- | --- |:--- |
 | tType | "TYPE" | attribut name identifies the type of this node |
 | tTask | "TASK" | attribute value identifies a task |
 | tStatus | "STATUS" | attribute name identifies the status of this node |
 | tDone | "DONE" | attribute value identifies a done task |
   
 So you get an attribute named 'TYPE' with a value 'TASK' - all other nodes - including them which do'nt have an 'TYPE' 
 attribute - are not identified as a task.  
 ..and an attribute named 'STATUS' with a value 'DONE' - all other tasks are interpreted as undone.  
   
This default, of attribute name - value pairs, are implemented in this way because :  
1. You can mix tasks and other nodes.(TYPE == TASK)
2. You can define your own names for type and status.
3. You can define your own values for task-identifier and done-value.
4. You can set this values in the first lines of the script to face your own needs and set your own attribute names
or values - perhaps you want to overtake them from other tools or way of thinking..  
  
**A node is identified as relevant iff an attribute with name [tType] exists and has the value [tTask].**  
**A relevant node is listed in output iff an attribute with name [tStatus] does not exist or has NOT the value [tDone]**.  
  
..going further : With this mechanismen you can manage many things - not only tasks..   
  
#### List due tasks  
  
**Idea :** If we manage some tasks and they have a due date - how can I get a report of tasks which are due or 
overdue ?  
  
**File :** *[ListDueTasks.groovy](./ListDueTasks.groovy)*  
  
**Task :** This script follows the structures which are explained in [List all tasks](#list-all-tasks).  
Additionally it cares about an attribute with the name which is set in 'tDueDate' (default 'DUE DATE') and
compares it - if a task is not done - with actual date.  
So the due and overdue tasks are found and reported in a similar way as in 'List all tasks'.  
  
**Remark :** You can set this attribute name (value of 'tDueDate') to your own needs in the beginning lines of the script.  
  
Nodes are listed in ouput when (both are true !) :  

1. Node is declared as a task and it's status is not done or doesn't exist.
2. Node has a due date and it is less or equal to today.
  
**Attention :** The value in attribute for 'DUE DATE' must follow ISO-8601 - 'yyyy-MM-dd' !
  
  
#### Create Tracking-ID  
  
**Idea :** For many use-cases it is important to manage different documents on your system - emails, protocols, 
contracts, pictures, media-files etc..  
Often it is difficult to keep an eye on all these things and to know which are relevant
for an entity (node) in your mindmap representing your business. Especially emails are hard to handle, because you can't
link them directly to your nodes.  
For all situations you don't want to or you can't link your messages, files etc. a so called 'Tracking-ID' is convenient.  
It is an ID - generated unique on your system - you can add to email-heades, filenames etc. and so the connection
to your node in Freeplane or to each other is represented.  
To find files which are not linked directly in your mindmap but signed with a Tracking-ID you can use the 
script [FindFilesWithTrackingID.groovy](./FindFilesWithTrackingID.groovy).  
  
**File :** *[CreateTrackingID.groovy](./CreateTrackingID.groovy)*  
  
**Task :** This script creates a unique Tracking-ID (Timestamp), copies it to Clipboard and asks for setting
an attribute with name 'TrID' and this value.  
  
**ATTENTION :**     Uncheck the 'Feature' to recognize date-formats automatically in Freeplanes configuration !  
Otherwise the value of the attribute  'TrID' will be converted to a date-format by insertion and it's getting
senseless.  
  
To use a date-time stamp as Tracking-ID offers an additional meaning to the user..

  
  
#### Find files with Tracking-ID  
  
**Idea :** Using a Trackin-ID (see [Create Trackin-ID](#create-tracking-id) is a useful concept to connect
files and emails to a node in your mindmap or to hold very different things together in a special context.  
If you are using this concept in your mindmap and renamed files or modified (e.g. email-headers) them, it 
is important to find them again across your system.  
OK - eamails are a very closed thing and you have to find them - via the Trackin-ID - in your email client.  
  
**File :** *[FindFilesWithTrackingID.groovy](./FindFilesWithTrackingID.groovy)*  
  
**Task :** If your node has an attribute with name 'TrID', this script will find all files containing
actual Tracking-ID (value of 'TrID') in their name. You can choose a folder to begin and it steps down
all existing subfolders.  
If there are files matching the Tracking-ID, you can add a child-node and link for each one in your mindmap.  
  
**Tip :** Don't start at your root folder - it'll take a long time..  ;-)  
Organize your projects useful.  

  
  
### Markdown  
  
#### Export branch to Markdown  
  
**Idea :** Freeplanes Markdown Export is not realy useful for my requirements. Missing header-characters,
no TOC (table of contents), loose of attributes and links a.s.o. makes it difficult for many use-cases.  
  
**File :** *[ExportBranchToMarkdown.groovy](./ExportBranchToMarkdown.groovy)*  
  
**Task :** This script exports a selected branch to Markdown (GitHub Flavored Markdown).  
You can choose if you want to create a table of contents (TOC). It recognizes attributes, links, 
pictures, a.s.o and you can choose which elements you want to copy to your Markdown file.  
The notes are copied as they are - so it makes sense to write them in 'Standard' or 'Markdown' Format.  
  
**Attention :** It is recommended to write your note as Markdown text - use content format 'standard' or
'markdown' (markdown is not alway rendered correctly).  
It makes no sense to do some formatting as HTML in your notes and export them to Markdown if you know how to
use it.  
  
LaTex is no problem - the notes are simply copied as ASCII and can be rendered in the same way as Markdown.  
  
***This README is written in Freeplane and simply exported to Markdown with this script.. ;-))***  
  
#### Import Markdown to node  
  
**Idea :** Many People writing their texts in Markdown because it is much more simple, portable and efficient
than a word processing application.  
To handle a text file with it's headings, chapters and paragraphs as a mindmap brings a lot of advantages.  
Some of them are the existing overview, high dynamic in ordering or splitting the chapters and paragraphs, etc..  
To realize an import of such files is - in hand with the existing export - a magnificent working environment
for all people who are working on text in any way.  
  
**File :** *[ImportMarkdownToNode.groovy](./ImportMarkdownToNode.groovy)*  
  
**Task :** This script imports a Markdown file - written in GFM (GitHub Flavored Markdown) - as subtree
of the actual node. Headings are mapped to nodes and their hirarchy, paragraphs copied unchanged to notes.  
A small documentation of the import with nodes (IDs) and hirarchy is written as a table to note of the importing node.  
  
**Attention :**  
- Metadata of the markdown file is written to the importing node.
- Reference-Links will be written to a create node 'Reference-Links'
  
It is recommended to set the values of 'contentType' and 'coreTextFormat' to your own needs - they define the type of 
notes and node-texts. The Defaults are settings for standard text although you're importing Markdown.  
This is the case because Freeplane's markdown rendering is not the best and you can't see reference links without
opening the note in an editor.  
For me it is more convenient to work with normal text and use an editor which can do a perfect rendering like 
[Ghostwriter](https://ghostwriter.kde.org/de/) or somthing similar.  
To use an external editor - see my script [Open note in external editor](#open-note-in-external-editor).
  
  
#### Open Markdown link in note  
  
**Idea :** While working with Freeplane as an authoring-system and creating Markdown notes, it was 
sometimes neccessary to check my written links in the text.  
It was very complicated to check all these links manually - especially when there are long 
lists of reference links.  
  
**File :** *[OpenMarkdownLinkInNote.groovy](./OpenMarkdownLinkInNote.groovy)*  
  
**Task :** This script offers a list of all Markdown links (reference or normal) you can choose from
and opens it - or open all at once if you want.  
  
Recognized links :  
  
1. \[*link_text*\](*link* "*title*") ..normal link with or without leading '!' or following title
2. \[!\[*link_text*\](*link* "*hover_text*")\](#*anchor_link*) ..image with a link and optional hover_text and anchor_link
3. \[*ref_text*\]: *link* "*title*" ..reference link with optional title
4. \[*ref_text*\]: <*link*> "*title*" ..reference link (angle brackets) with optional title
  
**Attention :* Take care about the single spaces in reference links and the double quotation marks !  
Keep an eye on your used 'absolute' or 'relative' paths.  
