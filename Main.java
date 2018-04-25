
import javax.swing.*;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Visualization and Comparison of Sorting Algorithms
public class Main extends JApplet {

	private static final long serialVersionUID = 1L;
	private SortPanel[] sortPanels = new SortPanel[3];

	private static int size = 30;
	private int sleepTime = 100;
	private String animationName = "";

	public Main() {
		setLayout(new GridLayout(1, 1, 0, 0));
		SortPanelsHolder sortPanelHolder = new SortPanelsHolder();
		sortPanelHolder.setLayout(new  GridLayout(3, 1, 0, 0));
		sortPanelHolder.setBackground(Color.CYAN);
		sortPanelHolder.setForeground(Color.CYAN);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width / 1;
		int height = screenSize.height / 3;
		sortPanels[0] = new SelectionSortPanel(" Selection Sort ", sleepTime, width, height);
		sortPanels[1] = new InsertionSortPanel(" Insertion Sort  ", sleepTime, width, height);
		sortPanels[2] = new BubbleSortPanel(" Bubble Sort ", sleepTime, width, height);
//		sortPanels[3] = new MergeSortPanel(" Merge Sort ", sleepTime, width, height);

		
		for (int i = 0; i < sortPanels.length; i++) {
			sortPanels[i].setVisible(false);
			sortPanelHolder.add(sortPanels[i]);				
		}
		add(sortPanelHolder);
	}
	
	class SortPanelsHolder extends JPanel {
		private static final long serialVersionUID = 1L;
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			Font animationNameFont = new Font("Serif", Font.BOLD, 150);
			FontMetrics animationNameFontFontMetrix = getFontMetrics(animationNameFont);
			g.setFont(animationNameFont);
			int x = (getWidth() - animationNameFontFontMetrix.stringWidth(animationName)) / 2;
			int y = (getHeight() - animationNameFontFontMetrix.getLeading()) / 2;
			g.drawString(animationName, x, y);
		}
	}
	
	public void beginAnimation(String animationName, int[] list) {
		try {
			
			this.animationName = animationName;
			repaint();
			Thread.sleep(2000);
			this.animationName = "";
			repaint();
			for (int i = 0; i < sortPanels.length; i++) {
				sortPanels[i].setList(list);
				sortPanels[i].setVisible(true);
			}
			Thread.sleep(1000);
			ExecutorService executor = Executors.newFixedThreadPool(sortPanels.length);
			for (int i = 0; i < sortPanels.length; i++) {
				executor.execute(sortPanels[i]);
			}
			executor.shutdown();
			while(!executor.isTerminated()) {
				Thread.sleep(100);
			}
			Thread.sleep(1000);
			for (int i = 0; i < sortPanels.length; i++) {
				sortPanels[i].setVisible(false);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void endAnimation(){
		this.animationName = "Hakuna Matata";
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Sorting Algorithm Animations");
		Main main = new Main();
		frame.add(main);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		int[] list = new int[size];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = i + 1;
		}
		for (int i = 0; i < list.length; i++) {
			int index = (int) (Math.random() * list.length);
			int temp = list[i];
			list[i] = list[index];
			list[index] = temp;
		}
		main.beginAnimation("CS382 Project", list);
		main.endAnimation();
	}
}
