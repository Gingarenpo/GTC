package jp.gingarenpo.gtc2.tileentity;

import jp.gingarenpo.gtc2.config.TrafficPoleConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

/**
 * 交通信号機用電柱、ならびにアームのデータ管理を行うTileEntityとなります。基本的なデータ管理は全てここで行います。
 * 詳しいことはTileEntityの説明がサイトにごろごろ掲載されているのでそちらでもご覧ください。
 *
 * 接続の検知については、TileEntity生成時に行います。ネット通信を少しでも減らしたいので…。
 */
public class TileEntityTrafficPole extends GTC2TileEntityBase {

	/**
	 * この電柱のタイプ。コンフィグって名前にしているけど実はJSONです。Jacksonライブラリ使用して展開するために別クラスにしています。
	 */
	private TrafficPoleConfig config;

	/**
	 * このメソッドは、この電柱が同じタイプの電柱と接続されているかどうかを表します。なお、JSONで設定を無効にすると、
	 * 同じタイプじゃなかろうと電柱なら接続します。
	 * @return 接続されているすべてのEnumFacingを返す（ArrayListで帰ります）。接続が一つもない場合は空
	 */
	private ArrayList<EnumFacing> getJoints() {
		// 6方向全てのTileEntityを取得する
		ArrayList<EnumFacing> list = new ArrayList<EnumFacing>(); // 追加していく形なので
		for (EnumFacing facing : EnumFacing.VALUES) {
			// 方向分繰り返す
			if (isJoint(facing)) list.add(facing); // 接続されていたらそれを代入する
		}
		return list; // 結果を返す
	}

	/**
	 * このメソッドは、この電柱が指定した方向において他の電柱と接続しているかどうかを返します。なお、JSONで設定を無効にすると、
	 * 同じタイプであろうとなかろうと電柱のインスタンス自体が生成されていれば問答無用でtrueを返します。
	 *
	 * @param facing 検証したい向き。
	 * @return 接続されていればtrue、そうでないならばfalse。
	 */
	private boolean isJoint(EnumFacing facing) {
		// facingによって座標が変わってくる
		int x = this.pos.getX(), y = this.pos.getY(), z = this.pos.getZ(); // ひとまず現在の座標を代入
		switch (facing) {
			case DOWN:
				// 下
				y--; // Y座標が1個下がる
				break;
			case UP:
				// 上
				y++; // Y座標が1個上がる
				break;
			case NORTH:
				// 北
				z++; // Z座標が1個上がる
				break;
			case SOUTH:
				// 南
				z--; // Z座標が1個下がる
				break;
			case WEST:
				// 西
				x--; // X座標が1個下がる
				break;
			case EAST:
				// 東
				x++; // X座標が1個上がる
				break;
		}
		// 座標を取得できたので、そこにあるTileEntityを取得する
		TileEntity check = this.world.getTileEntity(new BlockPos(x, y, z)); // Nullの可能性もある
		if (check == null) return false; // Nullの場合はそもそもTileEntity自体がない

		// 以降、TileEntity（何かはわからないが）存在する！
		if (!(check instanceof TileEntityTrafficPole)) return false; // TileEntityの種類が電柱でない場合は接続されていない！

		// 以降、このTileEntityは電柱であることがわかるので、安心してキャストできる
		TileEntityTrafficPole next = (TileEntityTrafficPole) check; // 絶対にキャストできる
		if (next.config.id == this.config.id) return true; // IDが一致したら同じタイプなので接続確実
		else if (next.config.ignoreTypeCheck) return true; // もしくはIDが一致しなくともタイプチェック無視なら接続できている

		return false; // それ以外の場合は接続しない
	}
}
