import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import us.codecraft.webmagic.Spider;

public class ZhihuJieMian extends JFrame{
	private JTextField jtfUrl = new JTextField();
	
	private JButton jbtStartCrawl = new JButton("开始抓取");
	
	public ZhihuJieMian(){
		//新建面板p1
		JPanel p1 = new JPanel(new GridLayout(1,2));
		p1.add(new JLabel("Answer URL:"));
		p1.add(jtfUrl);
		p1.setBorder(new TitledBorder("请输入网址（格式：/question/23535321/answer/86587243）："));
		
		//新建面板p2
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(jbtStartCrawl);
		
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
		//给按钮添加监听器
		jbtStartCrawl.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//将输入值赋给url
			String url = jtfUrl.getText();
			//开始抓取
			System.out.println("开始抓取https://www.zhihu.com"+url+"下的图片");
	        String answerUrl = "https://www.zhihu.com"+url;
	      //新建爬虫
	        Spider.create(new ZhihuSpider()).addUrl(answerUrl).thread(1).run();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ZhihuJieMian frame = new ZhihuJieMian();
        frame.pack();
        frame.setTitle("知乎图片一键抓取");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

}
