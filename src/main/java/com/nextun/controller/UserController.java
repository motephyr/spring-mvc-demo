package com.nextun.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.nextun.db.entity.User;
import com.nextun.db.entity.UserFb;
import com.nextun.db.entity.UserNu;
import com.nextun.db.entity.UserProfile;
import com.nextun.db.service.user.ProfileService;
import com.nextun.db.service.user.UserFbService;
import com.nextun.db.service.user.UserNuService;
import com.nextun.db.service.user.UserService;
import com.nextun.enums.EnumAjax;
import com.nextun.utils.CookiesUtil;
import com.nextun.utils.HttpUtil;
import com.nextun.utils.NUConstants;
import com.nextun.utils.codec.AES;

@Controller
@SessionAttributes(NUConstants.USER_SESSION_KEY)
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ProfileService profileService;

  @Autowired
  private UserFbService userFbService;

  @Autowired
  private UserNuService userNuService;
  
  @RequestMapping(value = "/nuLogin", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> nuLogin(@RequestParam String email,
      @RequestParam String password,ModelMap model, HttpServletResponse response,HttpServletRequest request)
      throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    try {
      UserNu userNu = userNuService.selectByEmail(email);
      if (userNu.getPassword().equals(password)) {
        User user = userService.getById(userNu.getUserId());
        
        setUserLogin(user,response,request,model);
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } else {
        map.put("result", EnumAjax.ERROR.getMsg());
      }

    } catch (Exception e) {
      e.printStackTrace();
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;
    }
    return map;
  }

  @RequestMapping(value = "/nuRegister", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> nuRegister(@RequestParam String email,
      @RequestParam String password, @RequestParam String nickname,
      @RequestParam int country,HttpServletRequest request,ModelMap model, HttpServletResponse response) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    try {

      UserNu userNu = userNuService.selectByEmail(email);
      if (userNu == null) { // 資料庫內無相同的Email
        User newUser = userService.newUser(email, nickname, "", country);
        newUser = userService.save(newUser);

        UserNu newUserNu = new UserNu();
        newUserNu.setUserId(newUser.getId());
        newUserNu.setEmail(email);
        newUserNu.setPassword(password);
        // XXX: 下次登入時驗證碼以Email清空後帳號才可使用
        newUserNu.setConfirm(RandomStringUtils.randomAlphanumeric(10));

        userNuService.save(newUserNu);

        setUserLogin(newUser,response,request,model);

        Timestamp sendTime = new Timestamp(Calendar.getInstance()
            .getTimeInMillis());
        UserProfile profile = new UserProfile();
        profile.setUserId(newUser.getId());
        profile.setSendTime(sendTime);
        profile.setLoginTime(sendTime);
        profile.setIp(request.getRemoteAddr());

        profileService.save(profile);

        map.put("result", EnumAjax.SUCCESS.getMsg());
      } else {
        map.put("result", EnumAjax.ERROR.getMsg());
      }
    } catch (Exception e) {
      e.printStackTrace();
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;
    }
    return map;
  }

  @RequestMapping(value = "/fbLogin", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> fbLogin(@RequestParam String uid,
      @RequestParam String accessToken, @RequestParam String email,
      @RequestParam String nickname, @RequestParam int country,
     ModelMap model,HttpServletResponse response,HttpServletRequest request) throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    try {
      System.out.println(isUidVaild(uid, accessToken));
      if (isUidVaild(uid, accessToken)) {
        UserFb userFb = userFbService.selectByUid(uid);

        if (userFb != null) {
          User user = userService.getById(userFb.getUserId());
          if (user != null) {
            userFb.setAccessToken(accessToken);
            userFbService.update(userFb);

            setUserLogin(user,response,request,model);
          }

        } else {
          // TODO: 設定個人相片 photoUrl
          User newUser = userService.newUser(email, nickname, "", country);
          newUser = userService.save(newUser);

          UserFb newUserFb = new UserFb();
          newUserFb.setUserId(newUser.getId());
          newUserFb.setUid(uid);
          newUserFb.setAccessToken(accessToken);

          userFbService.save(newUserFb);

          setUserLogin(newUser,response,request,model);
        }
        map.put("result", EnumAjax.SUCCESS.getMsg());
      } else {
        map.put("result", EnumAjax.ERROR.getMsg());
      }

    } catch (Exception e) {
      e.printStackTrace();
      map.put("result", EnumAjax.ERROR.getMsg());
      throw e;
    }
    return map;
  }
  
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> logout(ModelMap model,HttpServletResponse response,SessionStatus status) throws Exception {
    CookiesUtil.removeCookie(NUConstants.NEXTUN_COOKIE_NAME, response);
    status.setComplete();
    Map<String, String> map = new HashMap<String, String>();
    map.put("result", EnumAjax.SUCCESS.getMsg());
    return map;
  }
  
  
  private static boolean isUidVaild(String uid, String accessToken)
      throws JSONException {
    // server端驗證
    // 格式大致像這樣
    // https://graph.facebook.com/1711272827?fields=installed&access_token=<SNIPPED
    // TOKEN>
    String responseJson = HttpUtil.getInstance().getResult(
        "https://graph.facebook.com/" + uid + "?" + "fields=installed&"
            + "access_token=" + accessToken, HttpUtil.CHACTER_UTF8);

    JSONObject jsonObject = new JSONObject(responseJson);
    String install = jsonObject.get("installed").toString();

    if (install.equals("true")) {
      return true;
    } else {
      return false;
    }
  }
  
  private static final String COOKIE_SPLIT_STR = "s";
  
  public static void setUserLogin(User user, HttpServletResponse response,HttpServletRequest request,
      ModelMap model) throws Exception {
//    if(user.getPhoto() != null){
//      user.setPhotoFullPath(ImageUtil.getCurrentImgPath(EnumSaveImageType.USER, user.getPhoto()));
//    }
    user.setLanguage(request.getLocale().toString());
    setLoginCookie(user.getId(), user.getEmail(), false, response); // 設定cookie
    model.addAttribute(NUConstants.USER_SESSION_KEY, user);
  }
  
  private static void setLoginCookie(long userId, String email,
      boolean isRemember, HttpServletResponse response) throws Exception {
    int age = -1;
    if (isRemember) {
      age = CookiesUtil.COOKIE_DEFAULT_AGE;
    }
    String cookieValue = userId + COOKIE_SPLIT_STR + AES.encrypt(email);
    CookiesUtil.addCookie(NUConstants.NEXTUN_COOKIE_NAME, cookieValue, age, response);
  }
}