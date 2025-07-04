// @ExecutionModes({ON_SELECTED_NODE})

// author : Markus Seilnacht
// date : 2025-07-02
// (c) licensed under GPL-3.0 or later


/*
    This script finds all tasks (atribute tType == 'TASK') which are due today or former.
    if they have a tStatus-attribute == 'DONE' they are excluded..

    It creates a child with direct link to each found task.

    Attention : The value for 'DUE DATE' have to follow ISO-8601 ('yyyy-MM-dd')
*/

/*
*/

import java.time.LocalDate

final lf = System.lineSeparator()

def sb = new StringBuilder()
def int counter = 0

// defines which attribute is signed as task
final String tType = "TYPE"
final String tTask = "TASK"
// defines which attribute is checked for status and Done-Value
final String tStatus = "STATUS"
final String tDone = "DONE"
// checks this attribute against today
final tDueDate = "DUE DATE"

LocalDate now = LocalDate.now()

taskNode = node.createChild()
taskNode.format = "markdownPatternFormat"
taskNode.setNoteContentType("markdown")
//tasks.setNoteContentType("auto")
//taskNode.setFree(true)

//taskNode.style.setFloating(true)
taskNode.style.setBackgroundColorCode("#FFFF00")
taskNode.style.setTextColorCode("#333333")

// set timestamp
taskNode.text = "**Tasklist (due today) " + '**'
taskNode.attributes = ["Date" : format(new Date(), "yyyy-MM-dd  HH:mm:ss")]
//taskNode.attributes = ["Date" : now.toString()]
taskNode.attributes.optimizeWidths()

sb << '### Tasklist  ' << lf
sb << '*(' << tDueDate << ' <= ' << now.toString() << ' && ' << tStatus << ' != ' << tDone << ')*  ' << lf
sb << '  ' << lf

//node.findAll().each {
for (child in node.findAll()) {
    dueVal = child.attributes.getTransformed().getFirst(tDueDate)
    isTask = (child.attributes.getFirst(tType) == tTask) ? true : false
    // check actual status
    // child.attributes.getFirst(tStatus) returns NULL if attribute does not exist 
    // => NULL != tDone - task withaout a tStatus are recognized !
    // getTransformed().getFirst(..) - value after evaluation of formulas
    status = child.attributes.getTransformed().getFirst(tStatus)

    if (!dueVal || !isTask) continue
    dueDate = LocalDate.parse(dueVal)

    if (dueDate.compareTo(now) <= 0) {
        if (status != tDone) {
            counter = counter + 1
            // write note-text
            sb << '  ' << lf << '*Task(' << counter.toString() << ') :* **' << child.text << '**  ' << lf
            // Create child with link for each open task in its color
            newChild = taskNode.createChild()
            newChild.style.setBackgroundColorCode(child.style.getBackgroundColorCode())
            newChild.style.setTextColorCode(child.style.getTextColorCode())
            newChild.format = "markdownPatternFormat"
            newChild.text = '**' + child.text + '**'
            newChild.link.text = '#' + child.id
        }
    }
}

sb << '  ' << lf
sb << '  ' << lf << '..found ' << counter.toString() << ' Task(s).  ' << lf
taskNode.attributes.add("Task(s)", counter.toString())

taskNode.note = sb.toString()
taskNode.setFolded(true)

c.select(taskNode)