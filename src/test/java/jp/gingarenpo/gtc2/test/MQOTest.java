package jp.gingarenpo.gtc2.test;

import jp.gingarenpo.api.mqo.MQO;
import jp.gingarenpo.api.mqo.MQOFace;
import jp.gingarenpo.api.mqo.MQOObject;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;


/**
 * MQOファイルを読み込んで色々と行うテスト。swing使っているのは単にめんどくさいからです。
 * ひとまずここでは正規化がメイン。
 */
public class MQOTest extends JFrame {

	private static int angle;

	public MQOTest(String title) {
		super(title);
	}

	public static void main(String[] args) throws IOException {
		// ファイル選択ダイアログ
		JFileChooser fc = new JFileChooser("D:\\RTM Addons\\");
		fc.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				// fの中身によってファイルを切り替える
				if (f.isDirectory()) return true;
				String name = f.getName();
				return (name.endsWith(".mqo")); // 簡易的なチェックだけど
			}

			@Override
			public String getDescription() {
				return "Test.";
			}
		}); // 受け付けるファイル形式
		fc.showOpenDialog(null); // 受け付けて
		File input = fc.getSelectedFile(); // 入力されたファイル（チェックしないよ）

		// ファイルを読み込んでMQOを生成する
		MQO mqo = new MQO(input);

		mqo.normalize(1); // 1以内の大きさに最適化

		// lwjgl起動
		if (!glfwInit()) {
			throw new IOException("Failed to start LWJGL.");
		}
		glfwDefaultWindowHints();
		GLFWErrorCallback.createPrint(System.err).set();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		long window = glfwCreateWindow(640, 640, "Test", 0, 0); // 起動して
		glfwSetKeyCallback(window, (windows, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(windows, true); // We will detect this in the rendering loop
		});

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		loop(window, mqo);

	}

	private static void loop(long window, MQO mqo) {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			GL11.glPopMatrix();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			// draw
			mqo.drawOld();

			// angle
			angle = (angle >= 360) ? 0 : angle+1; // angle+1
			GL11.glRotated(Math.toRadians(angle), 0, 1, 1);


			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			GL11.glPushMatrix();
		}
	}
}
