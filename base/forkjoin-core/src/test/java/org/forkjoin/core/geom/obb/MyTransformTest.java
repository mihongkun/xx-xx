package org.forkjoin.core.geom.obb;

import javax.swing.*;

public class MyTransformTest extends JPanel {
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				JFrame frame = new JFrame();
//				frame.setTitle("AffineTransform");
//				frame.setSize(1000, 600);
////				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				Container contentPane = frame.getContentPane();
//				contentPane.add(new MyTransformTest());
//				frame.setVisible(true);
//				frame.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowClosing(WindowEvent e) {
//						System.exit(0);
//					}
//				});
//			}
//		});
//
//	}
//
//	public MyTransformTest() {
//		ActionListener taskPerformer = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				paint();
//			}
//		};
//		new Timer(0, taskPerformer).start();
//	}
//
//	public void paint() {
//		innerPaint();
//
//		ActionListener taskPerformer = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				paint();
//			}
//		};
//		new Timer(50, taskPerformer).start();
//	}
//
//	private double r = 180;
//
//	public void innerPaint() {
//		OBBTransformRectangle oobRect1 = new OBBTransformRectangle();
//		oobRect1.setByLeftTop(270, 200, 50, 200, Math.toRadians(r));
//        oobRect1.commit();
//
////		OBBTransformRectangle obbRect0 = new OBBTransformRectangle();
////		obbRect0.setByLeftTop(200, 200, 50, 200, 0);
////        oobRect1.commit();
//
////		int color = OBBUtils.detector(obbRect0, oobRect1) ? 0xff0000 : 0xff00;
//
//
//		Image bufferImg = this.createImage(getWidth(), getHeight());
//		Graphics2D g2 = (Graphics2D) bufferImg.getGraphics();
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.clearRect(0, 0, getWidth(), getHeight());
//
//
////		paintRect(g2, obbRect0, color);
//
//		paintRect(g2, oobRect1,0xff0000);
//
//		// System.out.println(OBBUtils.detector(obbRect0, oobRect1));
////		r += 0.05;
//
//		getGraphics().drawImage(bufferImg, 0, 0, null);
//	}
//
//	public void paintRect(Graphics2D g2, OBBTransformRectangle obbRect,int color) {
//		AffineTransform at = new AffineTransform();
//		OBBVector centerPoint = obbRect.getCenterPoint();
//		at.rotate(obbRect.getRotation(), centerPoint.getX(), centerPoint.getY());
//		at.translate(obbRect.getX(), obbRect.getY());
//
//		g2.drawRenderedImage(new MyBufferedImage(obbRect,color), at);
//
//		Rectangle rect = obbRect.getBounds();
//
//		g2.drawRect((int)Math.round(rect.getX()), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
//	}
//
//	public static class MyBufferedImage extends BufferedImage {
//		public MyBufferedImage(OBBTransformRectangle oobRect, int color) {
//			this(oobRect, oobRect.getBounds(), color);
//		}
//
//		public MyBufferedImage(OBBTransformRectangle obbRect, Rectangle rect0, int color) {
//			super((int) obbRect.getNativeWidth() + 1, (int) obbRect.getNativeHeight() + 1, (int) BufferedImage.TYPE_4BYTE_ABGR);
//			Graphics2D g2 = createGraphics();
////			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			g2.setColor(new Color(color));
//			g2.drawRect(0, 0, (int) obbRect.getNativeWidth(), (int) obbRect.getNativeHeight());
//
//			g2.setColor(new Color(color));
//			int x1 = (int) obbRect.getNativeWidth() / 2;
//			int y1 = (int) obbRect.getNativeHeight() / 2;
//			g2.drawLine(x1, y1, x1, y1);
//		}
//	}
}