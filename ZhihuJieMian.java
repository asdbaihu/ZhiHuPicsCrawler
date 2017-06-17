import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import us.codecraft.webmagic.Spider;

public class ZhihuJieMian extends JFrame{
	private JTextField jtfUrl = new JTextField();
	
	private JButton jbtStartCrawl = new JButton("��ʼץȡ");
	
	public ZhihuJieMian(){
		//�½����p1
		JPanel p1 = new JPanel(new GridLayout(1,2));
		p1.add(new JLabel("Answer URL:"));
		p1.add(jtfUrl);
		p1.setBorder(new TitledBorder("��������ַ����ʽ��/question/23535321/answer/86587243����"));
		
		//�½����p2
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(jbtStartCrawl);
		
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
		//����ť��Ӽ�����
		jbtStartCrawl.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//������ֵ����url
			String url = jtfUrl.getText();
			//��ʼץȡ
			System.out.println("��ʼץȡhttps://www.zhihu.com"+url+"�µ�ͼƬ");
	        String answerUrl = "https://www.zhihu.com"+url;
	      //�½�����
	        Spider.create(new ZhihuSpider()).addUrl(answerUrl).thread(1).run();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ZhihuJieMian frame = new ZhihuJieMian();
        frame.pack();
        frame.setTitle("֪��ͼƬһ��ץȡ");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

}
