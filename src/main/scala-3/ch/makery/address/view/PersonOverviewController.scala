package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.scene.control.TextField
import ch.makery.address.util.DateUtil.*
import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@FXML
class PersonOverviewController():
  @FXML
  private var personTable: TableView[Person] = null // alias fxml will inject this variable when it is trying to load the fxml file
  @FXML
  private var firstNameColumn: TableColumn[Person, String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String] = null
  @FXML
  private var firstNameLabel: Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null
  @FXML
  private var mytext: TextField = null

  // initialize Table View display contents model
  def initialize() =
    personTable.items = MainApp.personData
    // initialize columns's cell values
    firstNameColumn.cellValueFactory = {x => x.value.firstName}
    lastNameColumn.cellValueFactory  = {x => x.value.lastName}

    firstNameLabel.text <== mytext.text // bind text property of firstNameLabel to mytext TextField

    showPersonDetails(None)
    // only called once when the controller is initialized, not every time the view is shown
    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue)) // newValue is the selected person, if no person is selected, it will be None
    )

//cannot be singleton object, must be class
// fxml loader create object

  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) => //person is a variable (abstract value of the Some object), Some has one value which can be assigned to person
        // Fill the labels with info from the person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city;
        postalCodeLabel.text <== person.postalCode.delegate.asString() //delegate is used to access the underlying JavaFX property (binding)
        // cannot write as person.birthdayLabel.delegate.asString() because we want our customised date format
        birthdayLabel.text <== Bindings.createStringBinding(() => {
          person.date.value.asString
        }, person.date)
      // string object only stores value, string property has one value (can binding)
      //string property has string object inside

      case None =>
        // Person is null, remove all the text.
        firstNameLabel.text.unbind()
        lastNameLabel.text.unbind()
        streetLabel.text.unbind()
        postalCodeLabel.text.unbind()
        cityLabel.text.unbind()
        birthdayLabel.text.unbind()

        firstNameLabel.text = ""
        lastNameLabel.text = ""
        streetLabel.text = ""
        postalCodeLabel.text = ""
        cityLabel.text = ""
        birthdayLabel.text = ""

  @FXML
  def handleDeletePerson(action: ActionEvent) =
    val selectedIndex = personTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) then
      MainApp.personData.remove(selectedIndex).delete()
    else
      // Nothing selected.
      val alert = new Alert(AlertType.Error):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      .showAndWait()

  @FXML
  def handleNewPerson(action: ActionEvent) =
    val person = new Person("", "")
    val okClicked = MainApp.showPersonEditDialog(person);
    if (okClicked) then {
      MainApp.personData += person
      person.save()
    }


  @FXML
  def handleEditPerson(action: ActionEvent) =
    val selectedPerson = personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) then
      val okClicked = MainApp.showPersonEditDialog(selectedPerson)

      if (okClicked) then
        showPersonDetails(Some(selectedPerson))
        selectedPerson.save()

    else
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      .showAndWait()



