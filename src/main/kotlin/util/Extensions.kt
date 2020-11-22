package util

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.LanguageTextField
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun JTextField.addTextChangeListener(onChange: (String) -> Unit) =
        object : DocumentListener {
            override fun changedUpdate(e: DocumentEvent?) = onChange(text)
            override fun insertUpdate(e: DocumentEvent?) = onChange(text)
            override fun removeUpdate(e: DocumentEvent?) = onChange(text)
        }.apply { document.addDocumentListener(this) }

fun LanguageTextField.addTextChangeListener(onChange: (String) -> Unit) =
        object : com.intellij.openapi.editor.event.DocumentListener {
            override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent) = onChange(text)
        }.apply { document.addDocumentListener(this) }

fun String.toSnakeCase() = replace(Regex("([^_A-Z])([A-Z])"), "$1_$2").toLowerCase()

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun JTextField.updateText(newText: String) {
    if (text != newText) {
        text = newText
    }
}

fun JLabel.updateText(newText: String) {
    if (text != newText) {
        text = newText
    }
}

fun <E> ComboBox<E>.selectIndex(newIndex: Int) {
    if (selectedIndex != newIndex) {
        selectedIndex = newIndex
    }
}
fun LanguageTextField.updateText(newText: String) {
    if (text != newText) {
        text = newText
    }
}