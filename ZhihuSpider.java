import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class ZhihuSpider implements PageProcessor {
	
    // ���ñ��� ����ʱʱ�䣬���Դ�����
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
            .addCookie("www.zhihu.com", "unlock_ticket", "QUJBTXRpWGJRd2dYQUFBQVlRSlZUZl83Q2xjZkJISHZkZm13R05Jck93eTNFU2IyUE53LWVnPT0=|"
                    + "1460335857|e1d68d4125f73b6280312c3eafa71da1b9fc7cab")
//            .addCookie("login", "MWRiZWUxNmMzOTA5NDdmNTkwNGRmNWQyZWZhNDRmY2U=|1475371295|b9e9c165fc1d3c314afa2b66e3ff27c514bb4946")
            .addCookie("Domain", "zhihu.com")
            .addCookie("q_c1", "27cac3dee316405e94cfb9f8dadadc7a|1495936405000|1495936405000")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    
    private  static  final String URL_answer = "https://www\\.zhihu\\.com/question/\\d+/answer/\\d+";
    //https://www.zhihu.com/people/dan-wen-hui-10/answers


    @Override
    public void process(Page page) {

        if(page.getUrl().regex(URL_answer).match()){//���URLƥ��
            List<String> urlList  = page.getHtml().xpath("//div[@class=RichContent-inner]//img/@src").all();//�ҵ�����ͼƬ���������ַ�����ʽ�浽list
            String questionTitle = page.getHtml().xpath("//h1[@class=QuestionHeader-title]/text()").toString();//�ҵ�������Ŀ�����Ӳ�ת��Ϊ�ַ���
            System.out.println("��Ŀ��"+questionTitle);
            System.out.println(urlList);
            System.out.println("���ҵ�"+urlList.size()/2+"��ͼƬ��������ʼ����");
            List<String> url = new ArrayList<String>();
            for (int i=0;i<urlList.size();i=i+2){
                url.add(urlList.get(i));
            }
            //��ָ��Ŀ¼�����ļ���
            String filePath = "C:\\Users\\zqy49\\Pictures\\֪��ͼƬ";
            //��������ͼƬ
            try {
                downLoadPics(url,questionTitle,filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    //public static void main(String[] args) {

    //}
    // ����ͼƬ
    public static boolean downLoadPics( List<String> imgUrls,String title, String filePath) throws Exception {
        boolean isSuccess = true;

        // �ļ�·��+����
        String dir = filePath +title;
        // ����
        File fileDir = new File(dir);
        fileDir.mkdirs();

        int i = 1;
        // ѭ������ͼƬ
        for (String imgUrl : imgUrls) {
            URL url = new URL(imgUrl);
            // ������������
            DataInputStream dis = new DataInputStream(url.openStream());
            int x=(int)(Math.random()*1000000);
            String newImageName = dir + "/" + "pic" + i + "s" + x + ".jpg";
            // ����һ���µ��ļ�
            FileOutputStream fos = new FileOutputStream(new File(newImageName));
            byte[] buffer = new byte[1024];
            int length;
            System.out.println("��������......�� " + i + "��ͼƬ......���Ժ�");
            // ��ʼ�������
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            System.out.println("�� " + i + "��ͼƬ�������......");
            i++;
        }
        return isSuccess;
    }

}