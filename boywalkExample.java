package application;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class boywalkExample extends Application {
	public static void main(String[] args){
	          launch(args);
	}

	public void start(Stage theStage){
        theStage.setTitle( "boywalkExample" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        ArrayList<String> input = new ArrayList<String>(); //Stringを保存するArrayList「input」の作成

        //KeyPressedのイベントハンドラをSceneに登録（コンビニエンスメソッド方式）　
        //使い方は、.setで該当するイベントタイプのメソッドを呼び出し、引数ににハンドラをぶちこむだけ
        theScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() { //ハンドラの生成、<>にはEventの種類を入れる
                public void handle(KeyEvent e){ //KeyEventが発生したら呼び出される
                    String code = e.getCode().toString(); //押されたキーのコードをストリング型に変換して取得する

                    // only add once... prevent duplicates
                    if ( !input.contains(code) ){ //もし「input」になにもcodeが入っていなかったら
                    	input.add( code ); //「input」にcodeを追加
                    }
                }
            }
        );

        //ラムダ式記述
        theScene.setOnKeyReleased(
                (KeyEvent e) ->{
                         String code = e.getCode().toString();
                         input.remove( code );
                }
        );

        /* notラムダ式　ラムダと比べるとやっぱり冗長
        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>(){
                public void handle(KeyEvent e){
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            }
        );
        */

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image stand = new Image( new File("src/boy-stand-front.png").toURI().toString(), 50, 50, true, true );

        Image left = new Image( new File("src/boy-walk-left-R.png").toURI().toString(), 55, 55, true, true );
        Image left2 = new Image( new File("src/boy-walk-left-L.png").toURI().toString(), 55, 55, true, true );
        Image left3 = new Image( new File("src/boy-stand-left.png").toURI().toString(), 55, 55, true, true );
        Image[] leftwalk = new Image[30];
        leftwalk[0] = left;
        leftwalk[1] = left3;
        leftwalk[2] = left2;

        Image right = new Image( new File("src/boy-walk-right-R.png").toURI().toString(), 50, 50, true, true );
        //Image right2 = new Image( new File("src/boy-walk-right-L.png").toURI().toString(), 50, 50, true, true );
        //Image right3 = new Image( new File("src/boy-stand-right-L.png").toURI().toString(), 50, 50, true, true );

        Image up = new Image( new File("src/boy-walk-back-R.png").toURI().toString(), 50, 50, true, true );
        //Image up2 = new Image( new File("src/boy-walk-back-L.png").toURI().toString(), 50, 50, true, true );
        //Image up3 = new Image( new File("src/boy-stand-back.png").toURI().toString(), 50, 50, true, true );

        Image down = new Image( new File("src/boy-walk-front-R.png").toURI().toString(), 50,50, true, true );
        //Image down2 = new Image( new File("src/boy-walk-front-L.png").toURI().toString(), 50,50, true, true );

        AnimationTimer anitime =
        new AnimationTimer(){ //AnimationTimerは常に動いており、このなかのhandle内に書いてあることを常に反映する

            public void handle(long currentNanoTime ) {
            	//Canvasの描画をクリア
                gc.clearRect(0, 0, 512, 512);

                int number = (int) ( currentNanoTime % 3);

                if (input.contains("LEFT")){
                	gc.drawImage( leftwalk[number], 64, 64 );
                	gc.strokeText("currentNanoTimeは"+currentNanoTime, 100, 100);
                }
                else if(input.contains("RIGHT")){
                	gc.drawImage( right, 64, 64 );
                }
                else if(input.contains("UP")){
                	gc.drawImage( up, 64, 64 );
                }
                else if(input.contains("DOWN")){
                	gc.drawImage( down, 64, 64 );
                }
                else{
                	gc.drawImage( stand, 64, 64 );
                }
            }
        };

        anitime.start();

        theStage.show();
    }
}
