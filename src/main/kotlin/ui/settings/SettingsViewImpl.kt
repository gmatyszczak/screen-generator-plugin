package ui.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.LanguageTextField
import data.repository.SettingsRepositoryImpl
import model.Category
import model.FileType
import model.ScreenElement
import ui.help.HelpDialog
import util.addTextChangeListener
import java.awt.event.ActionListener
import javax.swing.JComponent
import javax.swing.event.DocumentListener

class SettingsViewImpl(private val project: Project) : Configurable, SettingsView {

    private var panel: SettingsPanel? = null
    private val presenter = SettingsPresenter(this, SettingsRepositoryImpl(project))
    private var categoryNameDocumentListener: DocumentListener? = null
    private var screenElementNameDocumentListener: DocumentListener? = null
    private var templateDocumentListener: com.intellij.openapi.editor.event.DocumentListener? = null
    private var activityDocumentListener: DocumentListener? = null
    private var fragmentDocumentListener: DocumentListener? = null
    private var fileNameDocumentListener: DocumentListener? = null

    private val fileTypeActionListener: ActionListener = ActionListener {
        onPanel {
            presenter.onFileTypeSelect(FileType.values()[fileTypeComboBox.selectedIndex])
        }
    }

    private var currentTemplateTextField: LanguageTextField? = null
    private var currentSampleTextField: LanguageTextField? = null

    override fun isModified() = presenter.isModified

    override fun getDisplayName() = "Screen Generator Plugin"

    override fun apply() = presenter.onApplySettings()

    override fun reset() = presenter.onResetSettings()

    override fun createComponent(): JComponent? {
        panel = SettingsPanel(project)
        presenter.onLoadView()
        onPanel {
            create(presenter::onHelpClick)
            currentTemplateTextField = codePanel.kotlinTemplateTextField
            currentSampleTextField = codePanel.kotlinSampleTextField
        }
        presenter.onViewCreated()
        return panel
    }

    override fun setUpListeners() = onPanel {
        categoryToolbarDecorator.apply {
            setAddAction { presenter.onCategoryAddClick() }
            setRemoveAction { presenter.onCategoryDeleteClick(categoriesList.selectedIndex) }
            setMoveDownAction { presenter.onCategoryMoveDownClick(categoriesList.selectedIndex) }
            setMoveUpAction { presenter.onCategoryMoveUpClick(categoriesList.selectedIndex) }
        }
        screenElementToolbarDecorator.apply {
            setAddAction { presenter.onScreenElementAddClick() }
            setRemoveAction { presenter.onScreenElementDeleteClick(screenElementsList.selectedIndex) }
            setMoveDownAction { presenter.onScreenElementMoveDownClick(screenElementsList.selectedIndex) }
            setMoveUpAction { presenter.onScreenElementMoveUpClick(screenElementsList.selectedIndex) }
        }
        categoriesList.addListSelectionListener {
            if (!it.valueIsAdjusting) presenter.onCategorySelect(categoriesList.selectedIndex)
        }
        screenElementsList.addListSelectionListener {
            if (!it.valueIsAdjusting) presenter.onScreenElementSelect(screenElementsList.selectedIndex)
        }
    }

    override fun addCategory(category: Category) = onPanel {
        categoriesListModel.add(category)
    }

    override fun selectCategory(index: Int) = onPanel {
        categoriesList.selectedIndex = index
    }

    override fun updateCategory(index: Int, category: Category) = onPanel {
        categoriesListModel.setElementAt(category, index)
    }

    override fun removeCategory(index: Int) = onPanel { categoriesListModel.remove(index) }

    override fun showCategories(categories: List<Category>) = onPanel {
        categories.forEach { categoriesListModel.add(it) }
    }

    override fun clearCategories() = onPanel { categoriesListModel.removeAll() }

    override fun showCategoryName(name: String) = onPanel {
        categoryNameTextField.text = name
    }

    override fun addScreenElement(screenElement: ScreenElement) = onPanel {
        screenElementsListModel.add(screenElement)
    }

    override fun selectScreenElement(index: Int) = onPanel {
        screenElementsList.selectedIndex = index
    }

    override fun showScreenElementName(name: String) = onPanel {
        screenElementNameTextField.text = name
    }

    override fun addTextChangeListeners() = onPanel {
        categoryNameDocumentListener = categoryNameTextField.addTextChangeListener(presenter::onCategoryNameChange)
        screenElementNameDocumentListener = screenElementNameTextField.addTextChangeListener(presenter::onScreenElementNameChange)
        templateDocumentListener = currentTemplateTextField?.addTextChangeListener(presenter::onTemplateChange)
        fileNameDocumentListener = fileNameTextField.addTextChangeListener(presenter::onFileNameChange)
        fileTypeComboBox.addActionListener(fileTypeActionListener)
    }

    override fun removeTextChangeListeners() = onPanel {
        categoryNameDocumentListener?.let { categoryNameTextField.document.removeDocumentListener(it) }
        screenElementNameDocumentListener?.let { screenElementNameTextField.document.removeDocumentListener(it) }
        templateDocumentListener?.let { currentTemplateTextField?.document?.removeDocumentListener(it) }
        fileNameDocumentListener?.let { fileNameTextField.document.removeDocumentListener(it) }
        categoryNameDocumentListener = null
        screenElementNameDocumentListener = null
        templateDocumentListener = null
        fileNameDocumentListener = null
        fileTypeComboBox.removeActionListener(fileTypeActionListener)
    }

    override fun updateScreenElement(index: Int, screenElement: ScreenElement) = onPanel {
        screenElementsListModel.setElementAt(screenElement, index)
    }

    override fun removeScreenElement(index: Int) = onPanel { screenElementsListModel.remove(index) }

    override fun showScreenElements(screenElements: List<ScreenElement>) = onPanel {
        screenElementsListModel.removeAll()
        screenElements.forEach { screenElementsListModel.add(it) }
    }

    override fun clearScreenElements() = onPanel { screenElementsListModel.removeAll() }

    override fun showSampleCode(text: String) {
        currentSampleTextField?.text = text
    }

    override fun showTemplate(template: String) {
        currentTemplateTextField?.text = template
    }

    override fun showActivityBaseClass(text: String) = onPanel {
        activityTextField.text = text
    }

    override fun showFragmentBaseClass(text: String) = onPanel {
        fragmentTextField.text = text
    }

    override fun addBaseClassTextChangeListeners() = onPanel {
        activityDocumentListener = activityTextField.addTextChangeListener(presenter::onActivityBaseClassChange)
        fragmentDocumentListener = fragmentTextField.addTextChangeListener(presenter::onFragmentBaseClassChange)
    }

    override fun removeBaseClassTextChangeListeners() = onPanel {
        activityDocumentListener?.let { activityTextField.document.removeDocumentListener(it) }
        activityDocumentListener = null
        fragmentDocumentListener?.let { fragmentTextField.document.removeDocumentListener(it) }
        fragmentDocumentListener = null
    }

    override fun showFileType(fileType: FileType) = onPanel {
        fileTypeComboBox.selectedIndex = fileType.ordinal
    }

    override fun showCodeTextFields(fileType: FileType) = onPanel { codePanel.show(fileType) }

    override fun swapToKotlinTemplateListener(addListener: Boolean) = onPanel {
        templateDocumentListener?.let { currentTemplateTextField?.document?.removeDocumentListener(it) }
        currentTemplateTextField = codePanel.kotlinTemplateTextField
        currentSampleTextField = codePanel.kotlinSampleTextField
        if (addListener) templateDocumentListener = currentTemplateTextField?.addTextChangeListener(presenter::onTemplateChange)
    }

    override fun swapToXmlTemplateListener(addListener: Boolean) = onPanel {
        templateDocumentListener?.let { currentTemplateTextField?.document?.removeDocumentListener(it) }
        currentTemplateTextField = codePanel.xmlTemplateTextField
        currentSampleTextField = codePanel.xmlSampleTextField
        if (addListener) templateDocumentListener = currentTemplateTextField?.addTextChangeListener(presenter::onTemplateChange)
    }

    override fun showFileNameTemplate(text: String) = onPanel {
        fileNameTextField.text = text
    }

    override fun showFileNameSample(text: String) = onPanel {
        fileNameSampleLabel.text = text
    }

    override fun showHelp() = HelpDialog().show()

    override fun setScreenElementDetailsEnabled(isEnabled: Boolean) = onPanel { setScreenElementDetailsEnabled(isEnabled) }

    private inline fun onPanel(function: SettingsPanel.() -> Unit) = panel?.run { function() } ?: Unit
}