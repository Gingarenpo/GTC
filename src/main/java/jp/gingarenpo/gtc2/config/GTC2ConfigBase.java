package jp.gingarenpo.gtc2.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * モデルパックJSONデータで指定するためのものです。RTMを参考にしていますが独自のパラメーターもいくつかあります。
 * ここでは共通仕様を定義しています。抽象クラスなので、インスタンスは生成できません。
 * Jacksonライブラリを使用しています。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GTC2ConfigBase {

	/**
	 * ID（必須）。文字列で、かつ英数字＋記号を受け付けます。日本語は文字コードの都合上動作不安定になることが多いので使用しない方が吉です。
	 * 文字数に制限はありません。被るとエラーを吐くことがあります。
	 */
	public String id;

	/**
	 * 名前（任意）。この名前も日本語使えますがあまり使わない方がいいかと思います。サンプルが動かないようでしたら英語で入力しましょう。
	 * 将来的には多言語対応させたいですがそれも無理そうで。
	 */
	public String name;

	/**
	 * タイプ（任意・予約済み）。現在使用しません。
	 */
	public String type;

	/**
	 * （任意）作成者の名前。
	 */
	public String author;

	/**
	 * （必須）MQOファイルがある場所を指定します。MQOファイル以外は現在対応していません。OBJはまだ勉強中なのでよくわかりません。
	 * ここに指定するのは、「絶対パス」もしくは「相対パス」となります。なお、ZIPにより圧縮されている場合、アーカイブ内の相対パスで
	 * 指定する必要があります。例えば、「zip > model > abc.mqo」の場合、「model/abc.mqo」と指定します。
	 * なお絶対パスはZIPではなく解凍したファイルなどをそのまま参照するような場合にのみ使用します。通常はZIPルートからの相対パスで
	 * 指定するようにしてください。
	 */
	public String model;

	/**
	 * （必須）MQOファイルから読み取るテクスチャパスは使い物にならないことがほとんどなので、ここで改めてテクスチャの場所を指定します。
	 * テクスチャファイルの指定も上記と同じように指定してください。ResourceLocation使えないのがいやね
	 */
	public String texture;

	/**
	 * （任意）テクスチャのうち、発光する部分に使用するテクスチャとなります。信号機の場合はこれを指定しないと光らなくなります。
	 * 上記と同じように指定する必要があります。
	 */
	public String lightTexture;

	/**
	 * （必須）このモデルを実際にMinecraftに配置した際の大きさを指定します。指定できるのは、小数値です。
	 * 1を指定すると1ブロック分になります。なお、この大きさは「XYZの中で最も大きくなる長さ」にしてください。そうしないと
	 * 等倍縮小するため大変なことになります。
	 */
	public double scale;

	public GTC2ConfigBase() {}; // デフォルトコンストラクタを明示的に宣言しないとJacksonがエラーを吐く
}
