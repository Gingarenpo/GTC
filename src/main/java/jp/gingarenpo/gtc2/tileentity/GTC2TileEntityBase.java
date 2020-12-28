package jp.gingarenpo.gtc2.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * GTC2によって追加されるTileEntityにおいて、共通化すべきメソッドなどを先にまとめたものとなります。
 * このクラス自体はabstractなので、インスタンスを生成することができません。必ず継承先のインスタンスを生成するようにします。
 *
 */
public abstract class GTC2TileEntityBase extends TileEntity {

	public GTC2TileEntityBase() {
		super(); // コンストラクタはここでは多分ほとんど弄らない。
	}
}
