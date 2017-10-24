package application;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		//rootノードの作成
		Group root = new Group();

		//Sceneの作成＆rootノードにセット
		Scene scene = new Scene(root,400,400); //後半の引数はウィンドウの大きさ
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		//ノードの作成(Canvas)
		Canvas canvas = new Canvas( 512, 512 );
		root.getChildren().add( canvas ); //rootにセット
		GraphicsContext gc = canvas.getGraphicsContext2D();

		//ノードの作成(円)
		Circle c = new Circle(200, 100, 50); //引数は「座標」と「半径の大きさ」
		c.setFill(Color.AQUA);
		root.getChildren().add(c); //leafノードをrootノードにセット

		//ノードの作成(テキスト)
		Text t = new Text(40,110,"Hello,World!"); //引数は「座標」と「本文」
		t.setStroke(Color.BLACK);
		t.setStrokeWidth(1);
		t.setFill(Color.ALICEBLUE);
		t.setFont(Font.font("MEIRYO",FontWeight.EXTRA_BOLD,50)); //フォントの指定　今回はMS明朝
		root.getChildren().add(t); //leafノードをrootノードにセット

		//テキスト色変えアニメーション作成
		FillTransition fill = new FillTransition(Duration.millis(750));
	    fill.setToValue(Color.CORAL);
	    //アニメーション同時進行設定（今回はアニメーションは色変えの一つだから意味なし？？）
	    ParallelTransition transition = new ParallelTransition(t,fill); //引数に複数のアニメーション
	    transition.setCycleCount(Timeline.INDEFINITE); //アニメーション無限繰り返し設定
	    transition.setAutoReverse(true); //アニメーション巻き戻し設定
	    transition.play(); //アニメーション起動

	    //ノード作成（画像、チョコボ）
	    Image chocobo = new Image( new File("src/chocobo.gif").toURI().toString() );
	    ImageView chocobov = new ImageView(chocobo);
	    chocobov.setX(120); //X座標設定
	    chocobov.setY(230); //Y座標設定
	    root.getChildren().add(chocobov); //leafノードをrootノードにセット

	    //チョコボジャンプアニメーション作成
	    TranslateTransition tt = new TranslateTransition(Duration.millis(100), chocobov);
		tt.setByY(-100);
		tt.setCycleCount(6); //アニメーション6回繰り返し設定
		tt.setAutoReverse(true);
	    tt.play();

		//StageにSceneをセット
		primaryStage.setScene(scene);
		primaryStage.setTitle("RPG"); //stageにタイトルを入れる

		primaryStage.show(); //描写

		new AnimationTimer() {
		    final long startNanoTime = System.nanoTime();

	        public void handle(long currentNanoTime) {
	            double t = (currentNanoTime - startNanoTime) / 1000000000.0;
	            double x = 232 + 128 * Math.cos(t);
	            double y = 232 + 128 * Math.sin(t);
	            gc.drawImage(chocobo, x, y);
	            gc.clearRect(512-x, 512-y, 100, 100);
	        }
		}.start();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
