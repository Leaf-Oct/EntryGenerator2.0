package cn.leaf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class WindowController {

	@FXML
	private Button choose_file;

	@FXML
	private Button choose_icon;

	@FXML
	private TextField comment_text;

	@FXML
	private TextField exec_text;

	@FXML
	private Button export_button;

	@FXML
	private CheckBox has_comment_check;

	@FXML
	private CheckBox has_icon_check;

	@FXML
	private TextField icon_text;

	@FXML
	private Button install_button;

	@FXML
	private TextField name_text;

	@FXML
	private VBox root_layout;

	@FXML
	private CheckBox terminal_check;

	@FXML
	private TextField type_text;

	@FXML
	void chooseExecFile(ActionEvent event) {
		System.out.println("choose file");
		var file_chooser = new FileChooser();
		var file = file_chooser.showOpenDialog(root_layout.getScene().getWindow());
		if (file != null) {
			exec_text.setText(file.getAbsolutePath());
		}
	}

	@FXML
	void chooseIconFile(ActionEvent event) {
		System.out.println("choose icon");
		var file_chooser = new FileChooser();
		file_chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("picture", "*.jpg", "*.png", "*.svg", "*.xpm", "*.ico"),
				new FileChooser.ExtensionFilter("All", "*.*"));
		var file = file_chooser.showOpenDialog(root_layout.getScene().getWindow());
		if (file != null) {
			icon_text.setText(file.getAbsolutePath());
		}
	}

//	暂时不可用，待解决
	@FXML
	void copyFileToUsrShareApplication(ActionEvent event) {
		var sb = new StringBuffer("[Desktop Entry]\n");
		var name = "Name=" + name_text.getText() + "\n";
		var exec = "Exec=" + exec_text.getText() + "\n";
		var terminal = "Terminal=" + (terminal_check.isSelected() ? "True" : "False") + "\n";
		var type = "Type=" + type_text.getText() + "\n";
		sb.append(name);
		sb.append(exec);
		sb.append(terminal);
		sb.append(type);
		if (has_icon_check.isSelected()) {
			sb.append("Icon=" + icon_text.getText() + "\n");
		}
		if (has_comment_check.isSelected()) {
			sb.append("Comment=" + comment_text.getText() + "\n");
		}
		try {
			var f=File.createTempFile("tmp_"+name_text.getText(), ".desktop");
			var bw=new BufferedWriter(new FileWriter(f));
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			var command="pkexec mv "+f.getAbsolutePath()+" /usr/share/applications/"+name_text.getText()+".desktop\r\n";
			System.out.println(command);
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			var error_window=new Stage();
			error_window.initOwner(root_layout.getScene().getWindow());
			var error_message=new Label("写入临时文件失败");
			var group=new Group();
			group.getChildren().add(error_message);
			error_window.setScene(new Scene(group));
			error_window.show();
			return;
		}
		
		
	}

	@FXML
	void exportEntryFile(ActionEvent event) {
		var sb = new StringBuffer("[Desktop Entry]\n");
		var name = "Name=" + name_text.getText() + "\n";
		var exec = "Exec=" + exec_text.getText() + "\n";
		var terminal = "Terminal=" + (terminal_check.isSelected() ? "True" : "False") + "\n";
		var type = "Type=" + type_text.getText() + "\n";
		sb.append(name);
		sb.append(exec);
		sb.append(terminal);
		sb.append(type);
		if (has_icon_check.isSelected()) {
			sb.append("Icon=" + icon_text.getText() + "\n");
		}
		if (has_comment_check.isSelected()) {
			sb.append("Comment=" + comment_text.getText() + "\n");
		}
//    	System.out.println(sb.toString());
		var file_chooser = new FileChooser();
		file_chooser.setInitialFileName(name_text.getText() + ".desktop");
		var file = file_chooser.showSaveDialog(root_layout.getScene().getWindow());
		if (file != null) {
			try (var bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(sb.toString());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
