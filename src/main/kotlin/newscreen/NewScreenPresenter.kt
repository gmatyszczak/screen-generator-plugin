package newscreen

class NewScreenPresenter(private val view: NewScreenView,
                         private val fileCreator: FileCreator) {

    fun onOkClick(screenName: String) {
        fileCreator.createScreenFiles(screenName)
        view.close()
    }
}