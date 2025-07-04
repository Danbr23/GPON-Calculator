module GPON {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
//	opens model to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	
}
