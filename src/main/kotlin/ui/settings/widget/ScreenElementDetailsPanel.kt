package ui.settings.widget

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.IdeBorderFactory
import model.Anchor
import model.AnchorPosition
import model.AndroidComponent
import model.FileType
import model.ScreenElementType
import ui.settings.SettingsState
import util.addTextChangeListener
import util.selectIndex
import util.updateItems
import util.updateText
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class ScreenElementDetailsPanel : JPanel() {

    var onNameTextChanged: ((String) -> Unit)? = null
    var onFileNameTextChanged: ((String) -> Unit)? = null
    var onSubdirectoryTextChanged: ((String) -> Unit)? = null
    var onSourceSetTextChanged: ((String) -> Unit)? = null
    var onFileTypeIndexChanged: ((Int) -> Unit)? = null
    var onAndroidComponentIndexChanged: ((Int) -> Unit)? = null
    var onTypeIndexChanged: ((Int) -> Unit)? = null
    var onAnchorIndexChanged: ((Int) -> Unit)? = null
    var onAnchorPositionIndexChanged: ((Int) -> Unit)? = null
    var onAnchorNameTextChanged: ((String) -> Unit)? = null

    private val nameTextField = JTextField()
    private val fileTypeComboBox = ComboBox(FileType.values())
    private val fileNameTextField = JTextField()
    private val fileNameSampleLabel = JLabel().apply { isEnabled = false }
    private val screenElementNameLabel = JLabel("Screen Element Name:")
    private val fileNameLabel = JLabel("File Name:")
    private val fileTypeLabel = JLabel("File Type:")
    private val typeLabel = JLabel("Type:")
    private val typeComboBox = ComboBox(ScreenElementType.values())
    private val androidComponentLabel = JLabel("Related Android Component:")
    private val androidComponentComboBox = ComboBox(AndroidComponent.values())
    private val anchorLabel = JLabel("Anchor:")
    private val anchorComboBox = ComboBox(emptyArray<Anchor>())
    private val anchorPositionLabel = JLabel("Anchor Position:")
    private val anchorPositionComboBox = ComboBox(AnchorPosition.values())
    private val anchorNameLabel = JLabel("Anchor Name:")
    private val anchorNameTextField = JTextField()

    private val subdirectoryLabel = JLabel("Subdirectory:")
    private val subdirectoryTextField = JTextField()

    private val sourceSetLabel = JLabel("Source Set:")
    private val sourceSetTextField = JTextField()

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Screen Element Details", false)
        layout = GridBagLayout()
        add(screenElementNameLabel, constraintsLeft(0, 0))
        add(nameTextField, constraintsRight(1, 0))
        add(typeLabel, constraintsLeft(0, 1))
        add(
            typeComboBox,
            constraintsRight(1, 1).apply {
                fill = GridBagConstraints.NONE
                anchor = GridBagConstraints.LINE_START
            }
        )
        add(fileNameLabel, constraintsLeft(0, 2))
        add(fileNameTextField, constraintsRight(1, 2))
        add(fileTypeLabel, constraintsLeft(0, 3))
        add(
            fileTypeComboBox,
            constraintsRight(1, 3).apply {
                gridwidth = 1
                fill = GridBagConstraints.NONE
                anchor = GridBagConstraints.LINE_START
            }
        )
        add(
            fileNameSampleLabel,
            constraintsRight(2, 3).apply {
                gridwidth = 1
                weightx /= 2
                anchor = GridBagConstraints.LINE_END
                fill = GridBagConstraints.NONE
            }
        )
        add(androidComponentLabel, constraintsLeft(0, 4))
        add(
            androidComponentComboBox,
            constraintsRight(1, 4).apply {
                fill = GridBagConstraints.NONE
                anchor = GridBagConstraints.LINE_START
            }
        )
        add(subdirectoryLabel, constraintsLeft(0, 5))
        add(subdirectoryTextField, constraintsRight(1, 5))
        add(sourceSetLabel, constraintsLeft(0, 6))
        add(sourceSetTextField, constraintsRight(1, 6))
        add(anchorLabel, constraintsLeft(0, 7))
        add(
            anchorComboBox,
            constraintsRight(1, 7).apply {
                fill = GridBagConstraints.NONE
                anchor = GridBagConstraints.LINE_START
            }
        )
        add(anchorPositionLabel, constraintsLeft(0, 8))
        add(
            anchorPositionComboBox,
            constraintsRight(1, 8).apply {
                fill = GridBagConstraints.NONE
                anchor = GridBagConstraints.LINE_START
            }
        )
        add(anchorNameLabel, constraintsLeft(0, 9))
        add(anchorNameTextField, constraintsRight(1, 9))

        nameTextField.addTextChangeListener { if (!listenersBlocked) onNameTextChanged?.invoke(it) }
        fileNameTextField.addTextChangeListener { if (!listenersBlocked) onFileNameTextChanged?.invoke(it) }
        subdirectoryTextField.addTextChangeListener { if (!listenersBlocked) onSubdirectoryTextChanged?.invoke(it) }
        sourceSetTextField.addTextChangeListener { if (!listenersBlocked) onSourceSetTextChanged?.invoke(it) }
        fileTypeComboBox.addActionListener { if (!listenersBlocked) onFileTypeIndexChanged?.invoke(fileTypeComboBox.selectedIndex) }
        androidComponentComboBox.addActionListener {
            if (!listenersBlocked) onAndroidComponentIndexChanged?.invoke(androidComponentComboBox.selectedIndex)
        }
        typeComboBox.addActionListener {
            if (!listenersBlocked) onTypeIndexChanged?.invoke(typeComboBox.selectedIndex)
        }
        anchorComboBox.addActionListener {
            if (!listenersBlocked) onAnchorIndexChanged?.invoke(anchorComboBox.selectedIndex)
        }
        anchorPositionComboBox.addActionListener {
            if (!listenersBlocked) onAnchorPositionIndexChanged?.invoke(anchorPositionComboBox.selectedIndex)
        }
        anchorNameTextField.addTextChangeListener { if (!listenersBlocked) onAnchorNameTextChanged?.invoke(it) }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedElement = state.selectedElement
        val isFileModification = selectedElement?.type == ScreenElementType.FILE_MODIFICATION
        nameTextField.updateText(selectedElement?.name ?: "")
        fileTypeComboBox.selectIndex(selectedElement?.fileType?.ordinal ?: FileType.KOTLIN.ordinal)
        fileNameTextField.updateText(selectedElement?.fileNameTemplate ?: "")
        fileNameSampleLabel.updateText(state.fileNameRendered)
        androidComponentComboBox.selectIndex(
            state.selectedElement?.relatedAndroidComponent?.ordinal ?: AndroidComponent.NONE.ordinal
        )
        subdirectoryTextField.updateText(selectedElement?.subdirectory ?: "")
        sourceSetTextField.updateText(selectedElement?.sourceSet ?: "")
        typeComboBox.selectIndex(selectedElement?.type?.ordinal ?: ScreenElementType.NEW_FILE.ordinal)
        anchorComboBox.isVisible = isFileModification
        anchorLabel.isVisible = isFileModification
        anchorComboBox.updateItems(selectedElement?.fileType?.anchors ?: emptyList())
        anchorComboBox.selectIndex(selectedElement?.anchor?.ordinal ?: Anchor.FILE.ordinal)
        anchorPositionComboBox.isVisible = isFileModification
        anchorPositionLabel.isVisible = isFileModification
        anchorPositionComboBox.selectIndex(selectedElement?.anchorPosition?.ordinal ?: AnchorPosition.TOP.ordinal)
        anchorNameLabel.isVisible = isFileModification
        anchorNameTextField.isVisible = isFileModification
        anchorNameTextField.updateText(selectedElement?.anchorName ?: "")

        isEnabled = selectedElement != null
        components.filter { it != fileNameSampleLabel }.forEach { it.isEnabled = selectedElement != null }
        listenersBlocked = false
    }
}
