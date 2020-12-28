package jp.gingarenpo.gtc2.config;

/**
 * 交通信号機用電柱に関する設定項目を一覧で代入するクラス。共通仕様は既に継承したAbstractクラスによって実装されています。
 * 全てのフィールドをpublicにしているのは、getterとsetterを使用するのがめんどくさいからです。Jacksonはpublicの場合にのみ
 * この2つを必要としません。とくに設定時に追加でやるものもないのでpublicにしています。API側ではこの内容はreadonlyとして扱います。
 * まず書き換えてはいけません。
 */
public class TrafficPoleConfig extends GTC2ConfigBase {

	/**
	 * （必須）この項目は、同じタイプでない電柱が隣接している際にその電柱と結合するかどうかを指定します。
	 * 通常はfalseを指定して、同じタイプのみを接続するように設定したほうがいいですが、どうしても他の電柱であろうと接続したい場合は
	 * trueを指定するとそのような処理になります。
	 */
	public boolean ignoreTypeCheck;

	/**
	 * （任意）この項目は、電柱をフェンスなどと接続するかを表しますが、現在この項目は使用不可能です（バニラのフェンスを書き換える術を
	 * 知らないため）。
	 * デフォルトでfalseが入っています。trueにしようと変わりません。
	 */
	public boolean jointFance = false;

}
