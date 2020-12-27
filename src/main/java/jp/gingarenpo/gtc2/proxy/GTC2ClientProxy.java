package jp.gingarenpo.gtc2.proxy;

import jp.gingarenpo.gtc2.GTC2;
import jp.gingarenpo.gtc2.block.GTC2Block;
import net.minecraft.item.Item;

/**
 * プロキシの、クライアントサイドで実行するためのものです。サーバーで実行するとクラッシュするような内容を主に登録します。
 * 例えば、レンダリング関係のメソッドはサーバー側で行うと落ちます。
 */
public class GTC2ClientProxy extends GTC2Proxy {

	/**
	 * クライアントサイドで処理する内容を記述しています。
	 */
	@Override
	public void init() {
		// superをしてしまうとサーバーの処理も実行してしまうためそれは記さないで完全にオーバーライドしてしまう
	}

	@Override
	public void registerBlockModel() {
		// ブロックのモデル
		net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation
				(Item.getItemFromBlock(GTC2Block.TRAFFIC_POLE), 0,
						new net.minecraft.client.renderer.block.model.ModelResourceLocation
								(GTC2.MODID + ":gtc2_traffic_pole_model", "inventory")); // 登録
	}
}
