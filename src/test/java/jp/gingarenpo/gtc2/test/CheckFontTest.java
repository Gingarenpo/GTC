package jp.gingarenpo.gtc2.test;

import java.awt.*;
import java.io.IOException;

/**
 * カスタムフォント使用時のチェックを行うテスト用ソースコードです。
 */
public class CheckFontTest {

	public static void main(String[] args) throws IOException, FontFormatException {
		// フォントを読み込んでFontオブジェクトを生成します
		Font font = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("fonts/1012.otf"));

		System.out.println("K12-345は" + ((font.canDisplayUpTo("K12-345") == -1) ? "表示できる" : "表示できない"));
		System.out.println("K12-345(ABC)は" + ((font.canDisplayUpTo("K12-345(ABC)") == -1) ? "表示できる" : "表示できない"));
	}
}
