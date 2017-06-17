import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class ZhihuSpider implements PageProcessor {
	
    // 设置编码 ，超时时间，重试次数，
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

        if(page.getUrl().regex(URL_answer).match()){//如果URL匹配
            List<String> urlList  = page.getHtml().xpath("//div[@class=RichContent-inner]//img/@src").all();//找到所有图片的链接以字符串形式存到list
            String questionTitle = page.getHtml().xpath("//h1[@class=QuestionHeader-title]/text()").toString();//找到问题题目的链接并转换为字符串
            System.out.println("题目："+questionTitle);
            System.out.println(urlList);
            System.out.println("共找到"+urlList.size()/2+"张图片，即将开始下载");
            List<String> url = new ArrayList<String>();
            for (int i=0;i<urlList.size();i=i+2){
                url.add(urlList.get(i));
            }
            //在指定目录创建文件夹
            String filePath = "C:\\Users\\zqy49\\Pictures\\知乎图片";
            //尝试下载图片
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
    // 下载图片
    public static boolean downLoadPics( List<String> imgUrls,String title, String filePath) throws Exception {
        boolean isSuccess = true;

        // 文件路径+标题
        String dir = filePath +title;
        // 创建
        File fileDir = new File(dir);
        fileDir.mkdirs();

        int i = 1;
        // 循环下载图片
        for (String imgUrl : imgUrls) {
            URL url = new URL(imgUrl);
            // 打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            int x=(int)(Math.random()*1000000);
            String newImageName = dir + "/" + "pic" + i + "s" + x + ".jpg";
            // 建立一个新的文件
            FileOutputStream fos = new FileOutputStream(new File(newImageName));
            byte[] buffer = new byte[1024];
            int length;
            System.out.println("正在下载......第 " + i + "张图片......请稍后");
            // 开始填充数据
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
            System.out.println("第 " + i + "张图片下载完毕......");
            i++;
        }
        return isSuccess;
    }

}