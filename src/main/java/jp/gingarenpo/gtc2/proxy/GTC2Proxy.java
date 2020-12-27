package jp.gingarenpo.gtc2.proxy;

/**
 * GTC2のプロキシで、クライアントとサーバーで処理を分けるべきものに使用します。
 * ようは「サーバーだと落ちちゃう」ダメなModになることを少しでも防止するものです。と言いつつどれがどっちで処理されているのかは
 * よくわかっていません。
 */
public class GTC2Proxy {

	/**
	 * サーバーサイドで行う処理をここに記述します。
	 */
	public void init() {
		// サーバー側として行う処理
	}

	/**
	 * クライアント専用: ブロックのモデルを登録します。サーバー側では何もしません。
	 */
	public void registerBlockModel() {

	}
}
