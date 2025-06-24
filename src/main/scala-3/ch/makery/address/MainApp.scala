package ch.makery.address

import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import ch.makery.address.model.Person

object MainApp extends JFXApp3:

  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None

  /* The data as an observable list of Persons.
  */
  val personData = new ObservableBuffer[Person]() //anything that extends ObservableBuffer can use binding
  /* Constructor */
  personData += new Person("Hans", "Muster") // add one value +=, multiple values ++=
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")
  
  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])
    // if u pass in some value, it will return "Some(value)", otherwise it will return "None"

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get

    // call to display PersonOverview when app start
    showPersonOverview()
  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots

  /**
   * publisher
   * - data
   * subscriber
   * - require data
   */
  
  val stringA = new StringProperty("hello") //publisher
  val stringB = new StringProperty("sunway") //subscriber
  val stringC = new StringProperty("sunway") //subscriber
  
  stringA.value = "world"
  stringB <==> stringA //biconditional - value binding from stringA to stringB everytime stringA changes and vice versa
  stringC <== stringA //value binding from stringA to stringB everytime stringA changes
  stringB.value = "google"
  println(stringB()) // prints "hello"

  stringA.onChange{ (_, oldValue, newValue) =>
    println(s"stringA changed from $oldValue to $newValue")
  }

  stringA.onChange { (_, _, _) =>
    println(s"stringA has changed")
  }
  
  stringA.value = "world"