package com.nextun.enums;

public enum EnumActivityState {
  INIT(0, "INIT"), DELETE(1,"DELETE"), SIZE(2, "SIZE");

private int code;
private String msg;

private EnumActivityState(int code, String msg) {
this.code = code;
this.msg = msg;
}

public int getCode() {
return this.code;
}

public String getMsg() {
return this.msg;
}
}
