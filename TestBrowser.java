
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextFieldBuilder;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ComboBox;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;


public class TestBrowser extends Application {

	 private Scene scene;
	 final ComboBox comboBox = new ComboBox();
	 final WebView smallView = new WebView();
	    final WebEngine webE = smallView.getEngine();
	 
	    @Override public void start(Stage stage) {
	
	   	    final WebView browser = new WebView();
	   	    final WebEngine webEngine = browser.getEngine();

	        
	   	 // apply CSS style
	       
	    	ScrollPane scrollPane = new ScrollPane();
	        scrollPane.setContent(browser);
	        // create scene
	       //
	      //testcode
		    	 VBox vbox = new VBox(15);
		 		vbox.setAlignment(Pos.CENTER_LEFT);
		 		HBox hbox = new HBox(20);
		 		hbox.setAlignment(Pos.CENTER_LEFT);
		 		TextField address = new TextField();
		 		hbox.setSpacing(10);
		 		address.setPrefColumnCount(70);
		 		Button button = new Button("Load");		
		 		hbox.getChildren().add(new Label("URL:"));
		 		hbox.getChildren().addAll(address, button);
		 		vbox.getChildren().add(hbox);
		 		vbox.getChildren().add(browser);
		 		

		 		button.setOnAction(buttonAction(address,webEngine, browser));
			//end
		 		 
		 
		 		 
			 	 final String[] captions = new String[]{
				        "Other",
				        "TestPage",
				        "Documentation",
				        "Help"
				    };
				 
				   final String[] urls = new String[]{
				        "http://www.youtube.com",
				        "file:///C:/Browser/src/temp.html",
				        "http://www.deakin.edu.au",
				        "file:///C:/Browser/src/help.html"
				    };
			    	
			    	
			       
				 	 final Hyperlink[] hpls = new Hyperlink[captions.length];

			       
			       // getStyleClass().add("browser");
			        
			        for (int i = 0; i < captions.length; i++) {
			            final Hyperlink hpl = hpls[i] = new Hyperlink(captions[i]);
			           
			            final String url = urls[i];
			 
			            hpl.setOnAction(new EventHandler<ActionEvent>() {
			                @Override
			                public void handle(ActionEvent e) {
			                    webEngine.load(url);   
			                }
			            });
			        }        
			 
			// load the home page        
			        webEngine.load("file:///C:/Browser/src/textilate/homepage.html");
			       
			        final HBox toolBar;
		 		// create the toolbar
			        toolBar = new HBox();
			        toolBar.getStyleClass().add("browser-toolbar");
			        toolBar.getChildren().addAll(hpls);  
			        
			        
			        
			        
				 	  

			        comboBox.setPrefWidth(60);  
					  //process history
				        final WebHistory history = webEngine.getHistory();
				        history.getEntries().addListener(new 
				            ListChangeListener<WebHistory.Entry>(){
				                @Override
				                public void onChanged(Change<?extends Entry> c) {
				                    c.next();
				                    for (Entry e : c.getRemoved()) {
				                        comboBox.getItems().remove(e.getUrl());
				                    }
				                    for (Entry e : c.getAddedSubList()) {
				                        comboBox.getItems().add(e.getUrl());
				                    }
				               }
				       });
				        

				      //set the behavior for the history combobox                
				        comboBox.setOnAction(new EventHandler<ActionEvent>(){
				            @Override
				            public void handle(ActionEvent ev) {
				                int offset =
				                        comboBox.getSelectionModel().getSelectedIndex()
				                        - history.getCurrentIndex();
				                history.go(offset);
				            }
				        });
				  	  
				        //handle popup windows
				        smallView.setPrefSize(220,80);
				        webEngine.setCreatePopupHandler(
				            new Callback<PopupFeatures,WebEngine>() {
				                @Override public WebEngine call(PopupFeatures config) {
				                    smallView.setFontScale(0.8);
				                    if (!toolBar.getChildren().contains(smallView)) {
				                        toolBar.getChildren().add(smallView);
				                   }
				                    return smallView.getEngine();
				                }
				             }
				        );
				        
			        
			    
			        //add components
			      //  getChildren().add(toolBar);
			       // getChildren().add(browser); 
			        toolBar.getChildren().add(comboBox);
		 		Scene scene = new Scene(vbox);
		 		vbox.getChildren().add(toolBar);
		 		// scene = new Scene(new Browser(address, webEngine, browser),750,500, Color.web("#666970"));
		 		stage.setScene(scene);
				stage.setTitle("Linky");
				stage.show();   

	        
	    }
	
		private EventHandler<ActionEvent> buttonAction(TextField address, WebEngine webEngine, WebView browser) {
			return new EventHandler<ActionEvent>() {			
				@Override
				public void handle(ActionEvent event) {
					String route = address.getText();
					System.out.println("Loading route: " + route);
					//progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
					webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
						@Override
						public void changed(ObservableValue<? extends State> value,
								State oldState, State newState) {
							if(newState == State.SUCCEEDED){
								System.out.println("Location loaded + " + webEngine.getLocation());
							}
						}
					});
					webEngine.load(route);
				
				}
			};
		}

 		;

		
		public static void main(String[] args){
	        launch(args);
	    }
		
		public class Browser extends Region{
			
			
			   
		    public Browser(final HBox toolBar, final WebEngine webEngine,
					final WebView browser) {  
		  
		        double w = getWidth();
		        double h = getHeight();
		        double tbHeight = toolBar.prefHeight(w);
		        layoutInArea(browser,0,0,w,h-tbHeight,0, HPos.CENTER, VPos.CENTER);
		        layoutInArea(toolBar,0,h-tbHeight,w,tbHeight,0,HPos.CENTER,VPos.CENTER);
			
		     
		    } //browser method ends
		 
		   // private Node createSpacer() {    Region spacer = new Region();HBox.setHgrow(spacer, Priority.ALWAYS);  return spacer;
	      
	        
	    
		    
		    
		
		    
		   
		 
		
		 
		       
		    
		 
		    @Override protected double computePrefWidth(double height) {
		        return 750;
		    }
		 
		    @Override protected double computePrefHeight(double width) {
		        return 500;
		    }
		    
		    
		    
		
		} //class browser ends
		
} //class testbrowser ends


	