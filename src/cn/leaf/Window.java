package cn.leaf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Window extends Application {

	private static final String TITLE="Ubuntu图标生成器";
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle(TITLE);
		s.setWidth(500);
		s.setHeight(600);
		s.setMaxHeight(1000);
		s.setMaxWidth(1000);
		s.getIcons().add(new Image(Window.class.getClassLoader().getResource("ubuntu.png").toString()));
		var loader=new FXMLLoader(Window.class.getClassLoader().getResource("UI.fxml"));
		var scene=new Scene(loader.load());
		s.setScene(scene);
		s.show();
	}

}
