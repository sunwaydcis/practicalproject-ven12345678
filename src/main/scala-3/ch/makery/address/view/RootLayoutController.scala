package ch.makery.address.view

import ch.makery.address.MainApp
import javafx.event.ActionEvent
import javafx.fxml.FXML

@FXML
class RootLayoutController():
  
  @FXML
  def handleClose(action: ActionEvent): Unit = 
    MainApp.stage.close()
    
  @FXML
  def handleDelete(action: ActionEvent): Unit =
    MainApp.personOverviewController.map(x => x.handleDeletePerson(action))
  
  // This controller is just the root layout controller.
  // It doesn't do anything, but it is needed to be able to
  // load the fxml file with the root layout.
  // The actual functionality is in the PersonOverviewController.
  // The FXML loader will inject the root layout into this controller.
  // So we don't need to do anything here.
  // This class is just a placeholder for the FXML loader.