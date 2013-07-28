package com.nextun.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.nextun.jms.JmsService;
import com.nextun.jms.JmsUtil;



@Controller
@RequestMapping("/notice")
public class NoticeController {
  
  private final NoticeRepository noticeRepository;

  private final Map<DeferredResult<List<String>>, Integer> chatRequests = new ConcurrentHashMap<DeferredResult<List<String>>, Integer>();

  @Autowired
  public NoticeController(NoticeRepository noticeRepository) {
    this.noticeRepository = noticeRepository;
  }

  // async
  @RequestMapping(method = RequestMethod.GET,value="/{id}")
  @ResponseBody
  public DeferredResult<List<String>> getMessages(@PathVariable String id) {

    final DeferredResult<List<String>> deferredResult = new DeferredResult<List<String>>(
        null, Collections.emptyList());
    System.out.println("messageIndex"+id);
    this.chatRequests.put(deferredResult, Integer.parseInt(id));

    deferredResult.onCompletion(new Runnable() {
      @Override
      public void run() {
        System.out.println("remove");
        chatRequests.remove(deferredResult);
      }
    });

    System.out.println("out"+deferredResult);

    final List<String> messages = this.noticeRepository.getMessages(Integer.parseInt(id));
    if (!messages.isEmpty()) {
      new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.println("messages"+messages);
        deferredResult.setResult(messages);
      }}).start();
    }

    return deferredResult;
  }

  @RequestMapping(method = RequestMethod.GET,value="/postMessage")
  @ResponseBody
  public void postMessage(@RequestParam String message) {
    System.out.println("message"+message);
    JmsUtil.getBean(JmsService.class).addMessage(123, message);
    
    //this.noticeRepository.addMessage(message);

    // Update all chat requests as part of the POST request
    // See Redis branch for a more sophisticated, non-blocking approach

//    for (Entry<DeferredResult<List<String>>, Integer> entry : this.chatRequests
//        .entrySet()) {
//      List<String> messages = this.noticeRepository.getMessages(entry
//          .getValue());
//      entry.getKey().setResult(messages);
//    }
  }

}
