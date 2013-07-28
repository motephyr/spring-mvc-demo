package com.nextun.jms.test;

 
import java.util.HashMap;
import java.util.Scanner;
import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;
 
public class SpringPublish {
 
    private JmsTemplate template;
    private Destination[] destinations;
 
    public void chart()
    {
        boolean chart = true;
        int count = 0;
        while(chart)
        {
            count ++;
            Scanner cin=new Scanner(System.in);
            System.out.println("输入聊天内容，输入N停止聊天");
            String text=cin.nextLine();
            if(text.equals("N"))
            {
                chart = false;
            }
            System.out.println("我："+text);
            sendChartMessage(count,text);
        }
 
    }
    protected void sendChartMessage(int count , String strMessage)
    {
        MyMessageCreator creator = new MyMessageCreator(count,strMessage);
        template.send(destinations[0], creator);
    }
 
    public JmsTemplate getTemplate() {
        return template;
    }
 
    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }
 
    public Destination[] getDestinations() {
        return destinations;
    }
 
    public void setDestinations(Destination[] destinations) {
        this.destinations = destinations;
    }
 
}