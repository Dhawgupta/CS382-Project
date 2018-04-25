
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

public class BubbleSortPanel extends SortPanel {
	private static final long serialVersionUID = 1L;
	private int redColumn = -1;
	private int cyanColumn = -1;
	
	public BubbleSortPanel(String name, int sleepTime, int width, int height) {
		super(name, sleepTime, width, height);
	}

	@Override
	public void reset() {
		redColumn = -1;
		cyanColumn = -1;		
	}

	@Override
	public void run() {
		try {
			boolean needNextPass = true;
			for (int k = 1; k < list.length && needNextPass; k++) {
				needNextPass = false;
				for (int i = 0; i < list.length - k; i++) {
					redColumn = i;
					repaint();
					Thread.sleep(sleepTime);
					if (list[i] > list[i + 1]) {
						redColumn = i + 1;
						int temp = list[i];
						list[i] = list[i + 1];
						list[i + 1] = temp;
						repaint();
						Thread.sleep(sleepTime);
						needNextPass = true; // flag to check wether to repaint the screen in next iteration
					}
				}
				cyanColumn = size - k;
			}
			cyanColumn = 0;
			redColumn = -1;
		} catch (InterruptedException e) {
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / size;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / size;

		for (int i = 0; i < (cyanColumn == -1 ? list.length : cyanColumn); i++) {
			printNumberOnTop(g,list[i],2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH);
			g.setColor(Color.YELLOW);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
		}
		if(cyanColumn != -1) {

			for (int i = cyanColumn; i < list.length; i++) {
				printNumberOnTop(g,list[i],2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH);
				g.setColor(Color.CYAN);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);
				g.setColor(Color.BLACK);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list[i] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[i] * columnHeight);			
			}
		}
		if(redColumn != -1) {
			printNumberOnTop(g,list[redColumn],2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH);

			g.setColor(Color.RED);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
			g.setColor(Color.BLACK);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * redColumn, getHeight() - list[redColumn] * columnHeight - 2 * BORDER_WIDTH, columnWidth, list[redColumn] * columnHeight);
		}
	}

}
//	Font nameFont = new Font("serif", Font.BOLD, 25);
//	FontMetrics nameFontMetrix = getFontMetrics(nameFont);
//		g.setColor(Color.ORANGE);
//				//g.fillRect(x,y,widht, height) where we take the midway of the panel and zeo hefiht and then take the string wight and height in the wight and height arameters
//				g.fillRect((getWidth() - nameFontMetrix.stringWidth(name)) / 2, 0, nameFontMetrix.stringWidth(name), BORDER_WIDTH + nameFontMetrix.getAscent() / 3);
//				g.setColor(Color.BLACK);
//				g.setFont(nameFont);
//				g.drawString(name, (getWidth() - nameFontMetrix.stringWidth(name)) / 2, BORDER_WIDTH + nameFontMetrix.getAscent() / 3);
